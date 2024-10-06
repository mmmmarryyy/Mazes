package mazes

import scala.util.Random

/** Trait for maze generators.
  */
trait Generator {

  /** Generate a maze with a given height and width.
    *
    * @param height
    *   Maze height.
    * @param width
    *   Maze width.
    * @param useSurfaceTypes
    *   Should we use custom surface types
    * @return
    *   Generated maze.
    */
  def generate(height: Int, width: Int, useSurfaceTypes: Boolean): Maze
}

/** Implementation of maze generation using recursive division algorithm.
  */
object RecursiveDivisionGenerator extends Generator {
  private val horizontal = 0
  private val vertical = 1

  private def divide(
      grid: Vector[Vector[Cell]],
      x: Int,
      y: Int,
      currentWidth: Int,
      currentHeight: Int,
      forbiddenTop: Int,
      forbiddenBottom: Int,
      forbiddenLeft: Int,
      forbiddenRight: Int
  ): Vector[Vector[Cell]] = {
    val dx = currentWidth - x
    val dy = currentHeight - y

    val possibleXIndexes = Range(x + 2, currentWidth, 2)
      .filter(row => row != forbiddenTop && row != forbiddenBottom)
      .toList
    val possibleYIndexes = Range(y + 2, currentHeight, 2)
      .filter(row => row != forbiddenLeft && row != forbiddenRight)
      .toList

    if (possibleXIndexes.isEmpty || possibleYIndexes.isEmpty) {
      grid
    } else {
      val wall =
        if (dy > dx) horizontal
        else if (dx > dy) vertical
        else scala.util.Random.nextInt(2)

      val xPointer = Random.shuffle(possibleXIndexes).head
      val yPointer = Random.shuffle(possibleYIndexes).head

      val updatedGrid =
        if (wall == horizontal) {
          val newRowY = grid(yPointer).zipWithIndex.map { (cell, index) =>
            if (index <= x || index >= currentWidth || index == xPointer) {
              cell
            } else {
              cell.copy(cellType = Cell.WALL)
            }
          }

          val newGrid = grid.updated(yPointer, newRowY)
          divide(
            divide(
              newGrid,
              x,
              y,
              currentWidth,
              yPointer,
              forbiddenTop,
              xPointer,
              forbiddenLeft,
              forbiddenRight
            ),
            x,
            yPointer,
            currentWidth,
            currentHeight,
            xPointer,
            forbiddenBottom,
            forbiddenLeft,
            forbiddenRight
          )
        } else {
          val newGrid = grid.zipWithIndex.map { (row, index) =>
            if (index <= y || index >= currentHeight || index == yPointer) {
              row
            } else {
              row.zipWithIndex.map { (cell, cellIndex) =>
                if (cellIndex == xPointer) {
                  cell.copy(cellType = Cell.WALL)
                } else {
                  cell
                }
              }
            }
          }

          divide(
            divide(
              newGrid,
              x,
              y,
              xPointer,
              currentHeight,
              forbiddenTop,
              forbiddenBottom,
              forbiddenLeft,
              yPointer
            ),
            xPointer,
            y,
            currentWidth,
            currentHeight,
            forbiddenTop,
            forbiddenBottom,
            yPointer,
            forbiddenRight
          )
        }
      updatedGrid
    }
  }

  override def generate(
      height: Int,
      width: Int,
      useSurfaceTypes: Boolean
  ): Maze = {
    val initialGrid = Vector.tabulate(height, width) { case (row, col) =>
      if (row == 0 || col == 0 || row == height - 1 || col == width - 1) {
        Cell(row, col, Cell.WALL, SurfaceType.Standard)
      } else {
        Cell(row, col, Cell.PASSAGE, SurfaceType.Standard)
      }
    }

    val generatedGrid =
      divide(initialGrid, 0, 0, width - 1, height - 1, -1, -1, -1, -1)

    Maze(
      height,
      width,
      if (useSurfaceTypes) {
        generatedGrid.map { row =>
          row.map { cell =>
            if (
              cell.cellType == Cell.PASSAGE && scala.util.Random.nextDouble < 0.3
            ) {
              cell.copy(surfaceType =
                if (scala.util.Random.nextDouble < 0.5) SurfaceType.Sand
                else SurfaceType.Coin
              )
            } else {
              cell
            }
          }
        }
      } else {
        generatedGrid
      }
    )
  }
}

/** Implementation of maze generation using recursive backtracking algorithm.
  */
object RecursiveBacktrackingGenerator extends Generator {
  def generate(height: Int, width: Int, useSurfaceTypes: Boolean): Maze = {
    val initialGrid = Vector.tabulate(height, width) { case (row, col) =>
      if (row % 2 == 1 && col % 2 == 1) {
        Cell(row, col, Cell.PASSAGE, SurfaceType.Standard)
      } else {
        Cell(row, col, Cell.WALL, SurfaceType.Standard)
      }
    }

    val visited = Vector.tabulate(height, width) { case (row, col) =>
      false
    }

    val startCell = Coordinate(1, 1)
    val (generatedGrid, finalVisited) =
      carvePassage(initialGrid, startCell, visited)

    Maze(
      height,
      width,
      if (useSurfaceTypes) {
        generatedGrid.map { row =>
          row.map { cell =>
            if (
              cell.cellType == Cell.PASSAGE && scala.util.Random.nextDouble < 0.3
            ) {
              cell.copy(surfaceType =
                if (scala.util.Random.nextDouble < 0.5) SurfaceType.Sand
                else SurfaceType.Coin
              )
            } else {
              cell
            }
          }
        }
      } else {
        generatedGrid
      }
    )
  }

  private def carvePassage(
      grid: Vector[Vector[Cell]],
      current: Coordinate,
      visited: Vector[Vector[Boolean]]
  ): (Vector[Vector[Cell]], Vector[Vector[Boolean]]) = {
    val currentGrid = grid.updated(
      current.row,
      grid(current.row)
        .updated(
          current.col,
          Cell(current.row, current.col, Cell.PASSAGE, SurfaceType.Standard)
        )
    )

    val updatedVisited = visited.updated(
      current.row,
      visited(current.row).updated(current.col, true)
    )
    val shuffledDirections = Random.shuffle(directions)

    val newGridAndVisited =
      shuffledDirections.foldLeft((currentGrid, updatedVisited)) {
        (acc, direction) =>
          val (accGrid, accVisited) = acc

          val (newRow, newCol) =
            (current.row + direction.row * 2, current.col + direction.col * 2)

          if (
            isInBounds(newRow, newCol, accGrid) && !accVisited(newRow)(newCol)
          ) {
            carvePassage(
              accGrid.updated(
                (current.row + newRow) / 2,
                accGrid((current.row + newRow) / 2).updated(
                  (current.col + newCol) / 2,
                  Cell(
                    (current.row + newRow) / 2,
                    (current.col + newCol) / 2,
                    Cell.PASSAGE,
                    SurfaceType.Standard
                  )
                )
              ),
              Coordinate(newRow, newCol),
              accVisited
            )
          } else {
            acc
          }
      }

    newGridAndVisited
  }

  private def isInBounds(
      row: Int,
      col: Int,
      grid: Vector[Vector[Cell]]
  ): Boolean = {
    row >= 0 && row < grid.length && col >= 0 && col < grid(0).length
  }
}

package mazes

sealed trait SurfaceType

object SurfaceType {
  case object Standard extends SurfaceType
  case object Sand extends SurfaceType
  case object Coin extends SurfaceType
}

final case class Cell(
    row: Int,
    col: Int,
    cellType: Cell.Type,
    surfaceType: SurfaceType
)

object Cell {
  sealed trait Type
  case object WALL extends Type
  case object PASSAGE extends Type
}

final case class Coordinate(row: Int, col: Int)

/** Represents a maze.
  *
  * @param height
  *   Maze height.
  * @param width
  *   Maze width.
  * @param grid
  *   A two-dimensional array of cells representing a maze.
  */
case class Maze(height: Int, width: Int, grid: Vector[Vector[Cell]]) {
  self: Maze =>

  /** Find a path from a given starting point to a given ending point.
    *
    * @param solver
    *   Pathfinding Algorithm.
    * @param start
    *   Start point.
    * @param end
    *   End point.
    * @return
    *   List of path coordinates if path found, otherwise None.
    */
  def solve(
      solver: Solver,
      start: Coordinate,
      end: Coordinate
  ): Option[List[Coordinate]] = solver.solve(self, start, end)

  /** Renders a maze using the given renderer.
    *
    * @param renderer
    *   Maze renderer.
    * @return
    *   A string representing the maze in text format.
    */
  def render(renderer: Renderer): String = renderer.render(self)

  /** Renders a maze using the given renderer, displaying the path.
    *
    * @param renderer
    *   Maze renderer.
    * @param path
    *   The path from the starting point to the ending point.
    * @return
    *   A string representing the maze with its path displayed in text format.
    */
  def render(renderer: Renderer, path: List[Coordinate]): String =
    renderer.render(self, path)

  def isPassable(coordinate: Coordinate): Boolean = {
    val (row, col) = (coordinate.row, coordinate.col)
    row >= 0 && row < grid.length && col >= 0 && col < grid(0).length && grid(
      row
    )(col).cellType == Cell.PASSAGE
  }

  def getCellType(coordinate: Coordinate): SurfaceType = {
    grid(coordinate.row)(coordinate.col).surfaceType
  }
}

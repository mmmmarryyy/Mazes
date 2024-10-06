package mazes

/** Trait for maze rendering.
  */
trait Renderer {

  /** Drawing a labyrinth.
    *
    * @param maze
    *   Maze.
    * @return
    *   A string representing the rendered maze.
    */
  def render(maze: Maze): String

  /** Drawing a labyrinth with the found path.
    *
    * @param maze
    *   Maze.
    * @param path
    *   List of path coordinates.
    * @return
    *   A string representing the rendered maze with a path.
    */
  def render(maze: Maze, path: List[Coordinate]): String
}

object DefaultRenderer extends Renderer {
  override def render(maze: Maze): String = {
    render(maze, List())
  }

  override def render(maze: Maze, path: List[Coordinate]): String = {
    val grid = maze.grid
    val height = maze.height
    val width = maze.width

    Array
      .range(0, height)
      .map { row =>
        Array
          .range(0, width)
          .map { col =>
            if (grid(row)(col).cellType == Cell.WALL) {
              '#'
            } else if (path.contains(Coordinate(row, col))) {
              '*'
            } else {
              grid(row)(col).surfaceType match {
                case SurfaceType.Standard => ' '
                case SurfaceType.Sand     => '~'
                case SurfaceType.Coin     => '$'
              }
            }
          }
          .mkString("")
      }
      .mkString("\n")
  }
}

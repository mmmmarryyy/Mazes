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
            if (grid(row)(col).cellType == Cell.WALL) { // тут допускаю использование apply так как внутренняя реализация, но можно было бы не по range бежать, а прямо по вектору по типу grid.map(_.map(_.cellType))
              '#'
            } else if (path.contains(Coordinate(row, col))) {
              '*'
            } else {
              grid(row)(col).surfaceType match { // суть рендеров я понял, задумка крутая, классно, что получилось реализовать, но всю логику отображения разных типов поверхностей и стен я бы собрал в мапе, так было бы читать проще + было бы неплохо выводить обозначения в консоль, а то я в начале не понял, что это за значки :)
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

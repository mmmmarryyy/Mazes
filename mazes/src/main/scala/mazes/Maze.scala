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
    cellType: Cell.Type, // я бы не стал разделять cellType и surfaceType на 2 параметра, а внес бы их в один и на уровне ADT разделил, тебе бы в рендере тогда проще можно было бы реализовать многие моменты
    surfaceType: SurfaceType
)

object Cell {
  sealed trait Type
  case object WALL
      extends Type // капсом в Scala принято называть только статические константы, и то, не помню, чтобы кто-то их писал так
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
case class Maze(height: Int, width: Int, grid: Vector[Vector[Cell]]) { // лучше было бы сделать приватный конструктор, и создать apply метод, в который ты только grid передаешь, потому что height и width - избыточная информация тут
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
  ): Option[List[Coordinate]] = solver.solve(
    self,
    start,
    end
  ) // очень круто декомпозировала и изолировала всю логику по лабиринтам, прямо кайф

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
    row >= 0 && row < grid.length && col >= 0 && col < grid(0).length && grid( // тут можно было использовать get с Option чтобы дополнительно не проверять на то, выходим мы за массив или нет
      row
    )(col).cellType == Cell.PASSAGE
    // grid.lift(row).flatMap(_.lift(col)).exists(_ == Cell.PASSAGE)
  }

  def getCellType(coordinate: Coordinate): SurfaceType = { // так как метод не приватный, мы не можем гарантировать, что координата влезает в наш размер и тут может быть ошибка
    grid(coordinate.row)(coordinate.col).surfaceType
  }
}

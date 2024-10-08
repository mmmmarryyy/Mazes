import mazes.{
  BFSSolver,
  Coordinate,
  Cell,
  DefaultRenderer,
  SurfaceType,
  DijkstraSolver,
  InputUtils,
  RecursiveBacktrackingGenerator,
  RecursiveDivisionGenerator,
  maxMazeSize,
  minMazeSize
}

import scala.util.Random

@main
def main(): Unit = {
  println("Welcome to the Maze Generator and Solver!")

  val height = InputUtils
    .readInt( // всякие взаимодействия я бы вынес в отдельные классы, чтобы менять их в одном месте, но то, что вынесла вне функции, уже супер
      s"Enter the height of the maze (odd number from $minMazeSize to $maxMazeSize):",
      minMazeSize,
      maxMazeSize,
      true
    )
  val width = InputUtils.readInt(
    s"Enter the height of the maze (odd number from $minMazeSize to $maxMazeSize):",
    minMazeSize,
    maxMazeSize,
    true
  )

  val useSurfaceTypes = InputUtils.readSurfaceChoice()

  val generatorChoice = InputUtils.readInt(
    """Select generation algorithm:
      |1. Recursive Division - simpler maze in the end
      |2. Recursive Backtracking - more complex maze in the end
      |Enter the number of the desired algorithm:""".stripMargin,
    1,
    2
  )
  val generator =
    generatorChoice match { // вот эту логику можно было бы вынести в метод apply в классе Generator
      case 1 => RecursiveDivisionGenerator
      case 2 => RecursiveBacktrackingGenerator
    }

  val maze = generator.generate(height, width, useSurfaceTypes)

  println("Generated maze:")
  println(maze.render(DefaultRenderer))

  val startCoordinates = InputUtils.readCoordinates(
    "Enter the coordinates of the starting point:",
    maze
  )
  val endCoordinates =
    InputUtils.readCoordinates("Enter the coordinates of the end point:", maze)

  val solverChoice =
    if (useSurfaceTypes) 2
    else
      InputUtils.readInt(
        "Enter the algorithm number:\n1. BFS\n2. Dijkstra's algorithm",
        1,
        2
      )

  val solver = solverChoice match {
    case 1 => BFSSolver
    case 2 => DijkstraSolver
  }

  val path = maze.solve(solver, startCoordinates, endCoordinates)

  path match {
    case Some(path) =>
      println("Find path:")
      println(maze.render(DefaultRenderer, path))
    case None =>
      println("Path not found.")
  }
}

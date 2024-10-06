package mazes

import java.io.{ByteArrayOutputStream, PrintStream}

val simpleSquareTestMaze = Maze(
  3,
  3,
  Vector(
    Vector(
      Cell(0, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 2, Cell.WALL, SurfaceType.Standard)
    ),
    Vector(
      Cell(1, 0, Cell.WALL, SurfaceType.Standard),
      Cell(1, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(1, 2, Cell.WALL, SurfaceType.Standard)
    ),
    Vector(
      Cell(2, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(2, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(2, 2, Cell.PASSAGE, SurfaceType.Standard)
    )
  )
)

val smallSquareTestMaze = Maze(
  3,
  3,
  Vector(
    Vector(
      Cell(0, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 2, Cell.WALL, SurfaceType.Standard)
    ),
    Vector(
      Cell(1, 0, Cell.WALL, SurfaceType.Standard),
      Cell(1, 1, Cell.WALL, SurfaceType.Standard),
      Cell(1, 2, Cell.WALL, SurfaceType.Standard)
    ),
    Vector(
      Cell(2, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(2, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(2, 2, Cell.PASSAGE, SurfaceType.Standard)
    )
  )
)

val simpleSquareTestMazeStartCoordinate = Coordinate(0, 0)
val simpleSquareTestMazeEndCoordinate = Coordinate(2, 2)
val simpleSquareTestMazePath = List(
  Coordinate(0, 0),
  Coordinate(0, 1),
  Coordinate(1, 1),
  Coordinate(2, 1),
  Coordinate(2, 2)
)

val simpleSquareTestMazeDisplayWithoutPath = "  #\n# #\n   "
val simpleSquareTestMazeDisplayWithPath = "**#\n#*#\n **it "

val simpleRectangleTestMaze = Maze(
  4,
  5,
  Vector(
    Vector(
      Cell(0, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 2, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 3, Cell.WALL, SurfaceType.Standard),
      Cell(0, 4, Cell.WALL, SurfaceType.Standard)
    ),
    Vector(
      Cell(1, 0, Cell.WALL, SurfaceType.Standard),
      Cell(1, 1, Cell.WALL, SurfaceType.Standard),
      Cell(1, 2, Cell.PASSAGE, SurfaceType.Standard),
      Cell(1, 3, Cell.PASSAGE, SurfaceType.Standard),
      Cell(1, 4, Cell.PASSAGE, SurfaceType.Standard)
    ),
    Vector(
      Cell(2, 0, Cell.WALL, SurfaceType.Standard),
      Cell(2, 1, Cell.WALL, SurfaceType.Standard),
      Cell(2, 2, Cell.WALL, SurfaceType.Standard),
      Cell(2, 3, Cell.WALL, SurfaceType.Standard),
      Cell(2, 4, Cell.PASSAGE, SurfaceType.Standard)
    ),
    Vector(
      Cell(3, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(3, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(3, 2, Cell.PASSAGE, SurfaceType.Standard),
      Cell(3, 3, Cell.PASSAGE, SurfaceType.Standard),
      Cell(3, 4, Cell.PASSAGE, SurfaceType.Standard)
    )
  )
)

val simpleRectangleTestMazeStartCoordinate = Coordinate(0, 0)
val simpleRectangleTestMazeEndCoordinate = Coordinate(3, 0)
val simpleRectangleTestMazePath = List(
  Coordinate(0, 0),
  Coordinate(0, 1),
  Coordinate(0, 2),
  Coordinate(1, 2),
  Coordinate(1, 3),
  Coordinate(1, 4),
  Coordinate(2, 4),
  Coordinate(3, 4),
  Coordinate(3, 3),
  Coordinate(3, 2),
  Coordinate(3, 1),
  Coordinate(3, 0)
)

val simpleRectangleTestMazeDisplayWithoutPath = "   ##\n##   \n#### \n     "
val simpleRectangleTestMazeDisplayWithPath = "***##\n##***\n####*\n*****"

val testMazeWithDifferentSurfaceTypes = Maze(
  3,
  3,
  Vector(
    Vector(
      Cell(0, 0, Cell.PASSAGE, SurfaceType.Coin),
      Cell(0, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(0, 2, Cell.WALL, SurfaceType.Standard)
    ),
    Vector(
      Cell(1, 0, Cell.WALL, SurfaceType.Standard),
      Cell(1, 1, Cell.PASSAGE, SurfaceType.Sand),
      Cell(1, 2, Cell.PASSAGE, SurfaceType.Coin)
    ),
    Vector(
      Cell(2, 0, Cell.PASSAGE, SurfaceType.Standard),
      Cell(2, 1, Cell.PASSAGE, SurfaceType.Standard),
      Cell(2, 2, Cell.PASSAGE, SurfaceType.Standard)
    )
  )
)

val testMazeWithDifferentSurfaceTypesStartCoordinate = Coordinate(0, 0)
val testMazeWithDifferentSurfaceTypesEndCoordinate = Coordinate(2, 2)
val testMazeWithDifferentSurfaceTypesPath = List(
  Coordinate(0, 0),
  Coordinate(0, 1),
  Coordinate(1, 1),
  Coordinate(1, 2),
  Coordinate(2, 2)
)

val testMazeWithDifferentSurfaceTypesDisplay = "$ #\n#~$\n   "

def captureOutput(f: => Any): String = {
  val stream = new ByteArrayOutputStream()
  val oldOut = System.out
  Console.withOut(PrintStream(stream)) {
    f
  }
  System.setOut(oldOut)
  stream.toString
}

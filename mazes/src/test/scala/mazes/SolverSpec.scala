package mazes

import org.scalatest.funsuite.AnyFunSuite

class SolverSpec extends AnyFunSuite {
  test(
    s"BFSSolver should find path for simpleSquareTestMaze"
  ) {
    assert(
      BFSSolver
        .solve(
          simpleSquareTestMaze,
          simpleSquareTestMazeStartCoordinate,
          simpleSquareTestMazeEndCoordinate
        )
        .contains(simpleSquareTestMazePath)
    )
  }

  test(
    s"DijkstraSolver should find path for simpleSquareTestMaze"
  ) {
    assert(
      DijkstraSolver
        .solve(
          simpleSquareTestMaze,
          simpleSquareTestMazeStartCoordinate,
          simpleSquareTestMazeEndCoordinate
        )
        .contains(simpleSquareTestMazePath)
    )
  }

  test(
    s"BFSSolver should not find path for smallSquareTestMaze"
  ) {
    assert(
      BFSSolver
        .solve(
          smallSquareTestMaze,
          simpleSquareTestMazeStartCoordinate,
          simpleSquareTestMazeEndCoordinate
        )
        .isEmpty
    )
  }

  test(
    s"DijkstraSolver should not find path for smallSquareTestMaze"
  ) {
    assert(
      DijkstraSolver
        .solve(
          smallSquareTestMaze,
          simpleSquareTestMazeStartCoordinate,
          simpleSquareTestMazeEndCoordinate
        )
        .isEmpty
    )
  }

  test(
    s"BFSSolver should find path for simpleRectangleTestMaze"
  ) {
    assert(
      BFSSolver
        .solve(
          simpleRectangleTestMaze,
          simpleRectangleTestMazeStartCoordinate,
          simpleRectangleTestMazeEndCoordinate
        )
        .contains(simpleRectangleTestMazePath)
    )
  }

  test(
    s"DijkstraSolver should find path for simpleRectangleTestMaze"
  ) {
    assert(
      DijkstraSolver
        .solve(
          simpleRectangleTestMaze,
          simpleRectangleTestMazeStartCoordinate,
          simpleRectangleTestMazeEndCoordinate
        )
        .contains(simpleRectangleTestMazePath)
    )
  }

  test(
    s"DijkstraSolver should find shortest path for maze with sand and coin"
  ) {
    assert(
      DijkstraSolver
        .solve(
          testMazeWithDifferentSurfaceTypes,
          testMazeWithDifferentSurfaceTypesStartCoordinate,
          testMazeWithDifferentSurfaceTypesEndCoordinate
        )
        .contains(testMazeWithDifferentSurfaceTypesPath)
    )
  }
}

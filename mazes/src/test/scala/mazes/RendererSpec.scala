package mazes

import org.scalatest.funsuite.AnyFunSuite

class RendererSpec extends AnyFunSuite {
  test(
    s"Renderer should correct display simpleSquareTestMaze without path"
  ) {
    assert(
      DefaultRenderer.render(
        simpleSquareTestMaze
      ) == simpleSquareTestMazeDisplayWithoutPath
    )
  }

  test(
    s"Renderer should correct display simpleRectangleTestMaze without path"
  ) {
    assert(
      DefaultRenderer.render(
        simpleRectangleTestMaze
      ) == simpleRectangleTestMazeDisplayWithoutPath
    )
  }

  test(
    s"Renderer should correct display simpleSquareTestMaze with path"
  ) {
    assert(
      DefaultRenderer.render(
        simpleSquareTestMaze,
        simpleSquareTestMazePath
      ) == simpleSquareTestMazeDisplayWithPath
    )
  }

  test(
    s"Renderer should correct display simpleRectangleTestMaze with path"
  ) {
    assert(
      DefaultRenderer.render(
        simpleRectangleTestMaze,
        simpleRectangleTestMazePath
      ) == simpleRectangleTestMazeDisplayWithPath
    )
  }

  test(
    s"Renderer should display different surface types correctly"
  ) {
    assert(
      DefaultRenderer.render(
        testMazeWithDifferentSurfaceTypes
      ) == testMazeWithDifferentSurfaceTypesDisplay
    )
  }

}

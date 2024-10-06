package mazes

import org.scalatest.funsuite.AnyFunSuite

class InputUtilsSpec extends AnyFunSuite {
  test(
    s"readInt should return valid int within limits"
  ) {
    Array
      .range(1, 200)
      .foreach(elem =>
        Console.withIn(new java.io.ByteArrayInputStream(s"$elem\n".getBytes)) {
          assert(InputUtils.readInt("", Int.MinValue, Int.MaxValue) == elem)
        }
      )
  }

  test(
    "readInt should prompt for valid input if input is outside of range"
  ) {
    Array
      .concat(Array.range(-200, 0), Array.range(11, 200))
      .foreach(elem =>
        val result = captureOutput {
          Console
            .withIn(new java.io.ByteArrayInputStream(s"$elem\n5".getBytes)) {
              InputUtils.readInt("", 1, 10)
            }
        }
        assert(
          result
            .contains("Incorrect input. Number should be between 1 and 10.")
        )
      )
  }

  test(
    "readInt should prompt for valid input if input is not a number"
  ) {
    "abcdefghijklmnopqrstuvwxyz".toCharArray.foreach(elem =>
      val result = captureOutput {
        Console
          .withIn(new java.io.ByteArrayInputStream(s"$elem\n5".getBytes)) {
            InputUtils.readInt("", 1, 10)
          }
      }
      assert(result.contains("Incorrect input. Please enter a number."))
    )
  }

  test(
    "readInt should prompt for odd number if oddFlag is true"
  ) {
    Array
      .concat(Array.range(-200, 200, 2))
      .foreach(elem =>
        val result = captureOutput {
          Console
            .withIn(new java.io.ByteArrayInputStream(s"$elem\n1".getBytes)) {
              InputUtils.readInt("", -200, 200, true)
            }
        }
        assert(
          result
            .contains(
              "Incorrect input. Number should be odd and between -200 and 200."
            )
        )
      )
  }

  test(
    "readCoordinates should return valid coordinates for passages coordinates"
  ) {
    simpleSquareTestMaze.grid.flatten
      .filter(cell => cell.cellType == Cell.PASSAGE)
      .map(cell => (cell.row, cell.col))
      .foreach { coordinate =>
        Console
          .withIn(
            new java.io.ByteArrayInputStream(
              s"${coordinate._1 + 1}\n${coordinate._2 + 1}".getBytes
            )
          ) {
            assert(
              InputUtils.readCoordinates(
                "",
                simpleSquareTestMaze
              ) == Coordinate(coordinate._1, coordinate._2)
            )
          }
      }
  }

  test(
    "readCoordinates should prompt if coordinate is a wall"
  ) {
    simpleSquareTestMaze.grid.flatten
      .filter(cell => cell.cellType == Cell.WALL)
      .map(cell => (cell.row, cell.col))
      .foreach { coordinate =>
        val result = captureOutput {
          Console
            .withIn(
              new java.io.ByteArrayInputStream(
                s"${coordinate._1 + 1}\n${coordinate._2 + 1}\n1\n1".getBytes
              )
            ) {
              InputUtils.readCoordinates("", simpleSquareTestMaze)
            }
        }
        assert(
          result
            .contains("This coordinate is a wall. Choose a correct one:")
        )
      }
  }

  test(
    "readSurfaceChoice should return true if user enters 1"
  ) {
    Console
      .withIn(new java.io.ByteArrayInputStream("1\n".getBytes)) {
        assert(InputUtils.readSurfaceChoice())
      }
  }

  test(
    "readSurfaceChoice should return false if user enters 2"
  ) {
    Console
      .withIn(new java.io.ByteArrayInputStream("2\n".getBytes)) {
        assert(!InputUtils.readSurfaceChoice())
      }
  }
}

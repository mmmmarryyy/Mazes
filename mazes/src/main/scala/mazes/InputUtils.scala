package mazes

object InputUtils {
  def readInt(
      message: String,
      lowerLimit: Int,
      upperLimit: Int,
      oddFlag: Boolean = false
  ): Int = {
    println(message)

    val input = scala.io.StdIn.readLine()

    try {
      val inputInt = input.toInt
      if (
        inputInt >= lowerLimit && inputInt <= upperLimit && ((oddFlag && inputInt % 2 != 0) || !oddFlag)
      ) {
        inputInt
      } else {
        readInt(
          s"Incorrect input. Number should be ${if (oddFlag) "odd and " else ""}between $lowerLimit and $upperLimit.",
          lowerLimit,
          upperLimit,
          oddFlag
        )
      }
    } catch {
      case _: NumberFormatException =>
        readInt(
          "Incorrect input. Please enter a number.",
          lowerLimit,
          upperLimit,
          oddFlag
        )
    }
  }

  def readCoordinates(message: String, maze: Maze): Coordinate = {
    println(message)
    val row = readInt(
      s"Enter a row (number from 1 to ${maze.height} inclusively):",
      1,
      maze.height
    ) - 1
    val col = readInt(
      s"Enter a column (number from 1 to ${maze.width} inclusively):",
      1,
      maze.width
    ) - 1
    val inputCoordinate = Coordinate(row, col)
    if (maze.isPassable(inputCoordinate)) {
      inputCoordinate
    } else {
      readCoordinates("This coordinate is a wall. Choose a correct one:", maze)
    }
  }

  def readSurfaceChoice(): Boolean = {
    readInt(
      "Do you want to use different surface types?\n1. Yes\n2. No",
      1,
      2
    ) == 1
  }

}

package mazes

val directions = List( // под эти движения тоже можно было создать ADT
  Coordinate(-1, 0), // Up
  Coordinate(1, 0), // Down
  Coordinate(0, -1), // Left
  Coordinate(0, 1) // Right
)

val minMazeSize = 9
val maxMazeSize = 21

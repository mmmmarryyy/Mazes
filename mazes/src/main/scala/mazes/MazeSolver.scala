package mazes

import scala.annotation.tailrec

/** Trait for maze solvers.
  */
trait Solver {

  /** Find a path from a given starting point to a given ending point.
    *
    * @param maze
    *   Maze.
    * @param start
    *   Start point.
    * @param end
    *   End point.
    * @return
    *   List of path coordinates if path found, otherwise None.
    */
  def solve(
      maze: Maze,
      start: Coordinate,
      end: Coordinate
  ): Option[List[Coordinate]]
}

/** Breadth-first search (BFS) maze solver.
  */
object BFSSolver extends Solver {
  override def solve(
      maze: Maze,
      start: Coordinate,
      end: Coordinate
  ): Option[List[Coordinate]] = {
    @tailrec
    def bfs(
        paths: List[List[Coordinate]],
        visited: Set[Coordinate]
    ): Option[List[Coordinate]] = {
      paths match {
        case Nil => None

        case path :: rest =>
          val current = path.last

          if (current == end) { // Check if we reached the end
            Some(path)
          } else {
            val neighbors = for {
              direction <- directions
              neighbor = Coordinate(
                current.row + direction.row,
                current.col + direction.col
              )
              if maze.isPassable(neighbor) && !visited.contains(neighbor)
            } yield neighbor

            val newPaths = neighbors.map(neighbor => path :+ neighbor)
            bfs(rest ++ newPaths, visited ++ neighbors)
          }
      }
    }

    if (!maze.isPassable(start) || !maze.isPassable(end)) { // Check if start and end are valid (not walls)
      None
    } else {
      bfs(List(List(start)), Set(start))
    }
  }
}

/** Maze solver using Dijkstra's algorithm.
  */
object DijkstraSolver extends Solver {
  override def solve(
      maze: Maze,
      start: Coordinate,
      end: Coordinate
  ): Option[List[Coordinate]] = {
    @tailrec
    def dijkstra(
        visited: Set[Coordinate],
        distances: Map[Coordinate, Int],
        parents: Map[Coordinate, Coordinate],
        queue: List[Coordinate]
    ): Option[List[Coordinate]] = {
      if (queue.isEmpty) {
        None
      } else {
        val current = queue.head
        if (current == end) {
          Some(reconstructPath(parents, end))
        } else {
          val neighbors = for {
            direction <- directions
            neighbor = Coordinate(
              current.row + direction.row,
              current.col + direction.col
            )
            if maze.isPassable(neighbor) && !visited.contains(neighbor)
          } yield neighbor

          val updatedDistances = neighbors.map { neighbor =>
            val currentDistanceToNeighbor =
              distances.getOrElse(neighbor, Int.MaxValue)
            val tentativeDistance =
              distances(current) + 1 + (maze.getCellType(neighbor) match {
                case SurfaceType.Sand => 1
                case SurfaceType.Coin => -1
                case _                => 0
              })
            if (tentativeDistance < currentDistanceToNeighbor) {
              (neighbor, tentativeDistance)
            } else {
              (neighbor, currentDistanceToNeighbor)
            }
          }.toMap

          val newDistances = distances ++ updatedDistances
          val newParents = parents ++ neighbors
            .filter(neighbor =>
              updatedDistances(neighbor) < distances
                .getOrElse(neighbor, Int.MaxValue)
            )
            .map(neighbor => (neighbor, current))
            .toMap
          val newQueue = (queue.tail ++ neighbors).sortBy(newDistances(_))

          dijkstra(
            visited + current,
            newDistances,
            newParents,
            newQueue
          )
        }
      }
    }

    def reconstructPath(
        parents: Map[Coordinate, Coordinate],
        current: Coordinate
    ): List[Coordinate] = {
      if (parents.contains(current)) {
        reconstructPath(parents, parents(current)) :+ current
      } else {
        List(current)
      }
    }

    if (!maze.isPassable(start) || !maze.isPassable(end)) { // Check if start and end are valid (not walls)
      None
    } else {
      dijkstra(Set.empty, Map(start -> 0), Map.empty, List(start))
    }
  }
}

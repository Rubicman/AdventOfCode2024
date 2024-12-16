package day16

import day16.Direction.RIGHT
import solution
import java.util.*

fun main() = solution(16) { maze ->
  var start = 0 to 0

  for (i in maze.indices) {
    for (j in maze[0].indices) {
      if (maze[i][j] == 'S') start = i to j
    }
  }

  val queue = PriorityQueue<Step>(Comparator.comparing { it.points })
  queue.add(Step(start.first, start.second, RIGHT, 0))
  val used = mutableSetOf<Triple<Int, Int, Direction>>()

  while (queue.isNotEmpty()) {
    val current = queue.remove()
    if (current.triple in used) continue
    used.add(current.triple)

    val (i, j, direction, points) = current
    if (maze[i][j] == '#') continue
    if (maze[i][j] == 'E') {
      println(points)
      return@solution
    }

    val (nextI, nextJ) = direction.next(i, j)
    queue.add(Step(nextI, nextJ, direction, points + 1))
    direction.rotation.forEach { newDirection -> queue.add(Step(i, j, newDirection, points + 1000)) }
  }
}

enum class Direction {
  UP, DOWN, LEFT, RIGHT;

  val rotation: List<Direction>
    get() = when (this) {
      UP, DOWN -> listOf(LEFT, RIGHT)
      LEFT, RIGHT -> listOf(UP, DOWN)
    }

  fun next(i: Int, j: Int): Pair<Int, Int> = when (this) {
    UP -> i - 1 to j
    DOWN -> i + 1 to j
    LEFT -> i to j - 1
    RIGHT -> i to j + 1
  }
}

data class Step(val i: Int, val j: Int, val direction: Direction, val points: Long) {
  val triple
    get() = Triple(i, j, direction)
}
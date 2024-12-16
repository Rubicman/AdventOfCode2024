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

  val from = mutableMapOf<Step, MutableList<Step>>()
  val result = mutableSetOf<Pair<Int, Int>>()
  fun dfs(step: Step) {
    result.add(step.i to step.j)
    from[step]?.forEach { dfs(it) }
  }

  while (queue.isNotEmpty()) {
    val current = queue.remove()
    if (current.triple in used) continue
    used.add(current.triple)

    val (i, j, direction, points) = current
    if (maze[i][j] == '#') continue
    if (maze[i][j] == 'E') {
      dfs(current)
      println(result.size)
      return@solution
    }

    val (nextI, nextJ) = direction.next(i, j)
    val nextStep = Step(nextI, nextJ, direction, points + 1)
    queue.add(nextStep)
    from.putIfAbsent(nextStep, mutableListOf())
    from.getValue(nextStep).add(current)
    direction
      .rotation
      .map { newDirection -> Step(i, j, newDirection, points + 1000) }
      .forEach { step ->
        from.putIfAbsent(step, mutableListOf())
        from.getValue(step).add(current)
        queue.add(step)
      }
  }

}
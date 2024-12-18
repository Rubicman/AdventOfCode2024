package day18

import solution

fun main() = solution(18) { lines ->
  val n = 70
  val bytes = lines.map { line -> line.split(",").map(String::toInt).let { it[0] to it[1] } }
  val fallenBytes = 1024

  val blockedCells = bytes.take(fallenBytes).toSet()
  val distances = mutableMapOf<Pair<Int, Int>, Int>()
  distances[0 to 0] = 0
  val queue = ArrayDeque(listOf(0 to 0))

  while (queue.isNotEmpty()) {
    val (x, y) = queue.removeFirst()
    val distance = distances.getValue(x to y)

    listOf(x - 1 to y, x + 1 to y, x to y - 1, x to y + 1)
      .asSequence()
      .filter { it.first >= 0 }
      .filter { it.first <= n }
      .filter { it.second >= 0 }
      .filter { it.second <= n }
      .filter { it !in distances }
      .filter { it !in blockedCells }
      .forEach {
        distances[it] = distance + 1
        queue.add(it)
      }
  }

  println(distances[n to n])
}
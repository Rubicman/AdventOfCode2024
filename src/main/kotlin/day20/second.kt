package day20

import nextCells
import solution
import kotlin.math.abs


fun main() = solution(20) { maze ->
  val distanceGap = 100
  val cheatDuration = 20
  val n = maze.size
  val m = maze[0].length
  val distances = Array(n) { IntArray(m) { Int.MAX_VALUE } }

  var start = 0 to 0
  for (i in 0..<n) {
    for (j in 0..<m) {
      if (maze[i][j] == 'S') start = i to j
    }
  }
  distances[start.first][start.second] = 0

  val queue = ArrayDeque(listOf(start))
  while (queue.isNotEmpty()) {
    val (i, j) = queue.removeFirst()
    nextCells(i, j)
      .filter { maze[it.first][it.second] != '#' }
      .filter { distances[it.first][it.second] == Int.MAX_VALUE }
      .forEach { (newI, newJ) ->
        distances[newI][newJ] = distances[i][j] + 1
        queue.add(newI to newJ)
      }
  }

  var result = 0

  for (i1 in 1..n - 2) {
    for (j1 in 1..m - 2) {
      if (distances[i1][j1] == Int.MAX_VALUE) continue

      for (i2 in 1..n - 2) {
        for (j2 in 1..m - 2) {
          if (distances[i2][j2] == Int.MAX_VALUE) continue
          val cheatDistance = abs(i1 - i2) + abs(j1 - j2)
          if (cheatDistance > cheatDuration) continue

          if (distances[i2][j2] - distances[i1][j1] - cheatDistance >= distanceGap) result++
        }
      }
    }
  }

  println(result)
}

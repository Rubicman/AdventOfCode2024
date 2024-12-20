package day20

import nextCells
import solution


fun main() = solution(20) { maze ->
  val distanceGap = 100
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
  for (i in 1..n - 2) {
    for (j in 1..m - 2) {
      if (maze[i][j] != '#') continue

      for ((i1, j1) in nextCells(i, j)) {
        val distance1 = distances[i1][j1]
        if (distance1 == Int.MAX_VALUE) continue

        for ((i2, j2) in nextCells(i, j)) {
          val distance2 = distances[i2][j2]
          if (distance2 == Int.MAX_VALUE) continue

          if (distance1 - distance2 - 1 >= distanceGap) result++

        }
      }
    }
  }

  println(result)
}
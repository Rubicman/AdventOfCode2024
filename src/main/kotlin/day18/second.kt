package day18

import solution

fun main() = solution(18) { lines ->
  val n = 70
  val bytes = lines
    .map { line ->
      line
        .split(",")
        .map(String::toInt)
        .let { it[0] to it[1] }
    }
    .toSet()

  val map = Array(n + 1) { Array(n + 1) { 0 } }
  val colorCounts = Array((n + 1) * (n + 1)) { 1 }

  for (i in 0..n) {
    for (j in 0..n) {
      map[i][j] = if (i to j in bytes) -1 else i * (n + 1) + j
    }
  }

  fun dfs(i: Int, j: Int) {
    val nextPoints = listOf(i - 1 to j, i + 1 to j, i to j - 1, i to j + 1)
      .filter { it.first in 0..n && it.second in 0..n && map[it.first][it.second] != -1 }

    nextPoints.forEach { (x, y) ->
      if (colorCounts[map[x][y]] >= colorCounts[map[i][j]]) {
        colorCounts[map[x][y]]++
        colorCounts[map[i][j]]--
        map[i][j] = map[x][y]
      }
    }

    nextPoints.forEach { (x, y) -> if (map[x][y] != map[i][j]) dfs(x, y) }
  }

  for (i in 0..n) {
    for (j in 0..n) {
      if (map[i][j] != -1) dfs(i, j)
    }
  }

  for ((i, j) in bytes.reversed()) {
    map[i][j] = i * (n + 1) + j
    dfs(i, j)
    if (map[0][0] == map[n][n]) {
      println("$i,$j")
      break
    }
  }
}
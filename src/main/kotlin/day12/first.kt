package day12

import solution

fun main() = solution(12) { map ->
  val visited = List(map.size) { MutableList(map[0].length) { false } }

  fun dfs(i: Int, j: Int): Pair<Int, Int> {
    if (visited[i][j]) return 0 to 0
    visited[i][j] = true

    return listOf(i - 1 to j, i + 1 to j, i to j - 1, i to j + 1).map { (newI, newJ) ->
      if (newI in map.indices && newJ in map[0].indices && map[newI][newJ] == map[i][j])
        dfs(newI, newJ)
      else 0 to 1
    }.reduce { (accFirst, accSecond), (first, second) -> accFirst + first to accSecond + second }
      .let { it.first + 1 to it.second }
  }

  var result = 0L
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (!visited[i][j]) dfs(i, j).let { result += it.first * it.second }
    }
  }
  println(result)
}
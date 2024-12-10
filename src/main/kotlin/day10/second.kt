package day10

import solution

fun main() = solution(10) { lines ->
  val map = lines.map { line -> line.map(Char::digitToInt) }

  suspend fun SequenceScope<Pair<Int, Int>>.dfs(i: Int, j: Int) {
    val value = map[i][j]
    if (value == 9) {
      yield(i to j)
      return
    }

    listOf(i - 1 to j, i + 1 to j, i to j - 1, i to j + 1).forEach { (newI, newJ) ->
      if (newI in map.indices && newJ in map.indices && map[newI][newJ] == value + 1) dfs(newI, newJ)
    }
  }

  var result = 0
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] == 0)
        result += sequence { dfs(i, j) }.count()
    }
  }

  println(result)
}
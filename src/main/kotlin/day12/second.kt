package day12

import solution

fun main() = solution(12) { map ->
  val visited = List(map.size) { MutableList(map[0].length) { false } }

  suspend fun SequenceScope<Triple<Int, Int, Int>>.dfs(i: Int, j: Int): Int {
    if (visited[i][j]) return 0
    visited[i][j] = true

    return listOf(i - 1 to j, i + 1 to j, i to j - 1, i to j + 1).mapIndexed { index, (newI, newJ) ->
      if (newI in map.indices && newJ in map[0].indices && map[newI][newJ] == map[i][j]) {
        dfs(newI, newJ)
      } else {
        yield(Triple(newI, newJ, index))
        0
      }
    }.sum() + 1
  }

  var result = 0L
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (visited[i][j]) continue
      var area = 0
      val borders = sequence {
        area = dfs(i, j)
      }.toList()

      var perimeter = borders
        .filter { it.third < 2 }
        .groupBy { it.first to it.third }
        .values
        .sumOf { group ->
          group
            .map { it.second }
            .sorted()
            .zipWithNext()
            .count { it.first + 1 < it.second } + 1
        }

      perimeter += borders
        .filter { it.third >= 2 }
        .groupBy { it.second to it.third }
        .values
        .sumOf { group ->
          group
            .map { it.first }
            .sorted()
            .zipWithNext()
            .count { it.first + 1 < it.second } + 1
        }

      result += perimeter * area
    }
  }
  println(result)
}
package day19

import solution

fun main() = solution(19) { lines ->
  val towels = lines[0].split(", ")

  val memo = mutableMapOf<Pair<String, Int>, Long>()
  fun dfs(design: String, index: Int): Long {
    if (index == design.length) return 1L
    if (design to index in memo) return memo.getValue(design to index)

    var count = 0L
    for (towel in towels) {
      val end = index + towel.length

      if (end > design.length) continue
      if (towel != design.substring(index, index + towel.length)) continue
      count += dfs(design, index + towel.length)
    }

    memo[design to index] = count
    return count
  }

  lines.drop(2).sumOf { dfs(it, 0) }.let(::println)
}
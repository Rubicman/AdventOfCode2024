package day11

import solution

fun main() = solution(11) { lines ->
  lines.first().split(" ").map { it.toLong() }.sumOf { dfs(it, 25) }.let { println(it) }
}

private val answers = mutableMapOf<Pair<Long, Int>, Long>()

fun dfs(stone: Long, leftDeep: Int): Long {
  if (leftDeep == 0) return 1L
  if (stone to leftDeep in answers) return answers.getValue(stone to leftDeep)
  val stoneStr = stone.toString()

  val answer = when {
    stone == 0L -> dfs(1, leftDeep - 1)
    stoneStr.length % 2 == 0 -> dfs(stoneStr.substring(0, stoneStr.length / 2).toLong(), leftDeep - 1) +
      dfs(stoneStr.substring(stoneStr.length / 2).toLong(), leftDeep - 1)

    else -> dfs(stone * 2024, leftDeep - 1)
  }

  answers[stone to leftDeep] = answer
  return answer
}
package day11

import solution

fun main() = solution(11) { lines ->
  lines.first().split(" ").map { it.toLong() }.sumOf { dfs(it, 75) }.let { println(it) }
}
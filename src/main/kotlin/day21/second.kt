package day21

import solution

val getComplexCommandsMemo = mutableMapOf<List<Any>, Long>()
fun getComplexCommands(code: String, deep: Int): Long {
  val memoKey = listOf(code, deep)

  return getComplexCommandsMemo.getOrPut(memoKey) {
    if (deep == 0) return@getOrPut code.length.toLong()

    val moves = getDirectionalCommands(code)
    moves
      .split("A")
      .dropLast(1)
      .map { it + "A" }
      .sumOf { getComplexCommands(it, deep - 1) }
  }
}

fun main() = solution(21) { lines ->
  lines.sumOf { line ->
    getComplexCommands(getNumericCommands(line), 25) * line.take(3).toLong()
  }.let(::println)
}
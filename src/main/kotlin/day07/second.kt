package day07

import solution

fun main() = solution(7) { lines ->
  lines
    .map { it.split(": ") }
    .map { (result, numbers) -> result.toLong() to numbers.split(" ").map { it.toLong() } }
    .filter { (result, numbers) ->
      var currentResults = listOf(numbers[0])
      numbers.drop(1).forEach { number ->
        val withAdd = currentResults.map { it + number }
        val withMultiply = currentResults.map { it * number }
        val withConcatenation = currentResults.map { "$it$number".toLong() }
        currentResults = withAdd + withMultiply + withConcatenation
      }

      currentResults.contains(result)
    }
    .sumOf { (result, _) -> result }
    .let { println(it) }
}
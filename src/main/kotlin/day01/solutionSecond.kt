package day01

import solution

fun main() = solution(1) { lines ->
  val firstList = mutableListOf<Long>()
  val secondList = mutableListOf<Long>()

  lines
    .map { it.split("   ").map(String::toLong) }
    .forEach { (first, second) ->
      firstList += first
      secondList += second
    }

  val secondMap = secondList.groupingBy { it }.eachCount()
  val result = firstList.sumOf { it * (secondMap[it] ?: 0) }

  println(result)
}
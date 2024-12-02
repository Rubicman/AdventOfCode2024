package day01

import solution
import kotlin.math.abs

fun main() = solution(1) { lines ->
  val firstList = mutableListOf<Long>()
  val secondList = mutableListOf<Long>()

  lines
    .map { it.split("   ").map(String::toLong) }
    .forEach { (first, second) ->
      firstList += first
      secondList += second
    }

  firstList.sort()
  secondList.sort()

  val result = firstList.zip(secondList).sumOf { (first, second) -> abs(first - second) }

  println(result)
}
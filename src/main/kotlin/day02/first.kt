package day02

import solution
import kotlin.math.abs

fun main() = solution(2) { lines ->
  lines
    .map { line -> line.split(" ").map(String::toLong) }
    .map { report -> report.zipWithNext().map { it.first - it.second } }
    .filter { reportDiffers -> reportDiffers.all { abs(it) <= 3 } }
    .filter { reportDiffers -> reportDiffers.all { it < 0 } || reportDiffers.all { it > 0 } }
    .count()
    .let { println(it) }
}
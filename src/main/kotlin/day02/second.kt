package day02

import solution
import kotlin.math.abs

fun main() = solution(2) { lines ->
  lines
    .map { line -> line.split(" ").map(String::toLong) }
    .count { checkReportWithPass(it) }
    .let { println(it) }
}

private fun checkReportWithPass(report: List<Long>): Boolean {
  if (checkReport(report)) return true

  for (i in report.indices) {
    if (checkReport(report.slice(0 ..< i) + report.slice(i + 1 ..< report.size))) return true
  }

  return false
}

private fun checkReport(report: List<Long>): Boolean {
  val diffs = report.zipWithNext().map { it.second - it.first }
  return diffs.all { abs(it) <= 3 } && (diffs.all { it < 0 } || diffs.all { it > 0 })
}
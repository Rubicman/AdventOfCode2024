package day03

import solution


fun main() = solution(3) { lines ->
  val operationRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
  var result = 0L
  var matcher = operationRegex.find(lines.joinToString())

  while (matcher != null) {
    result += matcher.groupValues.let { it[1].toLong() * it[2].toLong() }
    matcher = matcher.next()
  }

  println(result)
}

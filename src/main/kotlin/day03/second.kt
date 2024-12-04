package day03

import solution


fun main() = solution(3) { lines ->
  val operationRegex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)")
  var result = 0L
  var matcher = operationRegex.find(lines.joinToString())
  var disabled = false

  while (matcher != null) {
    when (matcher.groupValues[0]) {
      "do()" -> disabled = false
      "don't()" -> disabled = true
      else -> if (!disabled) result += matcher.groupValues.let { it[1].toLong() * it[2].toLong() }
    }
    matcher = matcher.next()
  }

  println(result)
}

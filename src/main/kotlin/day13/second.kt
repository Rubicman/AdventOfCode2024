package day13

import solution
import java.math.BigDecimal.ZERO
import java.math.BigDecimal.valueOf

val EXTRA_VALUE = "10000000000000".toBigDecimal()

fun main() = solution(13) { lines ->
  lines.chunked(4).sumOf { group ->
    val buttonA = BUTTON_REGEXP.matchEntire(group[0])!!.groupValues.let { it[1].toBigDecimal() to it[2].toBigDecimal() }
    val buttonB = BUTTON_REGEXP.matchEntire(group[1])!!.groupValues.let { it[1].toBigDecimal() to it[2].toBigDecimal() }
    val prize =
      PRIZE_REGEXP.matchEntire(group[2])!!.groupValues.let { it[1].toBigDecimal() + EXTRA_VALUE to it[2].toBigDecimal() + EXTRA_VALUE }

    val y = buttonB.first * buttonA.second - buttonB.second * buttonA.first
    val value = prize.first * buttonA.second - prize.second * buttonA.first

    val b = value / y
    val a = (prize.first - buttonB.first * b) / buttonA.first

    if (b < ZERO || a < ZERO) return@sumOf ZERO
    if (a * buttonA.first + b * buttonB.first != prize.first) return@sumOf ZERO
    if (a * buttonA.second + b * buttonB.second != prize.second) return@sumOf ZERO

    return@sumOf a * valueOf(3) + b
  }.let { println(it) }
}
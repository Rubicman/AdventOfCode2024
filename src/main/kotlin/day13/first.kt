package day13

import solution
import java.util.PriorityQueue

val BUTTON_REGEXP = Regex("Button [AB]: X\\+(\\d+), Y\\+(\\d+)")
val PRIZE_REGEXP = Regex("Prize: X=(\\d+), Y=(\\d+)")

fun main() = solution(13) { lines ->
  lines.chunked(4).sumOf { group ->
    val buttonA = BUTTON_REGEXP.matchEntire(group[0])!!.groupValues.let { it[1].toInt() to it[2].toInt() }
    val buttonB = BUTTON_REGEXP.matchEntire(group[1])!!.groupValues.let { it[1].toInt() to it[2].toInt() }
    val prize = PRIZE_REGEXP.matchEntire(group[2])!!.groupValues.let { it[1].toInt() to it[2].toInt() }

    val queue = PriorityQueue<Triple<Int, Int, Int>> { o1, o2 -> o1.third - o2.third }
    queue.add(Triple(0, 0, 0))
    val used = mutableSetOf<Triple<Int, Int, Int>>()

    while (queue.isNotEmpty()) {
      val triple = queue.remove()
      if (triple in used) continue
      used.add(triple)

      val (x, y, price) = triple
      if (x > prize.first || y > prize.second) continue
      if (x == prize.first && y == prize.second) return@sumOf price

      queue.add(Triple(x + buttonA.first, y + buttonA.second, price + 3))
      queue.add(Triple(x + buttonB.first, y + buttonB.second, price + 1))
    }

    return@sumOf 0
  }.let { println(it) }
}
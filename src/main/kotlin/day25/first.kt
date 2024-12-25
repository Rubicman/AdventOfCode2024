package day25

import solution

fun main() = solution(25) { lines ->
  val (keys, locks) = lines
    .chunked(8) { chunk ->
    val type = if (chunk[0][0] == '#') Type.KEY else Type.LOCK
    val pins = (0..4).map { j -> (0..4).count { i -> chunk[i + 1][j] == '#' } }
    Item(type, pins)
  }
    .partition { it.type == Type.KEY }

  var result = 0
  for (key in keys) {
    for (lock in locks) {
      if ((0..4).all { i -> key.pins[i] + lock.pins[i] <= 5 }) result++
    }
  }

  println(result)
}

enum class Type {
  KEY, LOCK
}

data class Item(
  val type: Type,
  val pins: List<Int>,
)
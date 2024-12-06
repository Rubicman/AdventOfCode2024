package day06

import solution

fun main() = solution(6) { map ->

  var startPosition: Position? = null
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] == '^') startPosition = Position(i, j, Direction.UP)
    }
  }

  var position = startPosition ?: return@solution
  val used = mutableSetOf<Position>()

  while (position !in used) {
    used.add(position)
    val (i, j) = position.next

    if (i !in map.indices || j !in map[0].indices) break

    position = if (map[i][j] == '#')
      position.rotated
    else
      position.next
  }

  println(used.map { it.i to it.j }.distinct().size)
}

data class Position(val i: Int, val j: Int, val direction: Direction) {
  val next: Position
    get() = when (direction) {
      Direction.UP -> copy(i = i - 1)
      Direction.DOWN -> copy(i = i + 1)
      Direction.LEFT -> copy(j = j - 1)
      Direction.RIGHT -> copy(j = j + 1)
    }

  val rotated: Position
    get() = copy(direction = direction.next)
}

enum class Direction {
  UP, DOWN, LEFT, RIGHT;

  val next: Direction
    get() = when (this) {
      UP -> RIGHT
      DOWN -> LEFT
      LEFT -> UP
      RIGHT -> DOWN
    }
}
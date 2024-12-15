package day15

import day15.Direction.*
import day15.FlatObjects.*
import solution

fun main() = solution(15) { lines ->
  val iMultiplier = 100
  val splitIndex = lines.indexOfFirst(String::isEmpty)

  val map = FlatMap(lines
    .take(splitIndex)
    .map { it.flatMap(FlatObjects::flatObject).toMutableList() })

  val moves = lines
    .drop(splitIndex + 1)
    .flatMap { it.map(Direction::direction) }

  var robotPosition = 0 to 0
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] == ROBOT) robotPosition = i to j
    }
  }

  moves.forEach { move ->
    if (map.canBeMoved(robotPosition, move, false)) {
      map.canBeMoved(robotPosition, move, true)
      robotPosition = robotPosition.next(move)
    }
  }

  var result = 0
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] != LEFT_BOX) continue

      result += i * iMultiplier + j
    }
  }
  println(result)
}

class FlatMap(private val values: List<MutableList<FlatObjects>>) {
  val indices: IntRange = values.indices

  operator fun get(i: Int) = values[i]

  operator fun get(position: Pair<Int, Int>) = values[position.first][position.second]
  operator fun set(position: Pair<Int, Int>, value: FlatObjects) {
    values[position.first][position.second] = value
  }

  fun canBeMoved(position: Pair<Int, Int>, direction: Direction, doMove: Boolean): Boolean =
    when (get(position)) {
      EMPTY -> true
      WALL -> false
      ROBOT -> {
        val newPosition = position.next(direction)
        if (canBeMoved(newPosition, direction, doMove)) {
          if (doMove) {
            set(newPosition, get(position))
            set(position, EMPTY)
          }
          true
        } else {
          false
        }
      }

      LEFT_BOX -> canBoxBeMoved(position, direction, doMove)
      RIGHT_BOX -> canBoxBeMoved(position.first to position.second - 1, direction, doMove)
    }

  private fun canBoxBeMoved(leftPosition: Pair<Int, Int>, direction: Direction, doMove: Boolean): Boolean {
    val (i, j) = leftPosition
    val move = when (direction) {
      UP -> canBeMoved(i - 1 to j, direction, doMove) && canBeMoved(i - 1 to j + 1, direction, doMove)
      DOWN -> canBeMoved(i + 1 to j, direction, doMove) && canBeMoved(i + 1 to j + 1, direction, doMove)
      LEFT -> canBeMoved(i to j - 1, direction, doMove)
      RIGHT -> canBeMoved(i to j + 2, direction, doMove)
    }
    if (doMove) {
      val rightPosition = i to j + 1
      set(leftPosition, EMPTY)
      set(rightPosition, EMPTY)
      set(leftPosition.next(direction), LEFT_BOX)
      set(rightPosition.next(direction), RIGHT_BOX)
    }
    return move
  }
}

enum class FlatObjects {
  EMPTY, WALL, LEFT_BOX, RIGHT_BOX, ROBOT;

  companion object {
    fun flatObject(char: Char) = when (char) {
      '.' -> listOf(EMPTY, EMPTY)
      '#' -> listOf(WALL, WALL)
      'O' -> listOf(LEFT_BOX, RIGHT_BOX)
      '@' -> listOf(ROBOT, EMPTY)
      else -> throw IllegalArgumentException()
    }
  }
}
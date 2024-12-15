package day15

import day15.Direction.*
import day15.MapObjects.*
import solution

fun main() = solution(15) { lines ->
  val iMultiplier = 100
  val splitIndex = lines.indexOfFirst(String::isEmpty)

  val map = lines
    .take(splitIndex)
    .map { it.map(MapObjects::mapObject).toMutableList() }

  val moves = lines
    .drop(splitIndex + 1)
    .flatMap { it.map(Direction::direction) }

  var robotPosition = 0 to 0
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] == ROBOT) robotPosition = i to j
    }
  }

  fun doMove(position: Pair<Int, Int>, direction: Direction): Boolean = when (map[position.first][position.second]) {
    EMPTY -> true
    WALL -> false
    BOX, ROBOT -> {
      val newPosition = position.next(direction)
      if (doMove(newPosition, direction)) {
        map[newPosition.first][newPosition.second] = map[position.first][position.second]
        map[position.first][position.second] = EMPTY
        true
      } else {
        false
      }
    }
  }

  moves.forEach { move ->
    if (doMove(robotPosition, move)) {
      robotPosition = robotPosition.next(move)
    }
  }

  var result = 0
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] != BOX) continue

      result += i * iMultiplier + j
    }
  }
  println(result)
}

enum class MapObjects {
  EMPTY, WALL, BOX, ROBOT;

  companion object {
    fun mapObject(char: Char): MapObjects = when (char) {
      '.' -> EMPTY
      '#' -> WALL
      'O' -> BOX
      '@' -> ROBOT
      else -> throw IllegalArgumentException()
    }
  }
}

enum class Direction {
  UP, DOWN, LEFT, RIGHT;

  companion object {
    fun direction(char: Char): Direction = when (char) {
      '^' -> UP
      'v' -> DOWN
      '<' -> LEFT
      '>' -> RIGHT
      else -> throw IllegalArgumentException()
    }
  }
}

fun Pair<Int, Int>.next(direction: Direction) = when (direction) {
  UP -> first - 1 to second
  DOWN -> first + 1 to second
  LEFT -> first to second - 1
  RIGHT -> first to second + 1
}
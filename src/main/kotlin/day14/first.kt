package day14

import day14.Robot.Companion.robot
import solution

fun main() = solution(14) { lines ->
  val width = 101L
  val height = 103L
  val seconds = 100L
  lines
    .map { it.robot() }
    .map { robot ->
      var position =
        robot.start.first + robot.velocity.first * seconds to robot.start.second + robot.velocity.second * seconds

      position = position.first % width to position.second % height
      position = position.first + width to position.second + height
      position = position.first % width to position.second % height

      position
    }
    .filter { it.first != width / 2 && it.second != height / 2 }
    .groupingBy { (it.first < width / 2) to (it.second < height / 2) }
    .eachCount()
    .values
    .reduce { acc, count -> acc * count }
    .also(::println)
}

class Robot(
  val start: Pair<Long, Long>,
  val velocity: Pair<Long, Long>,
) {
  companion object {
    private val ROBOT_REGEX = Regex("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)")
    fun String.robot(): Robot {
      val values = ROBOT_REGEX.matchEntire(this)!!.groupValues.drop(1).map(String::toLong)
      return Robot(values[0] to values[1], values[2] to values[3])
    }
  }
}
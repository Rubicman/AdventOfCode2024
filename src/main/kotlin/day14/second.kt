package day14

import day14.Robot.Companion.robot
import solution
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writer

fun main() = solution(14) { lines ->
  val width = 101L
  val height = 103L
  for (seconds in 0L..100_000L) {
    val positions = lines
      .map { it.robot() }
      .map { robot ->
        var position =
          robot.start.first + robot.velocity.first * seconds to robot.start.second + robot.velocity.second * seconds

        position = position.first % width to position.second % height
        position = position.first + width to position.second + height
        position = position.first % width to position.second % height

        position
      }
      .toSet()

    val mayBeTree = positions
      .groupBy { it.second }
      .values
      .map { it.map { (first) -> first }.sorted() }
      .any { group ->
        var k = 1
        for (i in 1 ..< group.size) {
          if (group[i - 1] + 1 == group[i]) {
            k++
          } else {
            k--
          }
          if (k == 30) return@any true
        }
        return@any false
      }

    if (!mayBeTree) continue

    Files.createFile(Path.of("tmp/$seconds")).writer().use { writer ->
      for (i in 0..<height) {
        for (j in 0..<width) {
          writer.write(if (j to i in positions) "#" else " ")
        }
        writer.appendLine()
      }
    }
  }
}

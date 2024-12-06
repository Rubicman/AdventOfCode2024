package day06

import solution

fun main() = solution(6) { map ->
  var startPosition: Position? = null
  for (i in map.indices) {
    for (j in map[0].indices) {
      if (map[i][j] == '^') startPosition = Position(i, j, Direction.UP)
    }
  }
  var answer = map.size * map[0].length

  for (iBlock in map.indices) {
    for (jBlock in map[0].indices) {
      val used = mutableSetOf<Position>()
      var position = startPosition ?: continue

      if (map[iBlock][jBlock] != '.') {
        answer--
        continue
      }

      while (position !in used) {
        used.add(position)
        val (i, j) = position.next

        if (i !in map.indices || j !in map[0].indices) {
          answer--
          break
        }

        position = if (map[i][j] == '#' || (i == iBlock && j == jBlock))
          position.rotated
        else
          position.next
      }
    }
  }

  println(answer)
}
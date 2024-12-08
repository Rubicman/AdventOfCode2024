package day08

import solution

fun main() = solution(8) { lines ->
  val points = sequence {
    for (i in lines.indices) {
      for (j in lines[0].indices) {
        val kind = lines[i][j]
        if (kind != '.') yield(Point(i, j, kind))
      }
    }
  }
    .groupBy { it.kind }
    .map { it.value }

  sequence {
    points.forEach { group ->
      for (a in group.indices) {
        for (b in a + 1..<group.size) {
          val first = group[a]
          val second = group[b]

          val diffI = first.i - second.i
          val diffJ = first.j - second.j

          yield(first.i + diffI to first.j + diffJ)
          yield(second.i - diffI to second.j - diffJ)
        }
      }
    }
  }.filter { it.first in lines.indices && it.second in lines[0].indices }
    .distinct()
    .count()
    .let(::println)
}

class Point(val i: Int, val j: Int, val kind: Char)
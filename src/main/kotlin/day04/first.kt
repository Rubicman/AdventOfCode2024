package day04

import solution

fun main() = solution(4) { lines ->
  var words = 0
  val n = lines.size
  val m = lines[0].length

  for (i in 0..<n) {
    words += search(sequence {
      for (j in 0..<m) yield(lines[i][j])
    })
    words += search(sequence {
      for (j in m - 1 downTo 0) yield(lines[i][j])
    })
  }

  for (j in 0..<m) {
    words += search(sequence {
      for (i in 0..<n) yield(lines[i][j])
    })
    words += search(sequence {
      for (i in n - 1 downTo 0) yield(lines[i][j])
    })
  }

  val subDiagonalRange = 0..m + n - 2
  for (t in subDiagonalRange) {
    words += search(sequence {
      for (i in subDiagonalRange) {
        val j = t - i
        if (i in lines.indices && j in lines[0].indices) yield(lines[i][j])
      }
    })
    words += search(sequence {
      for (i in subDiagonalRange.reversed()) {
        val j = t - i
        if (i in lines.indices && j in lines[0].indices) yield(lines[i][j])
      }
    })
  }

  val mainDiagonalRange = 1 - n..m - 1
  for (t in mainDiagonalRange) {
    words += search(sequence {
      for (i in mainDiagonalRange) {
        val j = t + i
        if (i in lines.indices && j in lines[0].indices) yield(lines[i][j])
      }
    })
    words += search(sequence {
      for (i in mainDiagonalRange.reversed()) {
        val j = t + i
        if (i in lines.indices && j in lines[0].indices) yield(lines[i][j])
      }
    })
  }

  println(words)
}

private val WORD = "XMAS"

private fun search(sequence: Sequence<Char>): Int {
  var words = 0
  var wordChar = 0
  sequence.forEach { char ->
    if (char != WORD[wordChar]) wordChar = 0

    if (char == WORD[wordChar]) {
      wordChar++
      if (wordChar == WORD.length) {
        wordChar = 0
        words++
      }
    }
  }
  return words
}
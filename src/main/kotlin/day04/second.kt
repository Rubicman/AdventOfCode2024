package day04

import solution

fun main() = solution(4) { lines ->
  var words = 0
  val n = lines.size
  val m = lines[0].length

  val wordSet = setOf("MMSS", "MSSM", "SMMS", "SSMM")

  for (i in 0 ..< n - 2) {
    for (j in 0..<m - 2) {
      if (lines[i + 1][j + 1] != 'A') continue

      val word = "${lines[i][j]}${lines[i][j + 2]}${lines[i + 2][j + 2]}${lines[i + 2][j]}"
      if (word in wordSet) words++
    }
  }

  println(words)
}
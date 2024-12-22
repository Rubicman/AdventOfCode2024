package day22

import solution

fun findSubstring(text: String, pattern: String): Int {
  if (pattern.isEmpty()) return 0
  if (text.isEmpty() || pattern.length > text.length) return -1

  val prefix = IntArray(pattern.length)
  var j = 0
  for (i in 1 until pattern.length) {
    while (j > 0 && pattern[i] != pattern[j]) {
      j = prefix[j - 1]
    }
    if (pattern[i] == pattern[j]) {
      j++
    }
    prefix[i] = j
  }

  j = 0
  for (i in text.indices) {
    while (j > 0 && text[i] != pattern[j]) {
      j = prefix[j - 1]
    }
    if (text[i] == pattern[j]) {
      j++
    }
    if (j == pattern.length) {
      return i - j + 1
    }
  }

  return -1
}

fun main() = solution(22) { lines ->
  val allPrices = lines.map { line ->
    var s = line.toLong()
    buildList {
      add((s % 10).toInt())
      repeat(2000) {
        s = next(s)
        add((s % 10).toInt())
      }
    }
  }

  val char = 'A' + 9
  val allDiffs = allPrices.map { prices ->
    prices
      .zipWithNext()
      .map { (a, b) -> b - a }
      .map { char + it }
      .joinToString("")
  }

  var bestResult = 0L
  sequence {
    allDiffs.forEach { diffs ->
      for (i in 4..diffs.length)
        yield(diffs.substring(i - 4, i))
    }
  }.toSet()
    .also { println("total: ${it.size}") }
    .forEach { sequence ->
      var currentResult = 0L
      for (i in allPrices.indices) {
        val j = findSubstring(allDiffs[i], sequence)
        if (j == -1) continue
        currentResult += allPrices[i][j + 4]
      }
      bestResult = maxOf(bestResult, currentResult)
    }

  println(bestResult)
}
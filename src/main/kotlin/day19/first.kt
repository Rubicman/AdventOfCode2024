package day19

import solution

fun main() = solution(19) { lines ->
  val towels = lines[0].split(", ")

  fun dfs(design: String, index: Int): Boolean {
    if (index == design.length) return true

    for (towel in towels) {
      val end = index + towel.length

      if (end > design.length) continue
      if (towel != design.substring(index, index + towel.length)) continue
      if (dfs(design, index + towel.length)) return true
    }

    return false
  }

  lines.drop(2).count { dfs(it, 0) }.let(::println)
}
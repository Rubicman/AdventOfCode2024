package day05

import solution

fun main() = solution(5) { lines ->
  val pagesOrder = mutableMapOf<String, MutableSet<String>>()
  var result = 0L

  lines
    .takeWhile(String::isNotBlank)
    .forEach { line ->
      val (first, second) = line.split("|")
      pagesOrder.putIfAbsent(first, mutableSetOf())
      pagesOrder.getValue(first).add(second)
    }

  lines
    .takeLastWhile(String::isNotBlank)
    .forEach { line ->
      val used = mutableSetOf<String>()
      val pages = line.split(",")
      var valid = true

      pages.forEach { page ->
        val order = pagesOrder[page] ?: emptySet()
        if ((order intersect used).isEmpty()) {
          used.add(page)
        } else {
          valid = false
        }
      }

      if (valid) {
        result += pages[pages.size / 2].toLong()
      }
    }

  println(result)
}
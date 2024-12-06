package day05

import solution

fun main() = solution(5) { lines ->
  val pagesOrder = mutableMapOf<String, MutableSet<String>>()
  var result = 0L

  lines
    .takeWhile(String::isNotBlank)
    .forEach { line ->
      val (first, second) = line.split("|")
      pagesOrder.putIfAbsent(second, mutableSetOf())
      pagesOrder.getValue(second).add(first)
    }

  lines
    .takeLastWhile(String::isNotBlank)
    .forEach { line ->
      val pages = line.split(",").toMutableList()
      val rightOrder = mutableListOf<String>()

      while (pages.isNotEmpty()) {
        for (i in pages.indices) {
          val order = pagesOrder[pages[i]] ?: emptySet()
          if ((pages intersect order).isEmpty()) {
            rightOrder.add(pages[i])
            pages.removeAt(i)
            break
          }
        }
      }

      if (rightOrder.joinToString(",") != line) result += rightOrder[rightOrder.size / 2].toLong()
    }

  println(result)
}
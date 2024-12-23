package day23

import solution

fun main() = solution(23) { lines ->
  val graph = mutableMapOf<String, MutableSet<String>>()

  lines
    .map { it.split("-") }
    .forEach { (v, u) ->
      graph.putIfAbsent(v, mutableSetOf())
      graph.getValue(v).add(u)
      graph.putIfAbsent(u, mutableSetOf())
      graph.getValue(u).add(v)
    }

  var result = 0
  for ((v, vNext) in graph) {
    for (u in vNext) {
      val uNext = graph.getValue(u)
      result += if (v.startsWith('t') || u.startsWith('t'))
        (vNext intersect uNext).size
      else
        (vNext intersect uNext).filter { it.startsWith('t') }.size
    }
  }

  println(result / 6)
}
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

  fun dfs(v: String, filtered: Set<String>): Set<String> {
    val next = (graph.getValue(v) intersect filtered)
    val newFiltered = next.toMutableSet()

    var result = emptySet<String>()
    for (u in next) {
      val current = dfs(u, newFiltered)
      if (current.size == newFiltered.size) return current + v

      if (result.size < current.size) {
        result = current
      }
      newFiltered.remove(u)
    }

    return result + v
  }

  graph
    .keys
    .map { dfs(it, graph.keys) }
    .maxBy { it.size }
    .let { println(it.sorted().joinToString(",")) }
}
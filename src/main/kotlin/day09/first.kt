package day09

import solution

fun main() = solution(9) { lines ->
  val files = lines.first().map { it.code - '0'.code }

  var freeSpace = false
  var id = 0
  val memory = mutableListOf<Int>()

  files.forEach { file ->
    if (freeSpace) {
      repeat(file) { memory.add(-1) }
    } else {
      repeat(file) { memory.add(id) }
      id++
    }
    freeSpace = !freeSpace
  }

  var l = 0
  var r = memory.size - 1
  while (l < r) {
    if (memory[l] != -1) {
      l++
      continue
    }
    if (memory[r] == -1) {
      r--
      continue
    }
    memory[l] = memory[r]
    memory[r] = -1
    l++
    r--
  }

  memory
    .takeWhile { it != -1 }
    .withIndex()
    .sumOf { it.value.toLong() * it.index }
    .let { println(it) }
}
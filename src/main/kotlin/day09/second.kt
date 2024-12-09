package day09

import solution

fun main() = solution(9) { lines ->
  val (files, emptySpaces) = lines
    .first()
    .map { it.code - '0'.code }
    .withIndex()
    .partition { it.index % 2 == 0 }
    .let { (files, emptySpaces) -> files.map { it.value } to emptySpaces.map { it.value }.toMutableList() }
  val memory = mutableListOf<Int>()

  val fileStarts = mutableListOf<Int>()
  val emptySpaceStarts = mutableListOf<Int>()

  for (i in files.indices) {
    fileStarts.add(memory.size)
    repeat(files[i]) {
      memory.add(i)
    }
    if (i in emptySpaces.indices) {
      emptySpaceStarts.add(memory.size)
      repeat(emptySpaces[i]) {
        memory.add(-1)
      }
    }
  }

  for (i in files.indices.reversed()) {
    val file = files[i]
    val fileStart = fileStarts[i]
    val emptySpaceIndex = emptySpaces.withIndex().find { it.value >= file }?.index ?: continue
    val emptySpaceStart = emptySpaceStarts[emptySpaceIndex]

    if (emptySpaceStart > fileStart) continue

    for (j in emptySpaceStart..<emptySpaceStart + file) {
      memory[j] = i
    }
    for (j in fileStart..<fileStart + file) {
      memory[j] = -1
    }

    fileStarts[i] = emptySpaceStart
    emptySpaces[emptySpaceIndex] -= file
    emptySpaceStarts[emptySpaceIndex] += file
  }

  memory
    .withIndex()
    .filter { it.value != -1 }
    .sumOf { it.value.toLong() * it.index }
    .let { println(it) }
}
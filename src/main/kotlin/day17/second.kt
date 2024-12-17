package day17

import solution
fun main() = solution(17) { lines ->
  val program = PROGRAM_REGEX.matchEntire(lines[4])!!.groupValues[1].split(",").map(String::toInt)
  var pointer = 0

  while (pointer + 1 < program.size) {
    val command = program[pointer]
    val literal = program[pointer + 1]
    val combo = if (literal in 4..6) 'A' + literal - 4 else literal

    when (command) {
      0 -> println("A = A / 2^$combo")
      1 -> println("B = B xor $literal")
      2 -> println("B = $combo % 8")
      3 -> println("jmp $literal")
      4 -> println("B = B xor C")
      5 -> println("out $combo % 8")
      6 -> println("B = A / 2^$combo")
      7 -> println("C = A / 2^$combo")
    }

    pointer += 2
  }

  println()

  var aSet = listOf(0L)
  for (result in program.reversed()) {
    val newSet = mutableListOf<Long>()
    aSet.forEach { a ->
      for (b in 0..7) {
        val tempA = a * 8 + b
        var tempB = b xor 1
        val c = (tempA / (1L shl tempB) % 8).toInt()
        tempB = tempB xor c
        tempB = tempB xor 4
        if (tempB == result) {
          newSet.add(tempA)
        }
      }
    }
    aSet = newSet
  }

  println(aSet.sorted())
}

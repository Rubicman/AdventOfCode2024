package day17

import solution

val REGISTER_A_REGEX = "Register A: (\\d+)".toRegex()
val PROGRAM_REGEX = "Program: ([0-7](,[0-7])+)".toRegex()

fun main() = solution(17) { lines ->
  val registers = arrayOf(REGISTER_A_REGEX.matchEntire(lines[0])!!.groupValues[1].toLong(), 0, 0)
  val program = PROGRAM_REGEX.matchEntire(lines[4])!!.groupValues[1].split(",").map(String::toLong)
  var pointer = 0

  sequence {
    while (pointer + 1 < program.size) {
      val command = program[pointer]
      val literal = program[pointer + 1]
      val combo = if (literal in 4..6) registers[literal.toInt() - 4] else literal

      when (command) {
        0L -> registers[0] = registers[0] / (1 shl combo.toInt())
        1L -> registers[1] = registers[1] xor literal
        2L -> registers[1] = combo % 8
        3L -> if (registers[0] != 0L) {
          pointer = literal.toInt()
          continue
        }
        4L -> registers[1] = registers[1] xor registers[2]
        5L -> yield(combo % 8)
        6L -> registers[1] = registers[0] / (1 shl combo.toInt())
        7L -> registers[2] = registers[0] / (1 shl combo.toInt())
      }

      pointer += 2
    }
  }.joinToString(",").let(::println)
}
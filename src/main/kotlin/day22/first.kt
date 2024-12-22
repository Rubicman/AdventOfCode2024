package day22

import solution

const val MODULO = 16777216L

fun next(s: Long): Long {
  val a = ((s shl 6) xor s) % MODULO
  val b = ((a shr 5) xor a) % MODULO
  val c = ((b shl 11) xor b) % MODULO

  return c
}

fun main() = solution(22) { lines ->
  lines.sumOf { line ->
    var s = line.toLong()
    repeat(2000) { s = next(s) }
    s
  }.let(::println)
}
package day24

import solution

val CONST_REGEX = "(\\w+): ([01])".toRegex()
val OPERATION_REGEX = "(\\w+) (AND|OR|XOR) (\\w+) -> (\\w+)".toRegex()

fun main() = solution(24) { lines ->
  val values = mutableMapOf<String, Int>()
  val operations = mutableMapOf<String, Operation>()

  lines
    .takeWhile { it.isNotEmpty() }
    .map { CONST_REGEX.matchEntire(it)!!.groupValues }
    .forEach { (_, name, value) -> values[name] = value.toInt() }

  lines
    .takeLastWhile { it.isNotEmpty() }
    .map { OPERATION_REGEX.matchEntire(it)!!.groupValues }
    .forEach { (_, left, operator, right, name) ->
      operations[name] = Operation(left, right, operator)
    }

  fun getValue(name: String): Int = values.getOrPut(name) {
    val (left, right, operator) = operations.getValue(name)
    val leftValue = getValue(left)
    val rightValue = getValue(right)
    when (operator) {
      "AND" -> leftValue and rightValue
      "OR" -> leftValue or rightValue
      "XOR" -> leftValue xor rightValue
      else -> throw IllegalArgumentException()
    }
  }

  (values.keys + operations.keys)
    .filter { it.startsWith('z') }
    .map { it.drop(1).toInt() to getValue(it) }
    .filter { it.second == 1 }
    .fold(0L) { acc, (index, _) -> acc + (1L shl index) }
    .let(::println)
}

data class Operation(
  val left: String,
  val right: String,
  val operator: String,
)
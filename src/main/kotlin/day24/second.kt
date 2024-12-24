package day24

import solution

fun main() = solution(24) { lines ->
  val initialValues = mutableMapOf<String, Int>()
  val operations = mutableMapOf<String, Operation>()

  lines
    .takeWhile { it.isNotEmpty() }
    .map { CONST_REGEX.matchEntire(it)!!.groupValues }
    .forEach { (_, name, value) -> initialValues[name] = value.toInt() }

  lines
    .takeLastWhile { it.isNotEmpty() }
    .map { OPERATION_REGEX.matchEntire(it)!!.groupValues }
    .forEach { (_, left, operator, right, name) ->
      operations[name] = Operation(left, right, operator)
    }

  val values = mutableMapOf<String, Int>()
  fun getValue(name: String): Int = values[name] ?: initialValues.getOrPut(name) {
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

  fun longValue(firstChar: Char): Long =
    (initialValues.keys + operations.keys)
      .filter { it.startsWith(firstChar) }
      .map { it.index() to getValue(it) }
      .filter { it.second == 1 }
      .fold(0L) { acc, (index, _) -> acc + (1L shl index) }

  fun getName(operator: String, name1: String?, name2: String?): String? =
    operations.entries
      .filter { it.value.operator == operator }
      .filter { name1 == null || it.value.left == name1 || it.value.right == name1 }
      .filter { name2 == null || it.value.left == name2 || it.value.right == name2 }
      .also { if (it.size > 1) throw IllegalStateException() }
      .firstOrNull()
      ?.key

  fun swap(name1: String, name2: String) {
    operations[name1] = operations.getValue(name2).also { operations[name2] = operations.getValue(name1) }
  }

  if (operations.getValue("z00") notSame Operation("x00", "y00", "XOR"))
    throw IllegalArgumentException()
  val n = operations
    .keys
    .filter { it.startsWith('z') }
    .maxOf { it.drop(1).toInt() }

  var prevName = getName("AND", "x00", "y00")!!
  sequence {
    for (i in 1..<n - 1) {
      val nameX = i.name('x')
      val nameY = i.name('y')
      val nameZ = i.name('z')

      var xorName1 = getName("XOR", nameX, nameY)!!
      var xorName2 = getName("XOR", xorName1, prevName)

      if (xorName2 == null) {
        val realXorName1 = operations.getValue(nameZ).other(prevName)
        yield(realXorName1)
        yield(xorName1)
        swap(realXorName1, xorName1)
        xorName1 = realXorName1
        xorName2 = getName("XOR", xorName1, prevName)!!
      }

      if (nameZ != xorName2) {
        yield(nameZ)
        yield(xorName2)
        swap(nameZ, xorName2)
      }

      val andName1 = getName("AND", nameX, nameY)!!
      val andName2 = getName("AND", prevName, xorName1)
      val orName = getName("OR", andName1, andName2)

      prevName = orName!!
    }
  }.toList()
    .sorted()
    .joinToString(",")
    .let(::println)


  if (longValue('x') + longValue('y') != longValue('z')) throw IllegalStateException()
}

infix fun Operation?.same(other: Operation?): Boolean {
  if (this == null || other == null) return false
  if (this.operator != other.operator) return false

  return left == other.left && right == other.right || left == other.right && right == other.left
}

infix fun Operation?.notSame(other: Operation?): Boolean = !(this same other)

fun String.index() = drop(1).toInt()
fun Int.name(char: Char) = char + toString().padStart(2, '0')
fun Operation.other(name: String): String = if (left == name) right else left

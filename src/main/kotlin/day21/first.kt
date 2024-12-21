package day21

import solution

typealias Keypad = Map<Char, Pair<Int, Int>>

val NUMERIC_KEYPAD = mapOf(
  '7' to (0 to 0),
  '8' to (0 to 1),
  '9' to (0 to 2),
  '4' to (1 to 0),
  '5' to (1 to 1),
  '6' to (1 to 2),
  '1' to (2 to 0),
  '2' to (2 to 1),
  '3' to (2 to 2),
  '0' to (3 to 1),
  'A' to (3 to 2),
)

val DIRECTIONAL_KEYPAD = mapOf(
  '^' to (0 to 1),
  'A' to (0 to 2),
  '<' to (1 to 0),
  'v' to (1 to 1),
  '>' to (1 to 2),
)

fun getCommands(code: String, keypad: Keypad): String = buildString {
  var prevKey = 'A'

  for (currKey in code) {
    val (prevI, prevJ) = keypad.getValue(prevKey)
    val (currI, currJ) = keypad.getValue(currKey)

    val rowDiff = currI - prevI
    val columnDiff = currJ - prevJ
    val canStartVertically = currI to prevJ in keypad.values
    val canStartHorizontally = prevI to currJ in keypad.values

    if (!canStartVertically || canStartHorizontally && columnDiff < 0) {
      moveHorizontally(columnDiff)
      moveVertically(rowDiff)
    } else {
      moveVertically(rowDiff)
      moveHorizontally(columnDiff)
    }

    append('A')
    prevKey = currKey
  }
}

fun StringBuilder.moveHorizontally(diff: Int) {
  if (diff > 0) repeat(diff) { append('>') }
  if (diff < 0) repeat(-diff) { append('<') }
}

fun StringBuilder.moveVertically(diff: Int) {
  if (diff > 0) repeat(diff) { append('v') }
  if (diff < 0) repeat(-diff) { append('^') }
}

fun getNumericCommands(code: String) = getCommands(code, NUMERIC_KEYPAD)
fun getDirectionalCommands(code: String) = getCommands(code, DIRECTIONAL_KEYPAD)

fun main() = solution(21) { lines ->
  lines.sumOf { line ->
    getDirectionalCommands(getDirectionalCommands(getNumericCommands(line))).length * line.take(3).toLong()
  }.let(::println)
}
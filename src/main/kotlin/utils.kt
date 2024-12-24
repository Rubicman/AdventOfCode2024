import java.io.FileNotFoundException
import java.nio.file.Path
import kotlin.io.path.readLines

fun solution(day: Int, body: (List<String>) -> Unit) {
  val folder = Path.of("src/main/resources/day${day.toString().padStart(2, '0')}")
  println("---Test---")
  folder.resolve("test.txt").test(body)
  println("---Real---")
  folder.resolve("real.txt").test(body)
}

fun Path.test(body: (List<String>) -> Unit) = try {
  readLines().apply(body)
} catch (e: Exception) {
  println("Error: $e")
}

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Long, b: Long) = a * b / gcd(a, b)

fun nextCells(i: Int, j: Int) = listOf(i - 1 to j, i + 1 to j, i to j - 1, i to j + 1)
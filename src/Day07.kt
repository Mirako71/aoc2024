
private val addition = { a: Long, b: Long -> a + b }
private val multiplication = { a: Long, b: Long -> a * b }
private val concatenation = { a: Long, b: Long -> "$a$b".toLong() }

private fun calculate(
    numbers: List<Long>,
    currentIndex: Int,
    operations: List<(Long, Long) -> Long>
) : List<Long> {
    if (currentIndex == 0) {
        return listOf(numbers[currentIndex])
    } else {
        val last = numbers[currentIndex]
        val result = mutableListOf<Long>()
        calculate(numbers, currentIndex - 1, operations).forEach { number ->
            operations.forEach { operation ->
                result.add(operation(number, last))
            }
        }
        return result
    }
}

fun main() {
    fun part1(input: List<String>): Long =
        input
            .map { line ->
                val (testValue, numbers) = line.split(": ")
                testValue.toLong() to numbers.split(" ").map { it.toLong() } }
            .filter { eq ->
                calculate(eq.second, eq.second.size - 1, listOf(addition, multiplication)).contains(eq.first) }
            .sumOf { it.first }


    fun part2(input: List<String>): Long =
        input
            .map { line ->
                val (testValue, numbers) = line.split(": ")
                testValue.toLong() to numbers.split(" ").map { it.toLong() } }
            .filter { eq ->
                calculate(eq.second, eq.second.size - 1, listOf(addition, multiplication, concatenation)).contains(eq.first) }
            .sumOf { it.first }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
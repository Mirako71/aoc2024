import kotlin.math.abs

fun main() {

    fun parse(input: List<String>): Pair<ArrayList<Int>, ArrayList<Int>> {
        val left = ArrayList<Int>()
        val right = ArrayList<Int>()
        input.forEach { line ->
            left.add(line.substringBefore(' ').trim().toInt())
            right.add(line.substringAfter(' ').trim().toInt())
        }
        return left to right
    }

    fun part1(input: List<String>): Int {
        val (left, right) = parse(input)

        val difference = left.sorted()
            .zip(right.sorted())
            .sumOf { abs(it.first - it.second) }

        return difference
    }

    fun part2(input: List<String>): Int {
        val (left, right) = parse(input)

        val similarity = left.sumOf { leftNumber ->
            leftNumber * right.count { it == leftNumber }
        }

        return similarity
    }

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

import kotlin.math.abs

fun main() {

    fun isSafe(levels: List<Int>): Boolean {
        var isDecreasing = true
        var isIncreasing = true
        (0 until levels.size - 1).forEach { index ->
            val diff = levels[index] - levels[index + 1]
            when {
                diff < 0 -> isDecreasing = false
                diff > 0 -> isIncreasing = false
                else -> {}
            }
            if (1 > abs(diff.toDouble()) || 3 < abs(diff.toDouble())) return false
        }
        return (isIncreasing || isDecreasing)
    }

    fun part1(input: List<String>): Int {
        val safeCount = input
            .map{ report -> report.split(" ").map { it.toInt() } }
            .count { levels -> isSafe(levels) }

        return safeCount
    }

    fun part2(input: List<String>): Int {
        val dampedSafeCount = input
            .map { report -> report.split(" ").map { it.toInt() } }
            .count { levels ->
                if (isSafe(levels)) return@count true
                var isDampedSafe = false
                levels.indices.forEach { index ->
                    val newLevels = levels.toMutableList()
                    newLevels.removeAt(index)
                    isDampedSafe = isDampedSafe || isSafe(newLevels)
                }
                return@count isDampedSafe
            }

        return dampedSafeCount
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
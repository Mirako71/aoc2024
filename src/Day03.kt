fun main() {
    fun part1(input: List<String>) =
        """mul\((\d{1,3}),(\d{1,3})\)""".toRegex()
            .findAll(input.joinToString())
            .sumOf {matchResult ->
                matchResult.groupValues
                    .drop(1)
                    .map { it.toInt() }
                    .reduce { acc, i -> acc * i }
            }

    fun part2(input: List<String>): Int {
        var count = 0
        var disabled = false
        """mul\((\d{1,3}),(\d{1,3})\)|do\(\)|don't\(\)""".toRegex()
            .findAll(input.joinToString())
            .forEach { matchResult ->
                when(matchResult.value) {
                    "do()" -> disabled = false
                    "don't()" -> disabled = true
                    else -> {
                        if (!disabled) {
                            count += matchResult.groupValues
                                .drop(1)
                                .map { it.toInt() }
                                .reduce { acc, i -> acc * i }
                        }
                    }
                }
            }
        return count
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
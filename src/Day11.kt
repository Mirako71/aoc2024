fun main() {

    fun String.hasEvenLength() = this.length % 2 == 0
    fun String.halved() = this.chunked(this.length / 2)

    fun countStones(input: List<String>, blinks: Int): Long {
        val stoneMap = input.first()
            .split(" ")
            .map { it.toLong() }
            .groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }

        // got this nice fold-expression from reddit
        // https://www.reddit.com/r/adventofcode/comments/1hbm0al/comment/m1jv5oo/
        return (0..<blinks).fold(stoneMap) { current, _ ->
            buildMap<Long, Long> {
                for ((stone, stoneCount) in current) {
                    val stoneString = stone.toString()
                    if (stone == 0L) {
                        this[1L] = this.getOrPut(1L) { 0 } + stoneCount
                    }
                    else if (stoneString.hasEvenLength()) {
                        stoneString.halved().forEach { this[it.toLong()] = this.getOrPut(it.toLong()) { 0 } + stoneCount }
                    }
                    else {
                        this[stone * 2024] = this.getOrPut(stone * 2024) { 0 } + stoneCount
                    }
                }
            }
        }.values.sum()
    }

    fun part1(input: List<String>): Long = countStones(input, 25)
    fun part2(input: List<String>): Long = countStones(input, 75)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
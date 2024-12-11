fun main() {

    fun String.hasEvenLength() = this.length % 2 == 0
    fun String.halved() = this.chunked(this.length / 2)

    fun blink(stoneMap: Map<Long, Long>): Map<Long, Long> =
        buildMap<Long, Long> {
            for (stone in stoneMap.keys) {
                val stoneCount = stoneMap.getValue(stone)
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

    fun countStones(stones: List<Long>, blinks: Int): Long {
        var stoneMap = stones.groupingBy { it }.eachCount().mapValues { (_ , v) -> v.toLong() }

        repeat(blinks) {
            stoneMap = blink(stoneMap)
        }

        return stoneMap.values.sum()
    }

    fun part1(input: List<String>): Long {
        val stones = input.first().split(" ").map { it.toLong() }
        return countStones(stones, 25)
    }

    fun part2(input: List<String>): Long {
        val stones = input.first().split(" ").map { it.toLong() }
        return countStones(stones, 75)
    }

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}
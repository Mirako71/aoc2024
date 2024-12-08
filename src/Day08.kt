fun main() {
    operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> =
        this.first + other.first to this.second + other.second
    operator fun Pair<Int, Int>.minus(other: Pair<Int, Int>): Pair<Int, Int> =
        this.first - other.first to this.second - other.second

    fun isSafe(position: Pair<Int, Int>, height: Int, width: Int): Boolean =
        position.first in 0 until height && position.second in 0 until width

    fun part1(input: List<String>): Int {
        val height = input.size
        val width = input[0].length
        val antennas =  mutableMapOf<Char, ArrayList<Pair<Int, Int>>>()

        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c != '.') antennas.getOrPut(c) { ArrayList() }.add(Pair(row, col))
            }
        }

        val antinodes = mutableSetOf<Pair<Int, Int>>()

        antennas.forEach { (_, positions) ->
            (0 until positions.size - 1).forEach { first ->
                (first + 1 until positions.size).forEach { second ->
                    val displacement = positions[first] - positions[second]
                    if (isSafe(positions[first] + displacement, height, width))
                        antinodes.add(positions[first] + displacement)
                    if (isSafe(positions[second] - displacement, height, width))
                        antinodes.add(positions[second] - displacement)
                }
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennas = mutableMapOf<Char, ArrayList<Pair<Int, Int>>>()
        val height = input.size
        val width = input[0].length

        input.forEachIndexed { row, line ->
            line.forEachIndexed { col, c ->
                if (c != '.') antennas.getOrPut(c) { ArrayList() }.add(Pair(row, col))
            }
        }

        val antinodes = mutableSetOf<Pair<Int, Int>>()

        antennas.forEach { (_, positions) ->
            (0 until positions.size - 1).forEach { first ->
                (first + 1 until positions.size).forEach { second ->
                    val displacement = positions[first] - positions[second]

                    var nextNode = positions[first]
                    while (isSafe(nextNode, height, width)) {
                        antinodes.add(nextNode)
                        nextNode += displacement
                    }

                    nextNode = positions[second]
                    while (isSafe(nextNode, height, width)) {
                        antinodes.add(nextNode)
                        nextNode -= displacement
                    }
                }
            }
        }

        return antinodes.size
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
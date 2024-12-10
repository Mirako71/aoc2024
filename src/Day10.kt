fun main() {
    fun parse(input: List<String>): Map<Position, Int> {
        // I don't want to fiddle with arrays, thus this map of
        // coordinates to characters. As usual my coordinates will
        // be row first
        return buildMap {
            input.forEachIndexed { row, line ->
                line.forEachIndexed { col, c ->
                    put(Pair(row, col), c.digitToInt())
                }
            }
        }
    }

    val neighbourOffsets = listOf( -1 to 0, 1 to 0, 0 to -1, 0 to 1)

    operator fun Position.plus(other: Position): Position =
        this.first + other.first to this.second + other.second
    operator fun Position.minus(other: Position): Position =
        this.first - other.first to this.second - other.second

    // okay make it fun Kotlin and an extension function to Position
    // respectively Pair<Int, Int>. The receiver is a trailhead.
    // Solution for both parts.
    fun Position.countTrails(
        map: Map<Position, Int>,
        visited: MutableSet<Position> = mutableSetOf(),
        distinctTrails: Boolean
    ): Int {
        // if we are looking for distinct trails we need to keep track of
        // visited positions
        if (distinctTrails) {
            if (this in visited) return 0
            else visited.add(this)
        }

        // if we are on a summit there is just this possibility so count 1
        if (map[this] == 9) return 1

        // for all possible neighbour directions
        return neighbourOffsets
            // calculate the neighbour's position
            .map { it + this }
            // check if the neighbour is on the map
            .filter { it in map }
            // check if the neighbour is only 1 unit higher
            .filter { map.getValue(it) - map.getValue(this) == 1 }
            // and sum up all the possible trails starting fom the neighbour
            .sumOf { it.countTrails(map, visited, distinctTrails) }
    }

    fun part1(input: List<String>): Int {
        val map = parse(input)
        return map.filter { it.value == 0 }.keys
            .sumOf { it.countTrails(map = map, distinctTrails = true) }
    }

    fun part2(input: List<String>): Int {
        val map = parse(input)
        return map.filter { it.value == 0 }.keys
            .sumOf { it.countTrails(map = map, distinctTrails = false) }
    }

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

typealias Position = Pair<Int, Int>
fun main() {

    fun parseInput(input: List<String>): Triple<MutableSet<Pair<Int, Int>>, Pair<Int, Int>, Pair<Int, Int>> {
        val dimension = input.size to input[0].length
        // all walls
        val obstacles = mutableSetOf<Pair<Int, Int>>()
        var currentPosition = Pair(0, 0)

        input.forEachIndexed { row, line ->
            line.trim().forEachIndexed { col, c ->
                when (c) {
                    '#' -> obstacles.add(Pair(row, col))
                    '^' -> currentPosition = Pair(row, col)
                }
            }
        }
        return Triple(obstacles, currentPosition, dimension)
    }

    fun isSafe(position: Pair<Int, Int>, dimension: Pair<Int, Int>): Boolean  =
        position.first in 0 until dimension.first &&
                position.second in 0 until dimension.second

    fun findVisitedPositions(
        startPosition: Pair<Int, Int>,
        dimension: Pair<Int, Int>,
        obstacles: MutableSet<Pair<Int, Int>>,
    ): MutableSet<Pair<Int, Int>> {
        // visited positions (we may walk over a position multiple times)
        val visited = mutableSetOf<Pair<Int, Int>>()
        var currentPosition = startPosition
        var currentDirection = Direction.NORTH

        while (isSafe(currentPosition, dimension)) {
            visited.add(currentPosition)
            val nextPosition =
                currentPosition.first + currentDirection.direction.first to
                        currentPosition.second + currentDirection.direction.second

            if (obstacles.contains(nextPosition)) {
                // turn right
                currentDirection = when (currentDirection) {
                    Direction.NORTH -> Direction.EAST
                    Direction.EAST -> Direction.SOUTH
                    Direction.SOUTH -> Direction.WEST
                    Direction.WEST -> Direction.NORTH
                }
            } else {
                currentPosition = nextPosition
            }
        }

        return visited
    }

    fun part1(input: List<String>): Int {
        val (obstacles,  startPosition, dimension) = parseInput(input)
        val visited = findVisitedPositions(startPosition, dimension, obstacles)
        return visited.size
    }

    fun part2(input: List<String>): Int {
        TODO("Not implemented yet")

        return 0
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

enum class Direction(val direction: Pair<Int, Int>) {
    NORTH(-1 to 0),
    SOUTH( 1 to 0),
    EAST(0 to 1),
    WEST(0 to -1);
}

fun printGrid(
    obstacles: Set<Pair<Int, Int>>,
    visited: Set<Pair<Int, Int>>,
    currentPosition: Pair<Int, Int>,
    direction: Direction,
    height: Int, width: Int
) {
    (0 until height).forEach { row ->
        (0 until width).forEach { col ->
            if (row == currentPosition.first && col == currentPosition.second) {
                when (direction) {
                    Direction.NORTH -> print('^')
                    Direction.EAST -> print('>')
                    Direction.SOUTH -> print('v')
                    Direction.WEST -> print('<')
                }
            }
            else if (obstacles.contains(Pair(row, col))) print('#')
            else if (visited.contains(Pair(row, col))) print('X')
            else print('.')
        }
        println()
    }
    println()
    println()
}
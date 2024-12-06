fun main() {
    /**
     * Parse the input into several variables:
     * - a mutableSet containing the obstacles (needs to be mutable for part 2)
     * - the start position of the guard
     * - the dimension of the lab
     */
    fun parseInput(input: List<String>): Triple<MutableSet<Pair<Int, Int>>, Pair<Int, Int>, Pair<Int, Int>> {
        // store the dimension we will need it later for inside/outside checks
        val dimension = input.size to input[0].length
        // all walls are stored as their positions
        val obstacles = mutableSetOf<Pair<Int, Int>>()
        var startPosition = Pair(0, 0)

        input.forEachIndexed { row, line ->
            line.trim().forEachIndexed { col, c ->
                when (c) {
                    '#' -> obstacles.add(Pair(row, col))
                    '^' -> startPosition = Pair(row, col)
                }
            }
        }
        return Triple(obstacles, startPosition, dimension)
    }

    /**
     * Checks if a position is inside the lab, i.e. inside the dimension
     */
    fun isSafe(position: Pair<Int, Int>, dimension: Pair<Int, Int>): Boolean  =
        position.first in 0 until dimension.first &&
                position.second in 0 until dimension.second

    /**
     * Find all visited position along a walk of the guard through the lab.
     * If the guard happens to walk along a loop, return an empty set.
     */
    fun findVisitedPositions(
        startPosition: Pair<Int, Int>,
        dimension: Pair<Int, Int>,
        obstacles: MutableSet<Pair<Int, Int>>,
    ): MutableSet<Pair<Int, Int>> {
        // visited positions (we may walk over a position multiple times)
        val visited = mutableSetOf<Pair<Int, Int>>()
        visited.add(startPosition)
        var currentPosition = startPosition
        var currentDirection = Direction.NORTH

        // Here we keep track of positions and direction in front of obstacles.
        // For the sake of simplicity, we use strings instead of a new data class.
        val corners = mutableSetOf<String>()

        while (true) {
            val nextPosition =
                currentPosition.first + currentDirection.direction.first to
                        currentPosition.second + currentDirection.direction.second

            // check if we are still inside the lab
            if (isSafe(nextPosition, dimension)) {
                // check if we are right before an obstacle
                if (obstacles.contains(nextPosition)) {
                    // remember position and direction in front of the obstacle
                    val corner = currentPosition.toString() + currentDirection.direction.toString()
                    // If corners already contains our corner, we have been here
                    // before looking in the same direction - a loop.
                    if (corners.contains(corner)) {
                        // return an empty set as sign that a loop was encountered
                        return mutableSetOf()
                    }
                    // we're still here, so do the turn
                    currentDirection = when (currentDirection) {
                        Direction.NORTH -> Direction.EAST
                        Direction.EAST -> Direction.SOUTH
                        Direction.SOUTH -> Direction.WEST
                        Direction.WEST -> Direction.NORTH
                    }
                    // and add the corner to the list of corners
                    corners.add(corner)
                } else {
                    // not an obstacle, so go on
                    currentPosition = nextPosition
                    visited.add(currentPosition)
                }
            } else {
                // we stepped outside the lab. i.e. no loops, return the visited positions
                return visited
            }
        }
    }

    fun part1(input: List<String>): Int {
        val (obstacles,  startPosition, dimension) = parseInput(input)

        val visited = findVisitedPositions(startPosition, dimension, obstacles)

        return visited.size
    }

    fun part2(input: List<String>): Int {
        val (obstacles,  startPosition, dimension) = parseInput(input)
        // Have him walk once through the lab. It makes only sense to place
        // additional obstacles on his normal path.
        val visited = findVisitedPositions(startPosition, dimension, obstacles)

        var loopCount = 0
        for (pos in visited) {
            // ad an additional obstacle at one of the positions on the path
            obstacles.add(pos)

            if (findVisitedPositions(startPosition, dimension, obstacles).isEmpty()) loopCount++

            // remove the obstacle again
            obstacles.remove(pos)
        }

        return loopCount
    }

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
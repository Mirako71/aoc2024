fun main() {
    val allDirections = listOf(
        -1 to -1, -1 to 0, -1 to 1,
         0 to -1,           0 to 1,
         1 to -1,  1 to 0,  1 to 1)
    val diagonals = listOf(
        -1 to -1, -1 to 1,
         1 to -1,  1 to 1)

    fun parse(input: List<String>): Array<Array<Char>> {
        val grid = Array(input.size) { Array<Char>(input.first().length) { ' ' } }
        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, c ->
                grid[rowIndex][colIndex] = c
            }
        }
        return grid
    }

    fun Array<Array<Char>>.safeGet(row: Int, col: Int): Char =
        when {
            (row < 0 || row >= this.size) -> ' '
            (col < 0 || col >= this[row].size) -> ' '
            else -> this[row][col]
        }

    fun part1(input: List<String>): Int {
        val grid = parse(input)
        var counter = 0
        grid.indices.forEach { row ->
            (0 until grid[row].size).forEach { col ->
                if (grid[row][col] == 'X') {
                    counter += allDirections.count { direction ->
                        "XMAS".foldIndexed(true) { index, acc, c ->
                            acc && grid.safeGet(row + direction.first * index,col + direction.second * index) == c
                        }
                    }
                }
            }
        }
        return counter
    }

    fun part2(input: List<String>): Int {
        val grid = parse(input)
        var counter = 0
        grid.indices.forEach { row ->
            (0 until grid[row].size).forEach { col ->
                if (grid[row][col] == 'A') {
                    diagonals
                        .map { grid.safeGet(row + it.first, col + it.second) }
                        .joinToString("")
                        .also {
                            println(it)
                            if (it in listOf("MMSS", "MSMS", "SMSM", "SSMM")) counter++
                        }
                }
            }
        }
        return counter
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
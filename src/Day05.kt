fun main() {

    lateinit var rules: List<Pair<Int, Int>>
    lateinit var updates: List<List<Int>>

    fun parseInput(input: List<String>) {
        rules = input.takeWhile { it.isNotEmpty() }.toList()
            .map { rule -> rule.split("|") }
            .map { it[0].toInt() to it[1].toInt() }
        updates = input
            .dropWhile { it.isNotEmpty() }
            .drop(1)
            .map { row -> row.split(",").map { it.toInt() } }
    }


    fun getCorrectUpdateIndices(): MutableList<Int> {
        val correctUpdateIndices = mutableListOf<Int>()
        updates.forEachIndexed { index, update ->
            var correct = true
            rules.forEach { rule ->
                val left = update.indexOf(rule.first)
                val right = update.indexOf(rule.second)
                if (left != -1 && right != -1) correct = correct && (left < right)
            }
            if (correct) correctUpdateIndices.add(index)
        }
        return correctUpdateIndices
    }

    fun part1(input: List<String>): Int {
        parseInput(input)

        val correctUpdateIndices = getCorrectUpdateIndices()

        val correctUpdates = correctUpdateIndices.sumOf { index ->
            val update = updates[index]
            update[update.size / 2]
        }

        return correctUpdates
    }

    val rulesComparator = object : Comparator<Int> {
        override fun compare(left: Int, right: Int): Int {
            for (rule in rules) {
                if (rule.first == left && rule.second == right) return -1
                if (rule.first == right && rule.second == left) return 1
            }
            return 0
        }
    }

    fun part2(input: List<String>): Int {
        parseInput(input)
        val correctUpdateIndices = getCorrectUpdateIndices()
        val toBeSorted = IntRange(0, updates.size - 1).toMutableList()
        toBeSorted.removeAll(correctUpdateIndices)

        return toBeSorted.sumOf { index ->
            val update = updates[index]
            val sorted = update.sortedWith(rulesComparator)
            sorted[sorted.size / 2]
        }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
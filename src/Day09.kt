fun main() {
    fun part1(input: List<String>): Long {
        val diskMap =
            input.first().withIndex()
                .fold(listOf<DiskBlock>()) { blockList, indexedMapChar ->
                    when (indexedMapChar.index % 2) {
                        0 -> blockList + List(indexedMapChar.value.digitToInt()) { FileBlock(indexedMapChar.index / 2) }
                        else -> blockList + List(indexedMapChar.value.digitToInt()) { EmptyBlock() }
                    }
                }.toMutableList()

        var left = 0
        var right: Int = diskMap.size - 1
        while (true) {
            // zum letzten belegten Platz gehen
            while (diskMap[right] is EmptyBlock) right--
            // zum ersten unbelegten Platz gehen
            while (diskMap[left] !is EmptyBlock) left++
            // wenn wir an der gleichen Stelle sind, sind wir fertig
            if (left >= right) break

            // die beiden Plätze tauschen
            val tmp = diskMap[right]
            diskMap[right] = diskMap[left]
            diskMap[left] = tmp
        }

        return diskMap
            .withIndex()
            .sumOf { (index, block) -> if (block is FileBlock) index * block.id.toLong() else 0L }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val testInput = listOf("2333133121414131402")
    check(part1(testInput) == 1928L)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

sealed interface DiskBlock
class EmptyBlock : DiskBlock
data class FileBlock(val id: Int): DiskBlock
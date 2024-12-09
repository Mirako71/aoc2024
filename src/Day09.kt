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

    fun part2(input: List<String>): Long {
        // create a different disk map, this time with sized blocks
        val diskMap = buildList {
            var id = 0
            input.first().windowed(2, 2, true).forEach {
                add(FileSegment(id, it[0].digitToInt()))
                if (it.length > 1)add(EmptySegment(it[1].digitToInt()))
                id++
            }
        }.toMutableList()

        var left: Int
        var right = diskMap.indexOfLast { it is FileSegment }
        while(true) {
            if ((diskMap[right] as FileSegment).id == 0) break

            // den ersten leeren Platz mit der richtigen Größe suchen
            val sizeNeeded = (diskMap[right] as FileSegment).size
            left = diskMap.indexOfFirst { it is EmptySegment && it.size >= sizeNeeded }
            if (left != -1 && left < right) {
                // Inhalte tauschen und leeren Platz neu berechnen
                val newFreeSpace = (diskMap[left] as EmptySegment).size - sizeNeeded
                diskMap[left] = diskMap[right]
                diskMap[right] = EmptySegment(sizeNeeded)
                if (newFreeSpace > 0) diskMap.add(left + 1, EmptySegment(newFreeSpace))

            }
            right--
            while (diskMap[right] !is FileSegment) right--
        }

        var index = 0
        var checksum = 0L
        for (segment in diskMap) {
            when (segment) {
                is EmptySegment -> index += segment.size
                is FileSegment -> {
                    (index ..< index + segment.size)
                        .fold(0L) { acc, i -> acc + i * segment.id.toLong() }.also { checksum += it }
                    index += segment.size
                }
            }
        }

        return checksum
    }

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

sealed interface DiskBlock
class EmptyBlock : DiskBlock
data class FileBlock(val id: Int): DiskBlock

sealed interface DiskSegment
data class FileSegment(val id: Int, val size: Int): DiskSegment
data class EmptySegment(val size: Int): DiskSegment
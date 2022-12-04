import java.io.File

fun main() {
    val input = File("src/main/resources/day_04_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(input: File): Int {
    var overlappingPairs = 0

    input.forEachLine { line ->
        val values = line.split(',', '-')
        val pairOne = Pair(values[0].toInt(), values[1].toInt())
        val pairTwo = Pair(values[2].toInt(), values[3].toInt())

        if (pairOne.contains(pairTwo) || pairTwo.contains(pairOne)) {
            overlappingPairs++
        }
    }

    return overlappingPairs
}

private fun partTwo(input: File): Int {
    var overlappingPairs = 0

    input.forEachLine { line ->
        val values = line.split(',', '-')
        val pairOne = Pair(values[0].toInt(), values[1].toInt())
        val pairTwo = Pair(values[2].toInt(), values[3].toInt())

        if (pairOne.overlaps(pairTwo)) {
            overlappingPairs++
        }
    }

    return overlappingPairs
}

fun Pair<Int, Int>.contains(pair: Pair<Int, Int>): Boolean = this.second >= pair.second && this.first <= pair.first

fun Pair<Int, Int>.overlaps(pair: Pair<Int, Int>): Boolean =
    (pair.first >= this.first && pair.first <= this.second) ||
        (this.first >= pair.first && this.first <= pair.second)

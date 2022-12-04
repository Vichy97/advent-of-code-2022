import java.io.File

fun main() {
    val input = File("src/main/resources/day_03_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private const val PRIORITIES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
private fun partOne(input: File): Int {
    var totalPriority = 0

    input.forEachLine { line ->
        val firstCompartment = line.substring(0, line.length / 2).toSet()
        val secondCompartment = line.substring(line.length / 2)

        val matchingChar = secondCompartment.first {
            firstCompartment.contains(it)
        }
        totalPriority += PRIORITIES.indexOf(matchingChar) + 1
    }

    return totalPriority
}

private fun partTwo(input: File): Int {
    var totalPriority = 0

    var possibleBadges = emptySet<Char>()
    var i = 0
    input.forEachLine { line ->
        possibleBadges = if (i == 0) {
            line.toSet()
        } else {
            possibleBadges.intersect(line.toSet())
        }
        if (i == 2) {
            totalPriority += PRIORITIES.indexOf(possibleBadges.first()) + 1
            i = 0
        } else {
            i++
        }
    }

    return totalPriority
}


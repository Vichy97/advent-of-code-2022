import java.io.File

fun main() {
    val input = File("src/main/resources/day_06_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(input: File): Int {
    input.readLines()
        .first()
        .windowed(4)
        .map { it.toSet() }
        .forEachIndexed { index, window ->
            if (window.size == 4) {
                return index + 4
            }
        }
    return -1
}

private fun partTwo(input: File): Int {
    input.readLines()
        .first()
        .windowed(14)
        .map { it.toSet() }
        .forEachIndexed { index, window ->
            if (window.size == 14) {
                return index + 14
            }
        }
    return -1
}
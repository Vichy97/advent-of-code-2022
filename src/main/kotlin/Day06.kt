import java.io.File

fun main() {
    val input = File("src/main/resources/day_06_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(input: File) = input.readText()
    .windowed(4)
    .map { it.toSet() }
    .indexOfFirst {
        it.size == 4
    } + 4

private fun partTwo(input: File) = input.readText()
    .windowed(14)
    .map { it.toSet() }
    .indexOfFirst {
        it.size == 14
    } + 14
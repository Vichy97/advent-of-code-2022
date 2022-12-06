import java.io.File
import java.util.Stack

fun main() {
    val input = File("src/main/resources/day_05_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(input: File): String {
    val lines = input.readLines()
    val stacks = getStacks(lines)

    lines.filter {
        it.contains("move")
    }.forEach {
        val instruction = getInstruction(it)
        for (i in 0 until instruction.numberToMove) {
            val item = stacks[instruction.moveFrom].removeLast()
            stacks[instruction.moveTo].addLast(item)
        }
    }

    return stacks.joinToString("") { it.last().toString() }
}

private fun partTwo(input: File): String {
    val lines = input.readLines()
    val stacks = getStacks(lines)

    lines.filter {
        it.contains("move")
    }.forEach {
        val instruction = getInstruction(it)
        val itemsToMove = Stack<Char>()
        for (i in 0 until instruction.numberToMove) {
            val item = stacks[instruction.moveFrom].removeLast()
            itemsToMove.push(item)
        }
        while(itemsToMove.isNotEmpty()) {
            stacks[instruction.moveTo].addLast(itemsToMove.pop())
        }
    }

    return stacks.joinToString("") { it.last().toString() }
}

private fun getStacks(lines: List<String>): List<ArrayDeque<Char>> {
    val stacks = List(9) { ArrayDeque<Char>() }
    lines.filter {
        it.contains('[')
    }.forEach { line ->
        line.chunked(4)
            .forEachIndexed { index, s ->
                val char = s[1]
                if (!char.isWhitespace()) {
                    stacks[index].addFirst(char)
                }
            }
    }
    return stacks
}

private fun getInstruction(line: String) = line.split("move", "from", "to")
    .let {
        Instruction(
            numberToMove = it[1].trim().toInt(),
            moveFrom = it[2].trim().toInt() - 1,
            moveTo = it[3].trim().toInt() - 1,
        )
    }

private data class Instruction(
    val numberToMove: Int,
    val moveFrom: Int,
    val moveTo: Int,
)
import java.io.File
import java.lang.IllegalStateException

fun main() {
    val input = File("src/main/resources/day_02_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(input: File): Int {
    var totalPoints = 0
    input.forEachLine {
        val opponentsChoice = it.first().toChoice()
        val yourChoice = it.last().toChoice()

        totalPoints += yourChoice.points
        totalPoints += when (yourChoice.versus(opponentsChoice)) {
            Result.Draw -> 3
            Result.Win -> 6
            Result.Lose -> 0
        }
    }

    return totalPoints
}

private fun partTwo(input: File): Int {
    var totalPoints = 0
    input.forEachLine {
        val opponentsChoice = it.first().toChoice()
        val result = it.last().toResult()
        val yourChoice = getChoiceForResult(opponentsChoice, result)

        totalPoints += yourChoice.points
        totalPoints += when (yourChoice.versus(opponentsChoice)) {
            Result.Draw -> 3
            Result.Win -> 6
            Result.Lose -> 0
        }
    }

    return totalPoints
}

private fun getChoiceForResult(
    opponentsChoice: Choice,
    result: Result,
) = when (result) {
    Result.Draw -> opponentsChoice
    Result.Win -> when (opponentsChoice) {
        Choice.Rock -> Choice.Paper
        Choice.Paper -> Choice.Scissors
        Choice.Scissors -> Choice.Rock
    }
    Result.Lose -> when (opponentsChoice) {
        Choice.Rock -> Choice.Scissors
        Choice.Paper -> Choice.Rock
        Choice.Scissors -> Choice.Paper
    }
}

private fun Char.toChoice() = when (this) {
    'A', 'X' -> Choice.Rock
    'B', 'Y' -> Choice.Paper
    'C', 'Z' -> Choice.Scissors
    else -> throw IllegalArgumentException()
}

enum class Choice(val points: Int) {
    Rock(1),
    Paper(2),
    Scissors(3);

    fun versus(choice: Choice) = when {
        this == choice -> Result.Draw
        this == Rock && choice == Scissors -> Result.Win
        this == Rock && choice == Paper -> Result.Lose
        this == Paper && choice == Rock -> Result.Win
        this == Paper && choice == Scissors -> Result.Lose
        this == Scissors && choice == Rock -> Result.Lose
        this == Scissors && choice == Paper -> Result.Win
        else -> throw IllegalStateException()
    }
}

private fun Char.toResult() = when (this) {
    'X' -> Result.Lose
    'Y' -> Result.Draw
    'Z' -> Result.Win
    else -> throw IllegalArgumentException()
}

enum class Result {
    Draw,
    Win,
    Lose,
}
import java.io.File
import java.util.PriorityQueue

fun main() {
    val input = File("src/main/resources/day_01_input.txt")
    val highest = highestCalories(input)
    val highestThreeSum = highestThreeCalories(input)

    println("highest calories: $highest")
    println("sum of the three highest calories: $highestThreeSum")
}

private fun highestCalories(input: File): Int {
    var runningTotal = 0
    var max = 0

    input.forEachLine {
        if (it.isBlank()) {
            if (runningTotal > max) {
                max = runningTotal
            }
            runningTotal = 0
        } else {
            runningTotal += it.toInt()
        }
    }

    return max
}

private fun highestThreeCalories(input: File): Int {
    val priorityQueue = PriorityQueue<Int>(3)
    var runningTotal = 0

    input.forEachLine {
        if (it.isBlank()) {
            if (priorityQueue.size < 3) {
                priorityQueue.add(runningTotal)
            } else if (priorityQueue.peek() < runningTotal) {
                priorityQueue.remove()
                priorityQueue.add(runningTotal)
            }
            runningTotal = 0
        } else {
            runningTotal += it.toInt()
        }
    }

    return priorityQueue.sum()
}
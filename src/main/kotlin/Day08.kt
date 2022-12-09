import java.io.File

fun main() {
    val input = File("src/main/resources/day_08_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(input: File): Int {
    val trees = parseTrees(input)

    var numberOfVisibleTrees = 0
    trees.forEach {
        it.forEach { tree ->
            if (tree.isVisible()) numberOfVisibleTrees++
        }
    }

    return numberOfVisibleTrees
}

private fun partTwo(input: File): Int {
    val trees = parseTrees(input)

    var highestVisibilityScore = 0
    trees.forEach {
        it.forEach { tree ->
            val visibilityScore = tree.visibilityScore()
            if (visibilityScore > highestVisibilityScore) {
                highestVisibilityScore = visibilityScore
            }
        }
    }

    return highestVisibilityScore
}

private fun parseTrees(input: File): Array<Array<Tree>> {
    val lines = input.readLines()
    val trees = Array(lines.size) { Array(lines[0].length) { Tree() } }

    input.readLines().forEachIndexed { i, line ->
        line.forEachIndexed { j, c ->
            val tree = trees[i][j]
            tree.height = c.digitToInt()
            tree.above = trees.getOrNull(i - 1)?.getOrNull(j)
            tree.below = trees.getOrNull(i + 1)?.getOrNull(j)
            tree.left = trees.getOrNull(i)?.getOrNull(j - 1)
            tree.right = trees.getOrNull(i)?.getOrNull(j + 1)
        }
    }

    return trees
}

private fun Tree.visibilityScore() =
    aboveVisibilityScore() * belowVisibilityScore() * leftVisibilityScore() * rightVisibilityScore()

private fun Tree.aboveVisibilityScore(): Int {
    var visibilityScore = 0
    var currentTree: Tree? = above
    while (currentTree != null) {
        visibilityScore++
        if (currentTree.height >= height) break
        currentTree = currentTree.above
    }
    return visibilityScore
}

private fun Tree.belowVisibilityScore(): Int {
    var visibilityScore = 0
    var currentTree: Tree? = below
    while (currentTree != null) {
        visibilityScore++
        if (currentTree.height >= height) break
        currentTree = currentTree.below
    }
    return visibilityScore
}

private fun Tree.leftVisibilityScore(): Int {
    var visibilityScore = 0
    var currentTree: Tree? = left
    while (currentTree != null) {
        visibilityScore++
        if (currentTree.height >= height) break
        currentTree = currentTree.left
    }
    return visibilityScore
}

private fun Tree.rightVisibilityScore(): Int {
    var visibilityScore = 0
    var currentTree: Tree? = right
    while (currentTree != null) {
        visibilityScore++
        if (currentTree.height >= height) break
        currentTree = currentTree.right
    }
    return visibilityScore
}

private fun Tree.isVisible(): Boolean {
    if (above == null || below == null || left == null || right == null) {
        return true
    }
    return isVisibleFromAbove() || isVisibleFromBelow() || isVisibleFromRight() || isVisibleFromLeft()
}

private fun Tree.isVisibleFromAbove(): Boolean {
    var currentTree: Tree? = above
    while (currentTree != null) {
        if (currentTree.height >= height) return false
        currentTree = currentTree.above
    }
    return true
}

private fun Tree.isVisibleFromBelow(): Boolean {
    var currentTree: Tree? = below
    while (currentTree != null) {
        if (currentTree.height >= height) return false
        currentTree = currentTree.below
    }
    return true
}

private fun Tree.isVisibleFromLeft(): Boolean {
    var currentTree: Tree? = left
    while (currentTree != null) {
        if (currentTree.height >= height) return false
        currentTree = currentTree.left
    }
    return true
}

private fun Tree.isVisibleFromRight(): Boolean {
    var currentTree: Tree? = right
    while (currentTree != null) {
        if (currentTree.height >= height) return false
        currentTree = currentTree.right
    }
    return true
}

class Tree(
    var above: Tree? = null,
    var below: Tree? = null,
    var left: Tree? = null,
    var right: Tree? = null,
    var height: Int = -1,
)
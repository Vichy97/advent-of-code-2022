import java.util.PriorityQueue

fun main() {
    val input = java.io.File("src/main/resources/day_07_input.txt")

    println("part one: ${partOne(input)}")
    println("part two: ${partTwo(input)}")
}

private fun partOne(file: java.io.File): Int {
    var directorySizes = 0
    val rootDirectory = parseDirectories(file)

    fun Directory.size(): Int = (files.sumOf { it.size } + directories.sumOf { it.size() })
        .also {
            if (it <= 100000) {
                directorySizes += it
            }
        }

    rootDirectory.size()
    return directorySizes
}

private fun partTwo(file: java.io.File): Int {
    val directorySizes = PriorityQueue<Int>()
    fun Directory.size(): Int = (files.sumOf { it.size } + directories.sumOf { it.size() })
        .also { directorySizes.add(it) }

    val rootDirectory = parseDirectories(file)

    val TOTAL_DISK_SPACE = 70000000
    val UPDATE_SIZE = 30000000
    val rootDirectorySize = rootDirectory.size()
    val unusedSpace = TOTAL_DISK_SPACE - rootDirectorySize
    val spaceToBeDeleted = UPDATE_SIZE - unusedSpace

    while (directorySizes.isNotEmpty()) {
        val size = directorySizes.poll()
        if (spaceToBeDeleted - size <= 0) {
            return size
        }
    }
    return -1
}

private fun parseDirectories(file: java.io.File): Directory {
    val rootDirectory = Directory(
        name = "root",
        parentDirectory = null,
    )
    var currentDirectory = rootDirectory
    file.readLines().forEach { line ->
        if (line.contains('$')) {
            if (line.contains("cd")) {
                currentDirectory = if (line.contains('/')) {
                    rootDirectory
                } else if (line.contains("..")) {
                    currentDirectory.parentDirectory!!
                } else {
                    val directoryName = line.split(' ').last()
                    currentDirectory.directories.find { it.name == directoryName }!!
                }
            }
        } else if (line.startsWith("dir")) {
            val newDirectory = Directory(
                name = line.split(' ').last(),
                parentDirectory = currentDirectory,
            )
            currentDirectory.directories.add(newDirectory)
        } else {
            val newFile = File(
                name = line.split(' ').last(),
                size = line.split(' ').first().toInt(),
            )
            currentDirectory.files.add(newFile)
        }
    }
    return rootDirectory
}

private class Directory(
    val name: String,
    val parentDirectory: Directory?,
    val directories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<File> = mutableListOf(),
)

private data class File(
    val name: String,
    val size: Int,
)
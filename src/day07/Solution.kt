package day07

import day07.FileSystem.Type.DIRECTORY
import day07.FileSystem.Type.FILE
import day07.FileSystem.Type.ROOT_DIRECTORY
import readInput

fun main() {
    fun part1(input: List<String>): Int =
        FileSystem(input).directories
            .filter { it.size <= 100000 }.sumOf { it.size }

    fun part2(input: List<String>): Int {
        val totalDiscSpaceAvailable = 70000000
        val neededSpace = 30000000

        val fileSystem = FileSystem(input)
        val notEmptySpace = fileSystem.root.size

        return fileSystem.directories.sortedBy { it.size }.find {
            it.size >= neededSpace - (totalDiscSpaceAvailable - notEmptySpace)
        }?.size ?: error("There is no chance for you to empty this much space")
    }

    val testInput = readInput("07", "test_input")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("07", "input")
    println(part1(input))
    println(part2(input))
}

class FileSystem(input: List<String>) {
    enum class Type { FILE, DIRECTORY, ROOT_DIRECTORY }

    data class Element(
        val type: Type,
        val path: String,
        val children: MutableList<String> = mutableListOf(),
        val parent: String? = null,
        var size: Int = 0
    )

    fun get(path: String): Element = fileSystem[path] ?: error("Element is not found")

    val root: Element
        get() = this._root

    val directories: List<Element>
        get() = fileSystem.values.filterNot { it.type == FILE }.toList()

    val all: List<Element>
        get() = fileSystem.values.toList()

    private val fileSystem = mutableMapOf<String, Element>()
    private val _root: Element = Element(type = ROOT_DIRECTORY, path = "")

    private fun propagateAddedFileSize(path: String, size: Int) {
        val queue = ArrayDeque(listOf(path))

        while (queue.isNotEmpty()) {
            val element = fileSystem[queue.removeFirst()]!!
            when (element.type) {
                ROOT_DIRECTORY -> element.size = element.size + size
                DIRECTORY -> run {
                    element.size = element.size + size
                    queue.add(element.parent!!)
                }

                FILE -> run {
                    element.size = size
                    queue.add(element.parent!!)
                }
            }
        }
    }

    init {
        fun String.isToSwitchToTheRoot(): Boolean = this == """$ cd /"""
        fun String.isToMoveOut(): Boolean = this == """$ cd .."""
        fun String.isToMoveIn(): Boolean = this.matches("""\$ cd [a-zA-Z0-9]+""".toRegex())
        fun String.isToPrintOut(): Boolean = this == """$ ls"""
        fun String.isDirectory(): Boolean = this.matches("""dir [a-zA-Z0-9]+""".toRegex())
        fileSystem.putIfAbsent(root.path, root)
        var currentElement = root
        input.forEach { line ->
            when {
                line.isToSwitchToTheRoot() -> run {
                    currentElement = root
                }

                line.isToMoveOut() -> run {
                    currentElement = fileSystem[currentElement.parent] ?: error("Illegal state: no parent")
                }

                line.isToMoveIn() -> run {
                    val dirName = line.split(" ").last()
                    val dirPath = "${currentElement.path}/$dirName"
                    currentElement = fileSystem[currentElement.children.find { it == dirPath }]
                        ?: error("Illegal state: no child found by path $dirPath")
                }

                line.isToPrintOut() -> run { }

                line.isDirectory() -> run {
                    val (_, dirName) = line.split(" ")
                    val dirPath = "${currentElement.path}/$dirName"
                    val newElement = Element(DIRECTORY, dirPath, parent = currentElement.path)
                    fileSystem.putIfAbsent(dirPath, newElement)
                    currentElement.children.add(newElement.path)
                }

                else -> run {
                    val (fileSize, fileName) = line.split(" ")
                    val path = "${currentElement.path}/$fileName"
                    val newElement = Element(FILE, path, size = fileSize.toInt())
                    fileSystem.putIfAbsent(path, newElement)
                    currentElement.children.add(newElement.path)
                    propagateAddedFileSize(currentElement.path, fileSize.toInt())
                }
            }
        }
    }
}
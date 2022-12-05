package day05

import chunkedBy
import readInput

fun main() {

    fun parseStacks(input: List<String>): List<ArrayDeque<Char>> {
        val stacks = input.last().split("\\s+".toRegex()).filter(String::isNotBlank).map { ArrayDeque<Char>() }

        for (lineIndex in input.size - 2 downTo 0) {
            for (craneIndex in stacks.indices) {
                when (val crate = input[lineIndex].getOrNull(4 * craneIndex + 1)) {
                    ' ', null -> continue
                    else -> stacks[craneIndex].add(crate)
                }
            }
        }

        return stacks
    }

    fun parseInstruction(line: String): Move {
        val (move, from, to) = Move.matchRegex.find(line)?.destructured!!
        return Move(move.toInt(), from.toInt() - 1, to.toInt() - 1)
    }

    fun part1(input: List<String>): String {
        val (stacksInput, moves) = input.chunkedBy(String::isEmpty)

        val stacks = parseStacks(stacksInput)

        moves.forEach { line ->
            val (quantity, from, to) = parseInstruction(line)
            repeat(quantity) { stacks[to].addLast(stacks[from].removeLast()) }
        }

        return stacks.map { it.last() }.joinToString(separator = "")
    }

    fun ArrayDeque<Char>.removeNLast(times: Int): List<Char> {
        val temp = mutableListOf<Char>()
        repeat(times) {
            temp.add(this.removeLast())
        }
        return temp.reversed()
    }

    fun part2(input: List<String>): String {
        val (stacksInput, moves) = input.chunkedBy(String::isEmpty)

        val stacks = parseStacks(stacksInput)

        moves.forEach { line ->
            val (quantity, from, to) = parseInstruction(line)
            stacks[to].addAll(stacks[from].removeNLast(quantity))
        }

        return stacks.map { it.last() }.joinToString(separator = "")
    }

    val testInput = readInput("05", "test_input")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("05", "input")
    println(part1(input))
    println(part2(input))
}

data class Move(val quantity: Int, val source: Int, val target: Int) {
    companion object {
        val matchRegex = "move ([0-9]+) from ([0-9]+) to ([0-9]+)".toRegex()
    }
}
package day01

import chunkedBy
import readInput

fun main() {
    fun summarizeCaloriesOfNTopElves(input: List<String>, elvesToInvolve: Int): Int {
        return input.chunkedBy(String::isEmpty) { it.sumOf(String::toInt) }
            .sortedDescending().take(elvesToInvolve).sum()
    }

    fun part1(input: List<String>): Int {
        return summarizeCaloriesOfNTopElves(input, 1)
    }

    fun part2(input: List<String>): Int {
        return summarizeCaloriesOfNTopElves(input, 3)
    }

    val testInput = readInput("01", "test_input")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("01", "input")
    println(part1(input))
    println(part2(input))
}

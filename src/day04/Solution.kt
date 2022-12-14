package day04

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val (fFrom, fTo, sFrom, sTo) = line.split(',', '-', limit = 4).map(String::toInt)
            1.takeIf { (fFrom <= sFrom && sTo <= fTo) || (sFrom <= fFrom && fTo <= sTo) } ?: 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (fFrom, fTo, sFrom, sTo) = line.split(',', '-', limit = 4).map(String::toInt)
            1.takeIf { fFrom <= sTo && sFrom <= fTo } ?: 0
        }
    }

    val testInput = readInput("04", "test_input")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("04", "input")
    println(part1(input))
    println(part2(input))
}

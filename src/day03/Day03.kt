package day03

import readInput

fun main() {
    fun Char.priority() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("Illegal character")
    }

    fun List<String>.findRepeatedElement() = this.map(String::toSet).reduce(Set<Char>::intersect).single()

    fun part1(input: List<String>): Int =
        input.sumOf { line -> line.chunked(line.length / 2).findRepeatedElement().priority() }

    fun part2(input: List<String>): Int =
        input.chunked(3).sumOf { chunk -> chunk.findRepeatedElement().priority() }

    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("day03/Day03")
    println(part1(input))
    println(part2(input))
}

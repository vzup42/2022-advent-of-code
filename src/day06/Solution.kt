package day06

import readInput

fun main() {
    fun findLastIndexOfFirstNDistinctCharacters(line: String, n: Int): Int {
        for (i in n..line.length) {
            if (line.substring(i - n, i).toSet().size == n) return i
        }
        error("No distinct characters")
    }

    fun part1(input: List<String>): Int = findLastIndexOfFirstNDistinctCharacters(input.first(), 4)
    fun part2(input: List<String>): Int = findLastIndexOfFirstNDistinctCharacters(input.first(), 14)

    check(part1(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz")) == 5)
    check(part1(listOf("nppdvjthqldpwncqszvftbrmjlhg")) == 6)
    check(part1(listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")) == 10)
    check(part1(listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")) == 11)

    check(part2(listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")) == 19)
    check(part2(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz")) == 23)
    check(part2(listOf("nppdvjthqldpwncqszvftbrmjlhg")) == 23)
    check(part2(listOf("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg")) == 29)
    check(part2(listOf("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")) == 26)

    val input = readInput("06", "input")
    println(part1(input))
    println(part2(input))
}

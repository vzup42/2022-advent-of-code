package day10

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var cycle = 1
        var register = 1
        var signalStrength = 0
        var memory = 0
        var inputIndex = 0

        do {
            if ((cycle - 20) % 40 == 0) {
                signalStrength += (register * cycle)
            }
            if (memory == 0) {
                val command = input[inputIndex++].split(" ")
                if (command.size == 2) {
                    memory = command[1].toInt()
                }
            } else {
                register += memory
                memory = 0
            }
            cycle++
        } while (inputIndex < input.size)

        return signalStrength
    }

    fun part2(input: List<String>): String {
        var cycle = 1
        var register = 1
        var memory = 0
        var inputIndex = 0
        val str = mutableListOf<String>()

        do {
            str.add(if ((cycle - 1) % 40 in (register - 1..register + 1)) "#" else ".")
            if (memory == 0) {
                val command = input[inputIndex++].split(" ")
                if (command.size == 2) {
                    memory = command[1].toInt()
                }
            } else {
                register += memory
                memory = 0
            }
            cycle++
        } while (inputIndex < input.size)

        return str.joinToString(separator = "")
    }

    val testInput = readInput("10", "test_input_2")
    check(part1(testInput) == 13140)
    part2(testInput).chunked(40).forEach(::println)

    val input = readInput("10", "input")
    println(part1(input))
    part2(input).chunked(40).forEach(::println)
}

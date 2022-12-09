package day09

import readInput
import kotlin.math.abs

fun main() {

    fun moveRight(pair: Pair<Int, Int>, step: Int = 1) = Pair(pair.first + step, pair.second)
    fun moveLeft(pair: Pair<Int, Int>, step: Int = 1) = Pair(pair.first - step, pair.second)
    fun moveUp(pair: Pair<Int, Int>, step: Int = 1) = Pair(pair.first, pair.second + step)
    fun moveDown(pair: Pair<Int, Int>, step: Int = 1) = Pair(pair.first, pair.second - step)

    fun Pair<Int, Int>.isAround(pair: Pair<Int, Int>) =
        abs(this.first - pair.first) <= 1 && abs(this.second - pair.second) <= 1

    val positionChanges = listOf(
        Pair(1, 1), Pair(0, 1), Pair(1, 0), Pair(-1, -1), Pair(0, -1), Pair(-1, 0), Pair(-1, 1), Pair(1, -1)
    )

    fun moveFollower(leader: Pair<Int, Int>, follower: Pair<Int, Int>) = positionChanges.mapIndexed { index, change ->
        index to abs(leader.first - follower.first - change.first) + abs(leader.second - follower.second - change.second)
    }.minBy { it.second }.first.let { finalIndex ->
        Pair(follower.first + positionChanges[finalIndex].first, follower.second + positionChanges[finalIndex].second)
    }

    fun solve(input: List<String>, ropeLength: Int): Int {
        val visitedByTale = mutableSetOf<Pair<Int, Int>>()
        val rope = Array(ropeLength) { Pair(0, 0) }
        val headIndex = 0
        val tailIndex = ropeLength - 1

        visitedByTale.add(rope[tailIndex])

        fun move(steps: Int, moveInDirection: (Pair<Int, Int>) -> Pair<Int, Int>) {
            repeat(steps) {
                rope[headIndex] = moveInDirection(rope[headIndex])
                (1 until ropeLength).forEach { index ->
                    if (!rope[index].isAround(rope[index - 1])) {
                        rope[index] = moveFollower(rope[index - 1], rope[index])
                    }
                }
                visitedByTale.add(rope[tailIndex])
            }
        }

        input.forEach { line ->
            val (direction, stepsStr) = line.split(" ")
            val steps = stepsStr.toInt()
            when (direction) {
                "R" -> move(steps, ::moveRight)
                "L" -> move(steps, ::moveLeft)
                "U" -> move(steps, ::moveUp)
                "D" -> move(steps, ::moveDown)
            }
        }

        return visitedByTale.size
    }

    fun part1(input: List<String>): Int = solve(input, ropeLength = 2)
    fun part2(input: List<String>): Int = solve(input, ropeLength = 10)

    val testInput = readInput("09", "test_input")
    check(part1(testInput) == 13)
    val testInput2 = readInput("09", "test_input_2")
    check(part2(testInput2) == 36)

    val input = readInput("09", "input")
    println(part1(input))
    println(part2(input))
}

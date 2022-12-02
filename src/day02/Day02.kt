package day02

import day02.Game.Companion.against
import day02.Game.Companion.by
import day02.Game.Outcome.DRAW
import day02.Game.Outcome.LOSS
import day02.Game.Outcome.VICTORY
import day02.Game.Shape.PAPER
import day02.Game.Shape.ROCK
import day02.Game.Shape.SCISSORS
import readInput

fun main() {
    fun parseShape(shape: String) = when(shape) {
        "A", "X" -> ROCK
        "B", "Y" -> PAPER
        "C", "Z" -> SCISSORS
        else -> throw IllegalArgumentException()
    }

    fun part1(input: List<String>): Int =
        input.sumOf {
            val (opponentShapeString, myShapeString) = it.split(" ", limit = 2)

            val opponentShape = parseShape(opponentShapeString)
            val myShape = parseShape(myShapeString)

            (myShape against opponentShape).score + myShape.score
        }

    fun parseOutcome(outcome: String) = when (outcome) {
        "X" -> LOSS
        "Y" -> DRAW
        "Z" -> VICTORY
        else -> throw IllegalArgumentException()
    }

    fun part2(input: List<String>): Int =
        input.sumOf {
            val (opponentShapeString, myOutcomeString) = it.split(" ", limit = 2)

            val opponentShape = parseShape(opponentShapeString)
            val myOutcome = parseOutcome(myOutcomeString)

            myOutcome.score + (myOutcome by opponentShape).score

        }

    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("day02/Day02")
    println(part1(input))
    println(part2(input))
}

class Game {
    enum class Shape(val score: Int) {
        ROCK(1), PAPER(2), SCISSORS(3);
    }

    enum class Outcome(val score: Int) {
        LOSS(0), DRAW(3), VICTORY(6);
    }

    companion object {
        private val outcomes: Map<Shape, Map<Shape, Outcome>> = mapOf(
            ROCK to mapOf(ROCK to DRAW, PAPER to LOSS, SCISSORS to VICTORY),
            PAPER to mapOf(PAPER to DRAW, SCISSORS to LOSS, ROCK to VICTORY),
            SCISSORS to mapOf(SCISSORS to DRAW, ROCK to LOSS, PAPER to VICTORY)
        )

        private val shapes: Map<Outcome, Map<Shape, Shape>> = mapOf(
            VICTORY to mapOf(ROCK to PAPER, PAPER to SCISSORS, SCISSORS to ROCK),
            DRAW to mapOf(ROCK to ROCK, PAPER to PAPER, SCISSORS to SCISSORS),
            LOSS to mapOf(ROCK to SCISSORS, PAPER to ROCK, SCISSORS to PAPER),
        )

        // Determines the game result for the currently selected shape by an opponent's shape.
        infix fun Shape.against(opponentShape: Shape) = outcomes[this]!![opponentShape]!!

        // Defines which shape to select by an opponent's shape in order to achieve the needed outcome
        infix fun Outcome.by(opponentShape: Shape) = shapes[this]!![opponentShape]!!
    }
}

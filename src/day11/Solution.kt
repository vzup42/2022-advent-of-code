package day11

import chunkedBy
import readInput

fun main() {
    fun parseMonkey(lines: List<String>): Monkey {
        check(lines.size == 6)
        val number = lines[0][7].digitToInt()
        val items = lines[1].substringAfter("items: ").split(" ")
            .map { it.removeSuffix(",").toLong() }.let(::ArrayDeque)
        val operation = lines[2].substringAfter("new = ").split(" ")
            .let { Operation.from(it.component1(), it.component2(), it.component3()) }
        val divisibleBy = lines[3].substringAfter("by ").toLong()
        val monkeyNumberWhenTestPasses = lines[4].substringAfter("monkey ").toInt()
        val monkeyNumberWhenTestFails = lines[5].substringAfter("monkey ").toInt()
        return Monkey(
            number = number,
            items = items,
            operation = operation,
            divisibleBy = divisibleBy,
            monkeyNumberWhenTestPasses = monkeyNumberWhenTestPasses,
            monkeyNumberWhenTestFails = monkeyNumberWhenTestFails
        )
    }

    fun part1(input: List<String>): Int {
        val monkeys = input.chunkedBy(String::isEmpty, ::parseMonkey).associateBy { it.number }
        val rounds = 20

        repeat(rounds) {
            monkeys.forEach { (_, monkey) ->
                while (monkey.hasItemsToInspect()) {
                    val worryLevel = monkey.inspectNextItem() / 3
                    val to = monkey.whomToThrow(worryLevel)
                    monkeys[to]!!.catchItem(worryLevel)
                }
            }
        }

        return monkeys.values.map { it.itemsInspected }.sortedDescending().take(2).fold(1) { a, b -> a * b }
    }

    fun part2(input: List<String>): Long {
        val monkeys = input.chunkedBy(String::isEmpty, ::parseMonkey).associateBy { it.number }
        val modulo = monkeys.values.map { it.divisibleBy }.fold(1L) { a, b -> a * b }
        val rounds = 10000

        repeat(rounds) {
            monkeys.forEach { (_, monkey) ->
                while (monkey.hasItemsToInspect()) {
                    val worryLevel = monkey.inspectNextItem() % modulo
                    val to = monkey.whomToThrow(worryLevel)
                    monkeys[to]!!.catchItem(worryLevel)
                }
            }
        }

        return monkeys.values.map { it.itemsInspected }.sortedDescending().take(2).fold(1) { a, b -> a * b }
    }

    val testInput = readInput("11", "test_input")
    check(part1(testInput) == 10605)
    check(part2(testInput) == 2713310158)

    val input = readInput("11", "input")
    println(part1(input))
    println(part2(input))
}

data class Monkey(
    val number: Int,
    val divisibleBy: Long,
    private val items: ArrayDeque<Long>,
    private val operation: Operation,
    private val monkeyNumberWhenTestPasses: Int,
    private val monkeyNumberWhenTestFails: Int
) {
    val itemsInspected: Int
        get() = _counter

    fun hasItemsToInspect() = items.isNotEmpty()

    fun inspectNextItem(): Long {
        _counter++
        return operation.perform(items.removeFirst())
    }

    fun whomToThrow(worryLevel: Long) =
        if (worryLevel % divisibleBy == 0L) monkeyNumberWhenTestPasses else monkeyNumberWhenTestFails

    fun catchItem(worryLevel: Long) {
        items.add(worryLevel)
    }

    private var _counter = 0
}

data class Operation(val firstOperand: Long?, val operator: Long.(Long) -> Long, val secondOperand: Long?) {

    fun perform(old: Long): Long {
        val a = firstOperand ?: old
        val b = secondOperand ?: old
        val func = operator
        return a.func(b)
    }

    companion object {
        fun from(firstOperand: String, operator: String, secondOperand: String) =
            Operation(
                firstOperand = firstOperand.takeIf { it != "old" }?.toLong(),
                operator = if (operator == "*") Long::times else Long::plus,
                secondOperand = secondOperand.takeIf { it != "old" }?.toLong()
            )
    }
}
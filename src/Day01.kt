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

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

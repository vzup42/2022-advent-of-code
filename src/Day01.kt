fun main() {
    fun summarizeCaloriesOfTopElves(input: List<String>, elvesToInvolve: Int): Int {
        val separatorIndicies = input.indices.filter { input[it].isEmpty() }
        val leftIntervalSides = listOf(0) + separatorIndicies.map { it + 1 }
        val rightIntervalSides = separatorIndicies + input.size
        require(leftIntervalSides.size == rightIntervalSides.size)

        return leftIntervalSides.indices.map { index ->
            input.subList(leftIntervalSides[index], rightIntervalSides[index]).map(String::toInt).sum()
        }.sortedDescending().take(elvesToInvolve).sum()
    }

    fun part1(input: List<String>): Int {
        return summarizeCaloriesOfTopElves(input, 1)
    }

    fun part2(input: List<String>): Int {
        return summarizeCaloriesOfTopElves(input, 3)
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

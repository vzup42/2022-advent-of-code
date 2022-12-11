package day08

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val forest = input.map { it.toCharArray() }.toTypedArray()
        val visible = Array(forest.size) { index ->
            run {
                when (index) {
                    0, forest.size - 1 -> forest[index].map { true }.toTypedArray()
                    else -> forest[index].mapIndexed { subIndex, _ ->
                        when (subIndex) {
                            0, forest[index].size - 1 -> true
                            else -> false
                        }
                    }.toTypedArray()
                }
            }
        }

        // Left --> Right

        forest.forEachIndexed { forestIndex, line ->
            var tallest = 0
            line.indices.forEach { lineIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }

        // Right --> Left
        forest.forEachIndexed { forestIndex, line ->
            var tallest = 0
            line.indices.reversed().forEach { lineIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }

        // Top --> Bottom

        forest[0].indices.forEach { lineIndex ->
            var tallest = 0
            forest.indices.forEach { forestIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }

        // Bottom --> Top

        forest[0].indices.forEach { lineIndex ->
            var tallest = 0
            forest.indices.reversed().forEach { forestIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }


        return visible.sumOf { it.count { it } }
    }

    fun part2(input: List<String>): Int {
        val forest = input.map { it.toCharArray() }.toTypedArray()
        val visible = Array(forest.size) { index ->
            run {
                when (index) {
                    0, forest.size - 1 -> forest[index].map { true }.toTypedArray()
                    else -> forest[index].mapIndexed { subIndex, _ ->
                        when (subIndex) {
                            0, forest[index].size - 1 -> true
                            else -> false
                        }
                    }.toTypedArray()
                }
            }
        }

        // Left --> Right

        forest.forEachIndexed { forestIndex, line ->
            var tallest = 0
            line.indices.forEach { lineIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }

        // Right --> Left
        forest.forEachIndexed { forestIndex, line ->
            var tallest = 0
            line.indices.reversed().forEach { lineIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }

        // Top --> Bottom

        forest[0].indices.forEach { lineIndex ->
            var tallest = 0
            forest.indices.forEach { forestIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }

        // Bottom --> Top

        forest[0].indices.forEach { lineIndex ->
            var tallest = 0
            forest.indices.reversed().forEach { forestIndex ->
                if (forest[forestIndex][lineIndex].digitToInt() > tallest) {
                    visible[forestIndex][lineIndex] = true
                    tallest = forest[forestIndex][lineIndex].digitToInt()
                }
            }
        }



        var theHighestScenicScore = 0

        forest.forEachIndexed{ forestIndex, line ->
            line.forEachIndexed{ lineIndex, tree ->

                if(forestIndex==3 && lineIndex==2) {
                    1+1
                }

                val current = tree.digitToInt()
                var counter = 0
                var tmpIndex = 0
                var score = 1

                // To the right
                counter = 0
                tmpIndex = lineIndex + 1
                while (tmpIndex < line.size && forest[forestIndex][tmpIndex].digitToInt()<current) {
                    counter++
                    tmpIndex++
                }
                if(tmpIndex < line.size) counter++
                score *= counter

                // To the left
                counter = 0
                tmpIndex = lineIndex - 1
                while (tmpIndex >= 0 && forest[forestIndex][tmpIndex].digitToInt()<current) {
                    counter++
                    tmpIndex--
                }
                if(tmpIndex >= 0) counter++
                score *= counter

                // To the top
                counter = 0
                tmpIndex = forestIndex - 1
                while (tmpIndex >= 0 && forest[tmpIndex][lineIndex].digitToInt()<current) {
                    counter++
                    tmpIndex--
                }
                if(tmpIndex >= 0) counter++
                score *= counter

                // To the bottom
                counter = 0
                tmpIndex = forestIndex + 1
                while (tmpIndex < forest.size && forest[tmpIndex][lineIndex].digitToInt()<current) {
                    counter++
                    tmpIndex++
                }
                if(tmpIndex < forest.size) counter++
                score *= counter

                if(score > theHighestScenicScore) theHighestScenicScore = score
            }
        }


        return theHighestScenicScore
    }

    val testInput = readInput("08", "test_input")
    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = readInput("08", "input")
    println(part1(input))
    println(part2(input))
}

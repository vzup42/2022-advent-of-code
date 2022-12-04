import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: String, name: String) = File("src/day$day", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * Breaks up this list into a list of lists using provided isSeparator function
 */
fun <T> List<T>.chunkedBy(isSeparator: (T) -> Boolean): List<List<T>> =
    chunkedBy(isSeparator) { it }

/*
 * Breaks up this list into a list of lists using provided isSeparator function
 * and applies the given transform function to an each.
 */
fun <T, R> List<T>.chunkedBy(isSeparator: (T) -> Boolean, transform: (List<T>) -> R): List<R> {
    val separatorIndicies = this.indices.filter { isSeparator(this[it]) }

    val leftIntervalSides = listOf(0) + separatorIndicies.map { it + 1 }
    val rightIntervalSides = separatorIndicies + this.size

    require(leftIntervalSides.size == rightIntervalSides.size)
    return leftIntervalSides.zip(rightIntervalSides).map { (left, right) -> this.subList(left, right) }.map(transform)
}
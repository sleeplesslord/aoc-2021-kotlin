import java.io.File
import java.rmi.UnexpectedException
import java.util.*

fun getCorruptedPoints(c: Char): Int {
    return when (c) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> throw UnexpectedException(c.toString())
    }
}

fun getIncompletePoints(c: Char): Long {
    return when (c) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> throw UnexpectedException(c.toString())
    }
}

fun day10part1(inputFile: String) {
    val lines = File(inputFile).readLines()
    val mappings = hashMapOf('{' to '}', '(' to ')', '[' to ']', '<' to '>')

    var sum = 0
    lines.forEach { line ->
        val stack: Stack<Char> = Stack()
        for (c in line) {
            if (mappings.keys.contains(c)) {
                stack.push(mappings[c])
            } else {
                if (stack.pop() != c) {
                    sum += getCorruptedPoints(c)
                    break
                }
            }
        }
    }
    println(sum)
}

fun day10part2(inputFile: String) {
    val lines = File(inputFile).readLines()
    val mappings = hashMapOf('{' to '}', '(' to ')', '[' to ']', '<' to '>')

    val validStacks: List<Stack<Char>> = lines.mapNotNull { line ->
        val stack: Stack<Char> = Stack()
        var corrupted = false
        for (c in line) {
            if (mappings.keys.contains(c)) {
                stack.push(mappings[c])
            } else {
                if (stack.pop() != c) {
                    corrupted = true
                    break
                }
            }
        }
        when (corrupted) {
            true -> null
            else -> stack
        }
    }

    println(validStacks.map { stack ->
        stack.toList().reversed().map { getIncompletePoints(it) }.reduce { a, b -> 5 * a + b }
    }.sorted()[validStacks.size / 2])
}
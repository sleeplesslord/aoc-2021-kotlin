import java.io.File
import java.util.*
import kotlin.collections.HashSet

fun getAdjacent(x: Int, y: Int, x_max: Int, y_max: Int): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    if (x > 0) {
        result.add(Pair(x - 1, y))
    }
    if (y > 0) {
        result.add(Pair(x, y - 1))
    }
    if (y < y_max) {
        result.add(Pair(x, y + 1))
    }
    if (x < x_max) {
        result.add(Pair(x + 1, y))
    }

    return result
}

fun findLowPoints(map: List<String>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    val xMax = map[0].length - 1
    val yMax = map.size - 1

    for (y in map.indices) {
        for (x in 0 until map[y].length) {
            if (getAdjacent(x, y, xMax, yMax).all { map[it.second][it.first].digitToInt() > map[y][x].digitToInt() }) {
                result.add(Pair(x, y))
            }
        }
    }
    return result
}

fun day9part1(inputFile: String) {
    val lines = File(inputFile).readLines()

    val points: List<Pair<Int, Int>> = findLowPoints(lines)
    println(points.sumOf { pair ->
        lines[pair.second][pair.first].digitToInt() + 1
    })
}

fun bfsForBasin(map: List<String>, start: Pair<Int, Int>): HashSet<Pair<Int, Int>> {
    val queue: Queue<Pair<Int, Int>> = LinkedList()
    val result: HashSet<Pair<Int, Int>> = HashSet()

    result.add(start)
    queue.add(start)

    val xMax = map[0].length - 1
    val yMax = map.size - 1

    while (!queue.isEmpty()) {
        val currPos = queue.remove()
        val adjacent = getAdjacent(currPos.first, currPos.second, xMax, yMax)
        val unseen = adjacent.filter { !result.contains(it) }
        unseen.filter { map[it.second][it.first] > map[currPos.second][currPos.first] }
            .filter { map[it.second][it.first] != '9' }.forEach { queue.add(it); result.add(it) }
    }

    return result
}

fun day9part2(inputFile: String) {
    val lines = File(inputFile).readLines()
    val lowPoints = findLowPoints(lines)

    println(lowPoints.map { bfsForBasin(lines, it) }.sortedBy { it.size }.reversed().slice(0..2).map { it.size }
        .fold(1) { a, b -> a * b })
}
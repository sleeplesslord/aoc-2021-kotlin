import java.io.File
import java.util.Collections.max
import kotlin.math.abs

fun day7part1(inputFile: String) {
    val crabs = File(inputFile).readLines().first().split(",").map { it.toInt() }.sorted()
    val median = crabs[crabs.size / 2]
    println(crabs.sumOf { abs(median - it) })
}

fun diffHelper(diff: Int): Int {
    return diff * (diff + 1) / 2
}

fun medianSum(prospect: Int, crabs: List<Int>): Int {
    return crabs.map { abs(prospect - it) }.sumOf{ diffHelper(it) }
}

fun day7part2(inputFile: String) {
    val crabs = File(inputFile).readLines().first().split(",").map { it.toInt() }.sorted()
    val valueMap: Map<Int, Int> = (0..max(crabs)).toList().associateWith { medianSum(it, crabs) }
    println(valueMap.toList().reduce { a, b ->
        if (a.second < b.second) a else b
    })
}
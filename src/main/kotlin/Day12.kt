import java.io.File

fun caveDfs(visitedSmallCaves: Set<String>, currPos: String, caves: Map<String, List<String>>, doubleVisit: Boolean): List<String> {
    if (currPos == "end") {
        return listOf("end")
    }

    val neighbors = caves[currPos]!!
    var pathList = listOf<String>()
    for (neighbor in neighbors) {
        if (visitedSmallCaves.contains(neighbor)) {
            if (!doubleVisit && neighbor != "start") {
                pathList = pathList + caveDfs(visitedSmallCaves, neighbor, caves, true).map { "$currPos-$it" }.toList()
            }
            continue
        }

        val smallCavesCopy = visitedSmallCaves.toHashSet()
        if (neighbor.all { it.isLowerCase() }) {
            smallCavesCopy.add(neighbor)
        }

        pathList = pathList + caveDfs(smallCavesCopy, neighbor, caves, doubleVisit).map { "$currPos-$it" }.toList()
    }

    return pathList
}

fun day12part1(inputFile: String) {
    val caves = hashMapOf<String, MutableList<String>>()
    File(inputFile).readLines().forEach { line ->
        val caveNames = line.split("-")
        caves.putIfAbsent(caveNames[0], mutableListOf())
        caves.putIfAbsent(caveNames[1], mutableListOf())

        caves[caveNames[0]]!!.add(caveNames[1])
        caves[caveNames[1]]!!.add(caveNames[0])
    }

    println(caveDfs(hashSetOf("start"), "start", caves, false).size)
}
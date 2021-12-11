import java.io.File

data class Octopus(var energy: Int, var hasFlashed: Boolean)

fun getAdjacent(x: Int, y: Int): List<Pair<Int, Int>> {
    val yMax = 10
    val xMax = 10
    val result = mutableListOf<Pair<Int, Int>>()

    for (xDelta in -1..1) {
        for (yDelta in -1..1) {
            if (xDelta == 0 && yDelta == 0) continue
            val xProspect = x + xDelta
            val yProspect = y + yDelta
            if (xProspect in 0 until xMax && yProspect in 0 until yMax) {
                result.add(Pair(xProspect, yProspect))
            }
        }
    }

    return result
}

fun octopusIteration(input: List<List<Octopus>>): Long {
    input.forEach {
        it.forEach { octopus ->
            octopus.energy += 1
        }
    }

    var hasFlashed = true
    var flashCount = 0L
    while (hasFlashed) {
        hasFlashed = false
        for (x in input.indices) {
            for (y in 0 until input[x].size) {
                if (input[y][x].energy > 9 && !input[y][x].hasFlashed) {
                    hasFlashed = true
                    getAdjacent(x, y).forEach { pair ->
                        input[pair.second][pair.first].energy += 1
                    }
                    input[y][x].hasFlashed = true
                    flashCount++
                }
            }
        }
    }

    input.forEach {
        it.forEach { o ->
            if (o.hasFlashed) {
                o.energy = 0
            }
            o.hasFlashed = false
        }
    }

    return flashCount
}

fun day11part1(inputFile: String) {
    val octopodes = File(inputFile).readLines().map { it.toList().map { c -> Octopus(c.digitToInt(), false)  } }
    var sum = 0L
    for (i in 1..100) {
        sum += octopusIteration(octopodes)
    }
    println(sum)
}

fun day11part2(inputFile: String) {
    val octopodes = File(inputFile).readLines().map { it.toList().map { c -> Octopus(c.digitToInt(), false)  } }
    var count = 1
    while (octopusIteration(octopodes) != 100L) {
        count++
    }
    println(count)
}

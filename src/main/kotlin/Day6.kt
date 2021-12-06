import java.io.File

fun simulateFish(fish: List<Int>): List<Int> {
    val newFish = fish.count { it == 0 }
    return fish.map { if (it > 0) it - 1 else 6 }.toList() + List(newFish) { 8 }
}

fun simulateFish(fish: HashMap<Int, Long>): HashMap<Int, Long> {
    val result = HashMap<Int, Long>()
    for (days in 1..8) {
        result[days - 1] = fish[days] ?: 0
    }
    val day6 = result[6] ?: 0
    result[6] = day6 + (fish[0] ?: 0)
    result[8] = fish[0] ?: 0

    return result
}

fun day6part1(inputFile: String) {
    val line = File(inputFile).readLines().first()
    var fishes = line.split(",").map { it.toInt() }
    for (i in 1..80) {
        fishes = simulateFish(fishes)
    }

    println(fishes.size)
}

fun day6part2(inputFile: String) {
    val line = File(inputFile).readLines().first()
    val fishes = line.split(",").map { it.toInt() }
    var fishMap = fishes.fold(HashMap<Int, Long>()) { acc, i ->
        when (val count = acc[i]) {
            null -> acc[i] = 1
            else -> acc[i] = count + 1
        }
        acc
    }

    for (i in 1..256) {
        fishMap = simulateFish(fishMap)
    }
    print(fishMap.values.sum())
}
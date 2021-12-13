import java.io.File

fun getFoldParamFunction(direction: String): (Pair<Int, Int>) -> Int {
    if (direction == "x") {
        return { pair: Pair<Int, Int> -> pair.first }
    } else {
        return { pair: Pair<Int, Int> -> pair.second }
    }
}

fun day13part2(inputFile: String) {
    val input = File(inputFile).readLines().filter { it.isNotEmpty() }.groupBy { it.split(" ").size == 3 }
    var points = input[false]!!.map { it.split(",").map { c -> c.toInt() } }.map { Pair(it[0], it[1]) }.toSet()
    val folds = input[true]!!.map { it.split(" ")[2].split("=") }

    folds.forEach { fold ->
        val foldParam = getFoldParamFunction(fold[0])
        val foldPoint = fold[1].toInt()
        points = points.map {
            if (foldParam(it) < foldPoint) {
                it
            } else {
                if (fold[0] == "x") {
                    Pair(2 * foldPoint - foldParam(it), it.second)
                } else {
                    Pair(it.first, 2 * foldPoint - foldParam(it))
                }
            }
        }.toSet()
    }

    val xMax = points.maxOf { it.first }
    val yMax = points.maxOf { it.second }
    for (y in 0..yMax) {
        for (x in 0..xMax) {
            if (points.contains(Pair(x, y))) {
                print('0')
            } else {
                print('.')
            }
        }
        println()
    }
}

fun day13part1(inputFile: String) {
    val input = File(inputFile).readLines().filter { it.isNotEmpty() }.groupBy { it.split(" ").size == 3 }
    var points = input[false]!!.map { it.split(",").map { c -> c.toInt() } }.map { Pair(it[0], it[1]) }.toSet()
    val folds = input[true]!!.map { it.split(" ")[2].split("=") }

    val fold = folds[0]
    val foldParam = getFoldParamFunction(fold[0])
    val foldPoint = fold[1].toInt()
    points = points.map {
        if (foldParam(it) < foldPoint) {
            it
        } else {
            if (fold[0] == "x") {
                Pair(2 * foldPoint - foldParam(it), it.second)
            } else {
                Pair(it.first, 2 * foldPoint - foldParam(it))
            }
        }
    }.toSet()
    println(points.size)
}
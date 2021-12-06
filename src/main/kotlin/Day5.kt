import java.io.File
import kotlin.math.abs
import kotlin.math.max

fun day5part2(inputFile: String) {
    File(inputFile).useLines { lines ->
        var total = 0
        val hashMap = HashMap<String, Int>()
        lines.map { line ->
            line.split("->").map { it.trim() }.map { it.split(",")}.map { it.map { s -> s.toInt() } }
        }.forEach {
            val deltaX = it[1][0] - it[0][0]
            val deltaY = it[1][1] - it[0][1]

            val maxSlope = max(abs(deltaX), abs(deltaY))
            var x = it[0][0]
            var y = it[0][1]
            for (k in 0..maxSlope) {
                when (val currentValue = hashMap["$x, $y"]) {
                    null -> {
                        hashMap["$x, $y"] = 1
                    }
                    1 -> {
                        print("$x, $y\n")
                        total++
                        hashMap["$x, $y"] = 2
                    }
                    else -> {
                        hashMap["$x, $y"] = currentValue + 1
                    }
                }
                x += deltaX / maxSlope
                y += deltaY / maxSlope
            }

        }

        for (y in 0..9) {
            for (x in 0..9) {
                when (val count = hashMap["$x, $y"]) {
                    null -> print('.')
                    else -> print(count)
                }
            }
            print("\n")
        }
        print(total)
    }
}

fun day5part1(inputFile: String) {
    File(inputFile).useLines { lines ->
        var total = 0
        val hashMap = HashMap<String, Int>()
        lines.map { line ->
            line.split("->").map { it.trim() }.map { it.split(",")}.map { it.map { s -> s.toInt() } }
        }.filter {
            it[0][0] == it[1][0] || it[0][1] == it[1][1]
        }.forEach {
            val deltaX = it[1][0] - it[0][0]
            val deltaY = it[1][1] - it[0][1]

            val maxSlope = max(abs(deltaX), abs(deltaY))
            var x = it[0][0]
            var y = it[0][1]
            for (k in 0..maxSlope) {
                when (val currentValue = hashMap["$x, $y"]) {
                    null -> {
                        hashMap["$x, $y"] = 1
                    }
                    1 -> {
                        print("$x, $y\n")
                        total++
                        hashMap["$x, $y"] = 2
                    }
                    else -> {
                        hashMap["$x, $y"] = currentValue + 1
                    }
                }
                x += deltaX / maxSlope
                y += deltaY / maxSlope
            }
        }

        for (y in 0..9) {
            for (x in 0..9) {
                when (val count = hashMap["$x, $y"]) {
                    null -> print('.')
                    else -> print(count)
                }
            }
            print("\n")
        }
        print(total)
    }
}

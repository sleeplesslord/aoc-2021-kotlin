import java.io.File

fun main(args: Array<String>) {
    File("input").useLines { lines ->
        var total = 0
        val hashMap = HashMap<String, Int>()
        lines.map { line ->
            line.split("->").map { it.trim() }.map { it.split(",")}.map { it.map { s -> s.toInt() } }
        }.filter {
            it[0][0] == it[1][0] || it[0][1] == it[1][1]
        }.forEach {
            val start: List<Int>
            val stop: List<Int>
            if (it[0].sum() < it[1].sum()) {
                start = it[0]
                stop = it[1]
            } else {
                start = it[1]
                stop = it[0]
            }
            for (x in start[0]..stop[0]) {
                for (y in start[1]..stop[1]) {
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
                }
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
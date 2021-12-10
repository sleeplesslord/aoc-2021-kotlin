import java.io.File


fun day8part1(inputFile: String) {
    val uniqueLengths = listOf(2, 4, 3, 7)
    File(inputFile).useLines { lines ->
        val count =
            lines.map { it.split(" | ")[1].split(" ").count { digit -> uniqueLengths.contains(digit.length) } }.sum()
        println(count)
    }
}

fun permutations(input: String): List<String> {
    val permList: MutableList<String> = mutableListOf()
    if (input.isEmpty()) {
        return permList
    }

    if (input.length > 1) {
        val next = input.substring(1)
        val nextList = permutations(next)
        nextList.forEach { x ->
            for (i in 0..x.length) {
                permList.add(x.substring(0, i) + input[0] + x.substring(i))
            }
        }
    } else {
        permList.add(input[0].toString())
    }
    return permList
}

fun day8part2(inputFile: String) {
    val validDigits = setOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
    val digitMappings = hashMapOf(
        "abcefg" to '0',
        "cf" to '1',
        "acdeg" to '2',
        "acdfg" to '3',
        "bcdf" to '4',
        "abdfg" to '5',
        "abdefg" to '6',
        "acf" to '7',
        "abcdefg" to '8',
        "abcdfg" to '9'
    )
    val perms = permutations("abcdefg")
    File(inputFile).useLines { lines ->
        val outputs = mutableListOf<Int>()
        lines.forEach {
            val digits = it.replace(" | ", " ")
                .split(" ")
                .map { f -> f.toList().sorted().joinToString("") }
            perms.forEach { perm ->
                val permMapping = perm.zip("abcdefg").toMap()
                val validMapping = digits.all { digit ->
                    validDigits.contains(digit.map { c -> permMapping[c] }.sortedBy { i -> i }.joinToString(""))
                }
                if (validMapping) {
                    val digitsToCheck = digits.slice(10..13)
                    outputs.add(digitsToCheck.map { digit ->
                        digitMappings[digit.map { c -> permMapping[c] }.sortedBy { i -> i }.joinToString("")]
                    }.joinToString("").toInt())
                }
            }
        }
        println(outputs.sum())
    }
}
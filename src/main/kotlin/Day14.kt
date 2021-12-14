import java.io.File

fun polymerStep(polymer: String, rules: Map<String, String>): String {
    val resList = mutableListOf<String>()
    for (i in 0..polymer.length - 2) {
        resList.add(polymer[i] + rules[polymer.substring(i, i + 2)]!! + polymer[i + 1])
    }
    return resList.reduce { acc, s ->
        acc + s.substring(1)
    }
}

fun day14part1(inputFile: String) {
    val input = File(inputFile).readLines().filter { it.isNotEmpty() }.toList()
    var template = input[0]
    val pairRules = input.subList(1, input.size).associate {
        val parts = it.split(" -> ")
        parts[0] to parts[1]
    }

    for (i in 1..4) {
        template = polymerStep(template, pairRules)
        println(template)
    }
    val counts = template.groupingBy { it }.eachCount()
    println(counts.values.maxOrNull()!! - counts.values.minOrNull()!!)
}

fun getPairs(input: String): List<String> {
    val res = mutableListOf<String>()
    for (i in 0..input.length - 2) {
        res.add(input.substring(i, i + 2))
    }

    return res
}

fun <T> reduceMap(acc: HashMap<T, Long>, v: Map<T, Long>): HashMap<T, Long> {
    v.forEach { (key, value) ->
        acc.putIfAbsent(key, 0)
        acc[key] = acc[key]!! + value
    }

    return acc
}

fun day14part2(inputFile: String) {
    val input = File(inputFile).readLines().filter { it.isNotEmpty() }.toList()
    val template = input[0]
    val pairRules = input.subList(1, input.size).associate {
        val parts = it.split(" -> ")
        parts[0] to parts[1]
    }

    val stepOne: Map<String, Map<String, Long>> = pairRules.map {
        var v = it.key
        v = polymerStep(v, pairRules)
        it.key to getPairs(v).groupingBy { pair -> pair }.eachCount().map { c -> c.key to c.value.toLong() }.toMap()
    }.toMap()

    var eight: Map<String, Map<String, Long>> = stepOne
    for (i in 1..3) {
        eight = eight.map { entry ->
            entry.key to entry.value.keys.map { k ->
                eight[k]!!.map { it.key to it.value * entry.value[k]!!}.toMap()
            }.fold(hashMapOf<String, Long>()) { acc, v ->
                reduceMap(acc, v)
            }.toMap()
        }.toMap()
    }

    var thirtytwo: Map<String, Map<String, Long>> = eight
    for (i in 1..2) {
        thirtytwo = thirtytwo.map { entry ->
            entry.key to entry.value.keys.map { k ->
                thirtytwo[k]!!.map { it.key to it.value * entry.value[k]!!}.toMap()
            }.fold(hashMapOf<String, Long>()) { acc, v ->
                reduceMap(acc, v)
            }
        }.toMap()
    }

    val forty = thirtytwo.map { entry ->
        entry.key to entry.value.keys.map { k ->
            eight[k]!!.map { it.key to it.value * entry.value[k]!!}.toMap()
        }.fold(hashMapOf<String, Long>()) { acc, v ->
            reduceMap(acc, v)
        }
    }.toMap()


    val t = getPairs(template).map { forty[it] }.fold(hashMapOf<String, Long>()) { acc, v ->
        reduceMap(acc, v!!)
    }
    val q = t.map { entry ->
        hashMapOf(entry.key[0] to entry.value)
    }.fold(hashMapOf<Char, Long>()) { acc, v ->
        reduceMap(acc, v)
    }

    q[template.last()] = q[template.last()]!! + 1
    println(q.values.maxOrNull()!! - q.values.minOrNull()!!)
}
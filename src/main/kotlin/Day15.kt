import java.io.File
import java.util.*
import kotlin.collections.HashMap

fun dijkstra(world: List<List<Int>>): HashMap<Pair<Int, Int>, Long> {
    val dist: HashMap<Pair<Int, Int>, Long> = hashMapOf()
    val nodeQueue: PriorityQueue<Pair<Pair<Int, Int>, Long>> = PriorityQueue { a, b -> a.second.compareTo(b.second) }
    for (y in world.indices) {
        for (x in 0 until world[0].size) {
            dist[Pair(x, y)] = Long.MAX_VALUE
        }
    }
    dist[Pair(0, 0)] = 0

    val maxX = world[0].size
    val maxY = world.size

    var step = 0
    nodeQueue.add(Pair(Pair(0, 0), 0))
    while (nodeQueue.isNotEmpty()) {
        val next = nodeQueue.peek()
        if (next.first.first == maxX && next.first.second == maxY) {
            break
        }
        nodeQueue.remove(next)
        val nextNode = next.first
        getAdjacentNodesWithoutDiagonal(nextNode.first, nextNode.second, world.size, world[0].size).forEach {
            val alt = dist[nextNode]!! + world[it.second][it.first]
            if (alt < dist[it]!!) {
                if (!nodeQueue.remove(Pair(it, dist[it]))) {
                    dist[it] = alt
                    nodeQueue.add(Pair(it, alt))
                }
            }
        }
    }

    return dist
}

fun day15part1(inputFile: String) {
    val world = File(inputFile).readLines().map { it.map { c -> c.digitToInt() } }
    val distances = dijkstra(world)
    println(distances[Pair(world.size - 1, world[0].size - 1)])
}

fun day15part2(inputFile: String) {
    val world = File(inputFile).readLines().map { it.map { c -> c.digitToInt() } }
    val newWorld = List(world.size * 5) { _ -> MutableList(world[0].size * 5) { _ -> 0 } }
    for (x in 0..4) {
        for (y in 0..4) {
            world.forEachIndexed { r, row ->
                row.forEachIndexed { c, v ->
                    var newVal = v + x + y
                    if (newVal > 9) newVal = (newVal % 10) + 1
                    newWorld[r + world.size * y][c + world[0].size * x] = newVal
                }
            }
        }
    }
    val distances = dijkstra(newWorld)
    println(distances[Pair(newWorld.size - 1, newWorld[0].size - 1)])
}
fun getAdjacentNodes(x: Int, y: Int, xMax: Int, yMax: Int): List<Pair<Int, Int>> {
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
fun getAdjacentNodesWithoutDiagonal(x: Int, y: Int, xMax: Int, yMax: Int): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    if (x > 0) {
        result.add(Pair(x - 1, y))
    }
    if (y > 0) {
        result.add(Pair(x, y - 1))
    }
    if (y < yMax - 1) {
        result.add(Pair(x, y + 1))
    }
    if (x < xMax - 1) {
        result.add(Pair(x + 1, y))
    }

    return result
}

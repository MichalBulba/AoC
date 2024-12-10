import java.io.File
import kotlin.system.measureTimeMillis

fun main() {
    val filePath = "24/input_ten.txt"

    // Read and parse the map from the input file
    val map = parseMap(filePath)

    // Measure time for Part 1 (Sum of Trailhead Scores)
    val part1Time = measureTimeMillis {
        val trailheads = findTrailheads(map)
        val scores = trailheads.map { calculateTrailheadScore(map, it) }
        val sumScores = scores.sum()
        println("Sum of Trailhead Scores: $sumScores")
    }

    println("Time for Part 1: $part1Time ms")

    // Measure time for Part 2 (Sum of Trailhead Ratings)
    val part2Time = measureTimeMillis {
        val trailheads = findTrailheads(map)
        val ratings = trailheads.map { calculateTrailheadRating(map, it) }
        val sumRatings = ratings.sum()
        println("Sum of Trailhead Ratings: $sumRatings")
    }

    println("Time for Part 2: $part2Time ms")
}

fun parseMap(filePath: String): List<List<Int>> {
    return File(filePath).readLines().map { line -> line.map { it.digitToInt() } }
}

fun findTrailheads(map: List<List<Int>>): List<Pair<Int, Int>> {
    val trailheads = mutableListOf<Pair<Int, Int>>()
    for (row in map.indices) {
        for (col in map[row].indices) {
            if (map[row][col] == 0) {
                trailheads.add(Pair(row, col))
            }
        }
    }
    return trailheads
}

fun calculateTrailheadScore(map: List<List<Int>>, start: Pair<Int, Int>): Int {
    val visited = mutableSetOf<Pair<Int, Int>>()
    val queue = mutableListOf(start)
    var score = 0

    while (queue.isNotEmpty()) {
        val (row, col) = queue.removeFirst()
        if (!visited.add(row to col)) continue

        if (map[row][col] == 9) score++

        listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1).forEach { (dr, dc) ->
            val newRow = row + dr
            val newCol = col + dc
            if (isValidStep(map, row, col, newRow, newCol)) {
                queue.add(newRow to newCol)
            }
        }
    }

    return score
}

fun calculateTrailheadRating(map: List<List<Int>>, start: Pair<Int, Int>): Int {
    val paths = mutableSetOf<List<Pair<Int, Int>>>()

    fun dfs(path: List<Pair<Int, Int>>) {
        val (row, col) = path.last()
        if (map[row][col] == 9) {
            paths.add(path)
            return
        }
        listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1).forEach { (dr, dc) ->
            val newRow = row + dr
            val newCol = col + dc
            if (isValidStep(map, row, col, newRow, newCol) && (newRow to newCol !in path)) {
                dfs(path + (newRow to newCol))
            }
        }
    }

    dfs(listOf(start))
    return paths.size
}

fun isValidStep(map: List<List<Int>>, row: Int, col: Int, newRow: Int, newCol: Int): Boolean {
    if (newRow !in map.indices || newCol !in map[0].indices) return false
    return map[newRow][newCol] == map[row][col] + 1
}

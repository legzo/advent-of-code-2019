import java.io.File

fun main() {
    // solveDay08Part1()
    solveDay08Part2()
}

private fun solveDay08Part1() {
    val input = getContentFromFile("day_08/layers")
    println(input.toLayerChecksum(25, 6))
}

private fun solveDay08Part2() {
    val input = getContentFromFile("day_08/layers")
    println(
        input
            .toLayers(25, 6)
            .mergeAll()
            .toStringRepresentation()
    )
}

private fun getContentFromFile(filename: String) =
    File("src/main/resources/$filename").readText().trim()

private fun getLinesFromFile(filename: String) =
    File("src/main/resources/$filename").readLines()

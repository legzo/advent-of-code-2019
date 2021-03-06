import java.io.File

fun main() {
    //solveDay06()
    //solveDay08()
    solveDay12()
}

private fun solveDay06() {
    val orbits = getLinesFromFile("day_06/orbits").toOrbits()
    println(orbits.countAll())
    println(orbits.minOrbitalTransfersBetweenYOUAndSAN())
}

private fun solveDay08() {
    val input = getContentFromFile("day_08/layers")
    println(input.toLayerChecksum(25, 6))
    println(
        input
            .toLayers(25, 6)
            .mergeAll()
            .toStringRepresentation()
    )
}

private fun solveDay12() {
    println(
        AstralSystem
            .of(getContentFromFile("day_12/astral_system"))
            .step(1000)
            .energy
    )
}

private fun getContentFromFile(filename: String) =
    File("src/main/resources/$filename").readText().trim()

private fun getLinesFromFile(filename: String) =
    File("src/main/resources/$filename").readLines()

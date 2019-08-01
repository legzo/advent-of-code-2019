import java.io.File

fun main() {

    println(getContentFromFile("day_08/layers").toLayerChecksum(25, 6))

}

private fun getContentFromFile(filename: String) =
    File("src/main/resources/$filename").readText().trim()

private fun getLinesFromFile(filename: String) =
    File("src/main/resources/$filename").readLines()

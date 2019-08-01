fun String.toLayers(width: Int, height: Int) = this.chunked(width * height)

fun List<String>.findInterestingLayer() = Layer(minBy { it.count { char -> char == '0' } }!!)

data class Layer(private val stringRepresentation: String) {
    val checksum: Int = numberOf('1') * numberOf('2')
    private fun numberOf(char: Char) = stringRepresentation.count { it == char }
}

fun String.toLayerChecksum(width: Int, height: Int) =
    toLayers(width, height)
        .findInterestingLayer()
        .checksum


import io.kotlintest.matchers.collections.shouldContain
import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day06Test {

    private val input = """
        COM)B
        B)C
        C)D
        D)E
        E)F
        B)G
        G)H
        D)I
        E)J
        J)K
        K)L
    """.trimIndent()
        .split("\n")

    @Test
    fun `should parse orbits`() {
        val orbits = input.toOrbits()
        orbits shouldContain ("B" to "COM")
        orbits shouldContain ("L" to "K")
    }

    @Test
    fun `should count orbits`() {
        val orbits = input.toOrbits()
        orbits.countAll() shouldBe 42
    }
}


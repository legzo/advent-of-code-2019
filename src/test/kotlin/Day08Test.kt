import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day08Test {

    @Test
    fun `should parse layers`() {
        "123456789012".toLayers(3, 2) shouldBe listOf("123456", "789012")
    }

    @Test
    fun `should find interesting layer`() {
        listOf("123456", "789012").findInterestingLayer() shouldBe Layer("123456")
    }

    @Test
    fun `should calculate checksum`() {
        Layer("123456").checksum shouldBe 1
    }
}





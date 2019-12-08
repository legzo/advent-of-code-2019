import io.kotlintest.shouldBe
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
    fun `should validate password`() {
        "111111".isAValidPassword() shouldBe true
        "223450".isAValidPassword() shouldBe false
        "123789".isAValidPassword() shouldBe false
    }

    @Test
    fun `should find valid passwords in range`() {
        "254032-789860".countValidPasswordsInRange() shouldBe 1033
    }
}



import org.junit.Assert.*
import org.junit.Test
import java.math.BigDecimal

class Test {
    @Test fun testSolution() {
        val expected = try {
            Smartphone().init {
                name = "iPhone X"
                brande = "Apple"
                screenDiagonal = BigDecimal("5.8")
                martixType = Smartphone.MartixType.OLED
                price = BigDecimal("30499.0")
            }
        } catch (e: Exception) {
            fail("Please implement Smartphone.init function")
        }
        assertNotNull("Please implement Smartphone.init function", expected)
        assertEquals("Please implement iPhoneX function", expected, iPhoneX())
    }
}
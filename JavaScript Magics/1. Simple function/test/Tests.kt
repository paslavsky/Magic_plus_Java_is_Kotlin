import org.junit.Assert.*
import org.junit.Test

class Test {
    @Test fun testSolution() {
        assertNotNull(
                """Your function should return "Hello World"""",
                """^\s*[Hh][Ee][Ll]{2}[Oo]\s+[Ww][Oo][Rr][Ll][Dd]\s*[.!]?\s*${'$'}""".toRegex().matchEntire(sayHello())
        )
    }
}
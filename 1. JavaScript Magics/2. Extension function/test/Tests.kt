import org.junit.Assert
import org.junit.Test

class Test {
    @Test fun testSolution() {
        val kitty = Cat()
        kitty.name = "Kitty"
        Assert.assertEquals("Your extension function should return \"{cat_name} say meow\"", kitty.meow(), "Kitty say meow")
    }
}
import org.junit.Assert.*
import org.junit.Test

class Test {
    @Test fun testSolution() {
        assertEquals(
                Log4JConfig(
                        status = LogLevel.WARN,
                        appenders = arrayOf(
                                Console(
                                        layout = PatternLayout("%d{HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n")
                                )
                        ),
                        loggers = arrayOf(
                                Root(
                                        level = LogLevel.ERROR,
                                        appenderRef = "Console"
                                )
                        )
                ),
                log4JConfig
        )
    }
}
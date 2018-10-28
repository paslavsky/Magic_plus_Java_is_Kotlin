import java.util.*

fun config(
        status: LogLevel = LogLevel.INFO,
        appenders: Array<Appender> = emptyArray(),
        loggers: Array<Logger> = emptyArray()
) = Log4JConfig(status, appenders, loggers)

enum class LogLevel { TRACE, DEBUG, INFO, WARN, ERROR, FATAL }

enum class ConsoleOutput { OUT, ERR }

interface Appender

data class Console(
        val name: String = "Console",
        val target: ConsoleOutput = ConsoleOutput.OUT,
        val layout: Layout
) : Appender

interface Layout

data class PatternLayout(
        val pattern: String
) : Layout

interface Logger

data class Root(
        val level: LogLevel = LogLevel.INFO,
        val appenderRef: String? = null
) : Logger

data class Log4JConfig(
        val status: LogLevel,
        val appenders: Array<Appender>,
        val loggers: Array<Logger>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Log4JConfig

        if (status != other.status) return false
        if (!Arrays.equals(appenders, other.appenders)) return false
        if (!Arrays.equals(loggers, other.loggers)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + Arrays.hashCode(appenders)
        result = 31 * result + Arrays.hashCode(loggers)
        return result
    }
}
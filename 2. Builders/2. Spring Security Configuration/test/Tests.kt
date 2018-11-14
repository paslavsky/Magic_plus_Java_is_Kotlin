import org.junit.Assert.*
import org.junit.Test
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.provisioning.InMemoryUserDetailsManager

class Test {
    @Test fun testSolution() {
        val service = userDetailsService()
        assert(service is InMemoryUserDetailsManager) {
            "Please create InMemoryUserDetailsManager. You can try inMemory() method"
        }
        val userDetails = service.loadUserByUsername("user")
        assertNotNull("Please create user with name \"user\" :)", userDetails)

        @Suppress("UNUSED_VARIABLE")
        val configureHttpSecurity: (HttpSecurity) -> Unit = ::configure
    }
}
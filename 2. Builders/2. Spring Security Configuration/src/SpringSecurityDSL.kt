import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import kotlin.properties.Delegates

@DslMarker
annotation class SpringSecurityDSL

@SpringSecurityDSL
class UserDetailsServiceConfig {
    var userDetailsService: UserDetailsService by Delegates.notNull()
}

fun configureUserDetailsService(body: UserDetailsServiceConfig.() -> Unit): UserDetailsService {
    val config = UserDetailsServiceConfig()
    config.body()
    return config.userDetailsService
}

fun UserDetailsServiceConfig.inMemory(body: InMemoryUserDetailsManagerConfig.() -> Unit) {
    val users = mutableListOf<UserDetails>()
    InMemoryUserDetailsManagerConfig(users).body()
    this.userDetailsService = InMemoryUserDetailsManager(users)
}

@SpringSecurityDSL
class InMemoryUserDetailsManagerConfig(private val users: MutableList<UserDetails>) {
    fun createUser(body: UserBuilder.() -> Unit) {
        users.add(User.builder().also { UserBuilder(it).body() }.build())
    }
}

@SpringSecurityDSL
class UserBuilder(private val target: User.UserBuilder) {
    var username: String
        get() = throw UnsupportedOperationException()
        set(value) {
            target.username(value)
        }

    var password: String
        get() = throw UnsupportedOperationException()
        set(value) {
            target.password(value)
        }

    var role: String
        get() = throw UnsupportedOperationException()
        set(value) {
            target.roles(value)
        }

    fun useDefaultPasswordEncoder() {
        target.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
    }

    fun passwordEncoder(encoder: (String) -> String) {
        target.passwordEncoder(encoder)
    }

    fun roles(vararg roleNames: String) {
        target.roles(*roleNames)
    }
}

inline fun HttpSecurity.dsl(body: HttpSecurityDSL.() -> Unit) {
    HttpSecurityDSL(this).body()
}

@SpringSecurityDSL
class HttpSecurityDSL(private val target: HttpSecurity) {
    fun authorizeRequests(body: RequestAuthorizationDSL.() -> Unit) {
        RequestAuthorizationDSL(target.authorizeRequests()).body()
    }

    fun formLogin(body: FormLoginConfigurer<HttpSecurity>.() -> Unit = {}) {
        target.formLogin().body()
    }

    fun httpBasic(body: HttpBasicConfigurer<HttpSecurity>.() -> Unit = {}) {
        target.httpBasic().body()
    }
}

@SpringSecurityDSL
class RequestAuthorizationDSL(
        private val target: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
) {
    fun anyRequest(body: AuthorizedUrlDSL.() -> AuthUrlConfig) {
        AuthorizedUrlDSL(target.anyRequest()).body().action()
    }

    fun antMatchers(vararg antPatterns: String, body: AuthorizedUrlDSL.() -> AuthUrlConfig) {
        AuthorizedUrlDSL(target.antMatchers(*antPatterns)).body().action()
    }

    fun mvcMatchers(vararg mvcPatterns: String, body: AuthorizedUrlDSL.() -> AuthUrlConfig) {
        AuthorizedUrlDSL(target.mvcMatchers(*mvcPatterns)).body().action()
    }

    fun regexMatchers(vararg regexPatterns: String, body: AuthorizedUrlDSL.() -> AuthUrlConfig) {
        AuthorizedUrlDSL(target.regexMatchers(*regexPatterns)).body().action()
    }
}

class AuthorizedUrlDSL(
        private val target: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.AuthorizedUrl
) {
    operator fun AuthUrlConfig.not() = AuthUrlConfig {
        target.not()
        this.action()
    }

    fun authenticated() = AuthUrlConfig {
        target.authenticated()
    }

    fun hasRole(role: String) = AuthUrlConfig {
        target.hasRole(role)
    }

    fun hasAnyRole(vararg roles: String) = AuthUrlConfig {
        target.hasAnyRole(*roles)
    }

    fun hasAuthority(authority: String) = AuthUrlConfig {
        target.hasAuthority(authority)
    }

    fun hasAnyAuthority(vararg authorities: String) = AuthUrlConfig {
        target.hasAnyAuthority(*authorities)
    }

    fun hasIpAddress(ip: String) = AuthUrlConfig {
        target.hasIpAddress(ip)
    }

    fun permitAll() = AuthUrlConfig {
        target.permitAll()
    }

    fun anonymous() = AuthUrlConfig {
        target.anonymous()
    }

    fun rememberMe() = AuthUrlConfig {
        target.rememberMe()
    }

    fun denyAll() = AuthUrlConfig {
        target.denyAll()
    }

    fun fullyAuthenticated() = AuthUrlConfig {
        target.fullyAuthenticated()
    }
}

class AuthUrlConfig internal constructor(internal val action: () -> Unit)

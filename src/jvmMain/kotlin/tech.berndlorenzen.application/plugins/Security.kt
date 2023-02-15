package tech.berndlorenzen.application.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.berndlorenzen.application.models.UserSession
import tech.berndlorenzen.application.repository.UserRepository
import java.io.File
import kotlin.collections.set

fun Application.configureSecurity() {
    val repository by closestDI().instance<UserRepository>()
    val isProduction: Boolean = System.getenv("PRODUCTION")?.toBoolean() ?: false
    install(Sessions) {
        if (isProduction) {
            cookie<UserSession>("user-session", directorySessionStorage(File("build/.sessions"))) {
                cookie.extensions["SameSite"] = "lax"
                //cookie.secure = true
            }
        } else {
            cookie<UserSession>("user-session", SessionStorageMemory()) {
                cookie.extensions["SameSite"] = "lax"
            }
        }

    }


    install(Authentication) {
        form("auth-form") {
            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                val user = repository.checkCredentials(credentials.name, credentials.password.hashCode().toString())
                if (user != null) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }


            }
        }
        session<UserSession>("auth-session") {
            validate { session ->
                if (session.userId.isNotEmpty()) {
                    session
                } else {
                    null
                }
            }
            challenge {
                call.respondRedirect("/anmelden")
            }
        }
    }

    routing {
        route("/api/auth") {
            authenticate("auth-form") {
                post("/login") {
                    call.principal<UserIdPrincipal>()?.name.toString()
                    call.sessions.set(UserSession(generateSessionId()))
                    call.respondRedirect("/SHS")
                }
            }
            get("/logout") {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/anmelden", false)
            }
        }


    }
}
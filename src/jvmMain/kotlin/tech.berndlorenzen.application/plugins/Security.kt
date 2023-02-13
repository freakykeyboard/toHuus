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

fun Application.configureSecurity() {
    val repository by closestDI().instance<UserRepository>()

    install(Sessions) {
        cookie<UserSession>("user-session") {
            cookie.extensions["SameSite"] = "lax"
        }
    }

    install(Authentication){
        form("auth-form"){

            userParamName = "username"
            passwordParamName = "password"
            validate { credentials ->
                val user=repository.checkCredentials(credentials.name,credentials.password.hashCode().toString())
                if (user!=null) {
                    user.userId?.let { UserIdPrincipal(it) }
                }else {
                    null
                }


            }
        }
        session<UserSession>("auth-session"){
            validate { session ->
                if (session.id.isNotEmpty()){
                    session
                }else {
                    null
                }
            }
            challenge{
                call.respondRedirect("/anmelden")
            }
        }
    }

    routing {
        route("/api/auth"){
            authenticate("auth-form") {
                post("/login") {
                    val userid = call.principal<UserIdPrincipal>()?.name.toString()
                    call.sessions.set(UserSession(id = userid))
                    call.respondRedirect("/")
                }
            }
            get("/logout") {
                call.sessions.clear<UserSession>()
                call.respondRedirect("/login")
            }
        }


    }
}
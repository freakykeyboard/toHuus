package tech.berndlorenzen.application.routes
import User
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.util.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.berndlorenzen.application.models.UserSession
import tech.berndlorenzen.application.repository.UserRepository

fun Route.userRouting() {
    val repository: UserRepository by closestDI().instance()
    route(User.path) {

        post {
            val formParameter = call.receiveParameters()
            val username = formParameter.getOrFail("username")
            val password = formParameter.getOrFail("password")
            val newUser = repository.add(username, password)
            // authenticate user and set session

            if (newUser != null) {
                newUser.password = ""
                call.sessions.set(UserSession(id = newUser.userId!!))
                call.respond(HttpStatusCode.OK, newUser.userId!!)
            }

        }
        get("/{id}") {
            val id = call.parameters["id"]!!
            val user = repository.read(id)
            if (user != null) {
                user.password = ""
                call.respond(user)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        put {
            val user = call.receive<User>()
            repository.update(user)
            call.respond(HttpStatusCode.OK)
        }
        delete("/{id}") {
            val id = call.parameters["id"]!!
            repository.delete(id)
            call.respond(HttpStatusCode.OK)
        }
    }
}
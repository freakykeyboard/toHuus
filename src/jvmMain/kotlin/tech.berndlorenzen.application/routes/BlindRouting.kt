package tech.berndlorenzen.application.routes

import Blind
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.berndlorenzen.application.models.UserSession
import tech.berndlorenzen.application.repository.BlindRepository

fun Route.blindRouting() {
    val repository: BlindRepository by closestDI().instance()
    authenticate("auth-session") {
        route(Blind.path) {
            get {
                val userId = call.principal<UserSession>()!!.userId
                call.respond(repository.all(userId))
            }
            get("/{id}") {
                val id = call.parameters["id"]!!
                call.respond(repository.read(id)!!)
            }
            post {
                val userId = call.principal<UserSession>()!!.userId
                val blind = call.receive<Blind>()
                call.respond(repository.create(blind.name, userId))
            }
            put("/{id}") {
                val blind = call.receive<Blind>()
                repository.update(blind)
                call.respond(HttpStatusCode(200, "OK"))
            }
            delete("/{id}") {
                val id = call.parameters["id"]!!
                repository.delete(id)
                call.respond(HttpStatusCode(200, "OK"))
            }
        }
    }


}
package tech.berndlorenzen.application.routes

import Wallbox
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.berndlorenzen.application.models.UserSession
import tech.berndlorenzen.application.repository.WallboxRepository

fun Route.wallBoxRouting() {
    val repository: WallboxRepository by closestDI().instance()
    authenticate("auth-session") {
        route(Wallbox.path) {
            get {
                val userSession = call.principal<UserSession>()
                val wallBoxList = repository.all(userSession!!.userId)
                call.respond(wallBoxList)
            }
            get("/{id}") {
                val wallBox = repository.read(call.parameters["id"]!!)
                call.respond(wallBox!!)
            }
            post {
                val userSession = call.principal<UserSession>()
                val wallbox = call.receive<Wallbox>()
                val newWallbox = repository.newWallbox(wallbox.name, userSession!!.userId)
                call.respond(newWallbox)
            }
            put("/{id}") {
                val wallbox = call.receive<Wallbox>()
                repository.update(wallbox)
                call.respond(HttpStatusCode(200, "OK"))
            }
            delete("/{id}") {
                repository.delete(call.parameters["id"]!!)
                call.respond(HttpStatusCode(200, "OK"))
            }

        }
    }
}
package tech.berndlorenzen.application.routes

import Lamp
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI
import tech.berndlorenzen.application.models.UserSession
import tech.berndlorenzen.application.repository.LampRepository

fun Route.lampRouting() {
    val repository: LampRepository by closestDI().instance()
    authenticate("auth-session") {
        route(Lamp.path) {

            get {
                val userSession = call.principal<UserSession>()
                repository.all(userSession!!.userId)
            }
            get("/{id}") {
                repository.read(call.parameters["id"]!!)
            }
            post {
                val userSession = call.principal<UserSession>()
                val lamp = call.receive<Lamp>()
                repository.newLamp(lamp.name, userSession!!.userId)
                call.respond(HttpStatusCode(201, "Created"))

            }

            put("/state/{id}") {
                val lamp = call.receive<Lamp>()
                repository.switch(lamp)
            }
            put("/name/{id}") {
                val lamp = call.receive<Lamp>()
                repository.rename(lamp)
            }

            delete("/{id}") {
                repository.delete(call.parameters["id"]!!)
            }
        }
    }

}
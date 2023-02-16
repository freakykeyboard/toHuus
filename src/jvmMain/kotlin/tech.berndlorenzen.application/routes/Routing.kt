package tech.berndlorenzen.application.routes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        indexRouting()
        userRouting()
        lampRouting()


    }
}
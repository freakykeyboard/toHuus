package tech.berndlorenzen.application

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import tech.berndlorenzen.application.plugins.configureSerialization
import tech.berndlorenzen.application.plugins.configureStatusPages
import tech.berndlorenzen.application.routes.configureRouting


fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "127.0.0.1",
        watchPaths = listOf("classes","resources","js","developmentEcecutable"),
        module = Application::module
    ).start(wait = true)
}
fun Application.module() {
    configureSerialization()
    configureRouting()
    configureStatusPages()
}
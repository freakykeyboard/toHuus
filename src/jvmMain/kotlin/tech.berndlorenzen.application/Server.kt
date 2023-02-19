package tech.berndlorenzen.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di
import tech.berndlorenzen.application.plugins.*
import tech.berndlorenzen.application.repository.*
import tech.berndlorenzen.application.routes.configureRouting


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    di {
        bindSingleton<UserRepository> { UserMongoDBRepository() }
        bindSingleton<LampRepository> { LampMongoDBRepository() }
        bindSingleton<WallboxRepository> { WallboxMongoDbRepository() }
    }
    configureSecurity()
    configureRouting()
    configureMonitoring()
    configureSerialization()
    configureStatusPages()

}
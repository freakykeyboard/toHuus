package tech.berndlorenzen.application

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.kodein.di.bindSingleton
import org.kodein.di.ktor.di
import tech.berndlorenzen.application.plugins.*
import tech.berndlorenzen.application.repository.LampMongoDBRepository
import tech.berndlorenzen.application.repository.LampRepository
import tech.berndlorenzen.application.repository.UserMongoDBRepository
import tech.berndlorenzen.application.repository.UserRepository
import tech.berndlorenzen.application.routes.configureRouting


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    di {
        bindSingleton<UserRepository> { UserMongoDBRepository() }
        bindSingleton<LampRepository> { LampMongoDBRepository() }
    }
    configureSecurity()
    configureRouting()
    configureMonitoring()
    configureSerialization()
    configureStatusPages()

}
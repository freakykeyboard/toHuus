package tech.berndlorenzen.application.models

import io.ktor.server.auth.*

data class UserSession(val userId:String):Principal
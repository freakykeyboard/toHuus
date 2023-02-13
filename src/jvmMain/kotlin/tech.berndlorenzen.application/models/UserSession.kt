package tech.berndlorenzen.application.models

import io.ktor.server.auth.*

data class UserSession(val id:String):Principal
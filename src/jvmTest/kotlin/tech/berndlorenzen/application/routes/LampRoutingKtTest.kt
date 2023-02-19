package tech.berndlorenzen.application.routes

import Lamp
import User
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class LampApiTest {
    private val lamp = Lamp("", "test", false)

    @Test
    fun testUnauthorized() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post(Lamp.path) {
            contentType(ContentType.Application.Json)
            setBody(Lamp("", "test", false))
        }
        assertEquals(HttpStatusCode.Unauthorized, response.status)
    }

    @Test
    fun testAddLamp() = testApplication {
        val client = createClient {
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
            install(ContentNegotiation) {
                json()
            }
        }

        client.submitForm(
            url = User.path,
            formParameters = Parameters.build {
                append("username", "testUser")
                append("password", "testPassword")
            })

        val response = client.post(Lamp.path) {
            contentType(ContentType.Application.Json)
            setBody(lamp)
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

}
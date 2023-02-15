import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*


val jsonClient = HttpClient(Js) {
    install(ContentNegotiation) {
        json()
    }
}

suspend fun login(user: User): HttpResponse {
    val response: HttpResponse = jsonClient.submitForm(
        url = "/api/auth/login",
        formParameters = Parameters.build {
            append("username", user.username)
            append("password", user.password)
        }
    )
    return response

}
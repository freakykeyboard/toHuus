import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserApiTest {

    @Test
    fun testCreateUserOK() = testApplication {

            val response: HttpResponse =
                client.submitForm(
                    url = User.path,
                    formParameters = Parameters.build {
                        append("username", "testUser")
                        append("password", "testPassword")
                    }
                )
            assertEquals(HttpStatusCode.OK, response.status)

    }
    @Test
    fun testGetUserNotFound() = testApplication {
        val response: HttpResponse = client.get(User.path + "/1")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }
    @Test
    fun testGetUserFound() = testApplication {
        val client=createClient {
            install(ContentNegotiation) {
                json()
            }
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
        }
        val createResponse: HttpResponse=
            client.submitForm(
                url = User.path,
                formParameters = Parameters.build {
                    append("username", "testUser")
                    append("password", "testPassword")
                }
            )
        val sessionId=createResponse.bodyAsText()
        val response: User = client.get(User.path +"/"+ sessionId) {
            header("Cookie", "SESSIONID=$sessionId")
        }.body()
        assertEquals("testUser", response.username)
        assertEquals("",response.password)
    }
    @Test
    fun testUpdateUserSuccess()= testApplication{
        val client=createClient {
            install(ContentNegotiation) {
                json()
            }
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
        }
        val createResponse: HttpResponse=
            client.submitForm(
                url = User.path,
                formParameters = Parameters.build {
                    append("username", "updateUser")
                    append("password", "updateUserPassword")
                }
            )
        val sessionId=createResponse.bodyAsText()
        val createdUser: User = client.get(User.path +"/"+ sessionId) {
            header("Cookie", "SESSIONID=$sessionId")
        }.body()
        createdUser.username="updatedUser"
        client.put(User.path){
            contentType(ContentType.Application.Json)
            setBody(createdUser)
        }
        val updatedUser: User = client.get(User.path +"/"+ sessionId) {
            header("Cookie", "SESSIONID=$sessionId")
        }.body()
        assertEquals("updatedUser",updatedUser.username)


    }
    @Test
    fun testUserDeleteOk()= testApplication{

        val client=createClient {
            install(ContentNegotiation) {
                json()
            }
            install(HttpCookies) {
                storage = AcceptAllCookiesStorage()
            }
        }
        val createResponse: HttpResponse=
            client.submitForm(
                url = User.path,
                formParameters = Parameters.build {
                    append("username", "deleteUser")
                    append("password", "deleteUserPassword")
                }
            )
        val sessionId=createResponse.bodyAsText()

        val response=client.delete(User.path +"/"+ sessionId)
        val getResponse = client.get(User.path +"/"+ sessionId) {
            header("Cookie", "SESSIONID=$sessionId")
        }
        assertEquals(HttpStatusCode.NotFound,getResponse.status)
        assertEquals(HttpStatusCode.OK,response.status)
    }
}
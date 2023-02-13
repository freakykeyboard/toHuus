import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals

class StatusTest {
    @Test
    fun testStatusOk() {
        val client = HttpClient(CIO)
        // if the route is not found and does not contain "api" in the path, the index.html should be returned
        runBlocking {
            val response:HttpResponse=client.request("http://localhost:8080/unknownRoute")
            assertEquals(HttpStatusCode.OK,response.status)
        }
        }
    @Test
    fun testStatusNotFound() {
        val client = HttpClient(CIO)
        // if the route is not found and does  contain "api" in the path a 404 should be returned
        runBlocking {
            val response:HttpResponse=client.request("http://localhost:8080/api/unknownRoute")
            assertEquals(HttpStatusCode.NotFound,response.status)
        }
    }

}
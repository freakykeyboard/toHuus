import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals("Hello, world!", response.bodyAsText())
    }
}

class StatusTest {
    @Test
    fun testStatusOk() = testApplication {
        val response: HttpResponse = client.request("unknownRoute")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(this::class.java.classLoader.getResource("index.html")!!.readText(), response.bodyAsText())

    }

    @Test
    fun testStatusNotFound() = testApplication {

        // if the route is not found and does  contain "api" in the path a 404 should be returned
        val response: HttpResponse = client.request("api/unknownRoute")
        assertEquals(HttpStatusCode.NotFound, response.status)

    }

}
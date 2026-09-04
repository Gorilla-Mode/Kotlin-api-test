package com.example

import io.ktor.client.request.get
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import kotlin.test.*

class ServerTest {

    @Test
    fun `test root endpoint`() = testApplication {
        // loads default configuration
        configure()
        // verify server root returns 200
        assertEquals(HttpStatusCode.OK, client.get("/").status)
    }

    @Test
    fun `post adds an obstacle`() = testApplication {
        configure()
        val obstacleCount = obstacles.size

        val response = client.post("/") {
            contentType(ContentType.Application.Json)
            setBody("""{"id":3,"name":"Obstacle 3","geometry":"POINT(2 3)"}""")
        }

        assertEquals(HttpStatusCode.Created, response.status)
        assertEquals(obstacleCount + 1, obstacles.size)
        assertEquals("Obstacle 3", obstacles.last().name)
        assertTrue(response.bodyAsText().contains("Obstacle 3"))
    }

    @Test
    fun `get returns an obstacle by id`() = testApplication {
        configure()

        val response = client.get("/1")

        assertEquals(HttpStatusCode.OK, response.status)
        assertTrue(response.bodyAsText().contains("Obstacle 1"))
        assertTrue(response.bodyAsText().contains("POINT(1 1)"))
    }

    @Test
    fun `get returns not found for an unknown id`() = testApplication {
        configure()

        assertEquals(HttpStatusCode.NotFound, client.get("/999999").status)
    }

    @Test
    fun `delete removes an obstacle by id`() = testApplication {
        configure()
        val id = 9001
        obstacles.add(Obstacle(id, "Temporary obstacle", "POINT(9 9)"))

        val deleteResponse = client.delete("/$id")

        assertEquals(HttpStatusCode.NoContent, deleteResponse.status)
        assertEquals(HttpStatusCode.NotFound, client.get("/$id").status)
    }

    @Test
    fun `delete returns not found for an unknown id`() = testApplication {
        configure()

        assertEquals(HttpStatusCode.NotFound, client.delete("/999999").status)
    }

}

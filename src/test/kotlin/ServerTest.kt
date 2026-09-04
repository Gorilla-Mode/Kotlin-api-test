package com.example

import io.ktor.client.request.get
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

}

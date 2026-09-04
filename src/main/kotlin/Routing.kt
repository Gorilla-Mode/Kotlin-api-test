package com.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val obstacles = mutableListOf(
    Obstacle(1, "Obstacle 1", "POINT(1 1)"),
    Obstacle(2, "Obstacle 2", "POINT(6 7)")
)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, obstacles)
        }
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val obstacle = id?.let { requestedId -> obstacles.find { it.id == requestedId } }

            if (obstacle == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(HttpStatusCode.OK, obstacle)
            }
        }
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            val deleted = id != null && obstacles.removeIf { it.id == id }

            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
        post("/") {
            val obstacle = call.receive<Obstacle>()
            obstacles.add(obstacle)
            call.respond(HttpStatusCode.Created, obstacle)
        }
    }
}

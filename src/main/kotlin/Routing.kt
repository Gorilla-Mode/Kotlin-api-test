package com.example

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

val obstacles = listOf(
    Obstacle(1, "Obstacle 1", "POINT(1 1)"),
    Obstacle(2, "Obstacle 2", "POINT(6 7)")
)

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(HttpStatusCode.OK, obstacles)
        }
    }
}
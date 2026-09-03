package com.example

import kotlinx.serialization.Serializable

@Serializable
data class Obstacle(
    val id: Int,
    val name: String,
    val geometry: String
)
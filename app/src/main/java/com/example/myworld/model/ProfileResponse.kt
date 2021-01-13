package com.example.myworld.model

data class ProfileResponse(
    val date_joined: String,
    val email: String,
    val firstname: String,
    val id: Int,
    val last_login: String,
    val lastname: String,
    val password: String,
    val username: String
)
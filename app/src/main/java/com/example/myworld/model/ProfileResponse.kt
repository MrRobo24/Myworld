package com.example.myworld.model

data class ProfileResponse(
    val id: Int,
    val username: String,
    val email: String,
    val firstname: String,
    val lastname: String,
    val password: String,
    val last_login: String,
    val date_joined: String
)
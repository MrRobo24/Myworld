package com.example.myworld.model.authresponse

data class LoginResponse(
    val email: String,
    val token: String,
    val user_id: Int
)
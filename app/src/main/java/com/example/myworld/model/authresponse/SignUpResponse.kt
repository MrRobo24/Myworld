package com.example.myworld.model.authresponse

data class SignUpResponse(
    val token: String,
    val user: User
)
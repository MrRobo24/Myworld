package com.example.myworld.model.authmodels

data class SignInResponseBody(val email: String, val token: String, val userId: Int)
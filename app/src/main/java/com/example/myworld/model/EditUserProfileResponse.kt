package com.example.myworld.model

data class EditUserProfileResponse(
    val birth_date: String,
    val gender: String,
    val id: Int,
    val profile_picture: String,
    val user: Int
)
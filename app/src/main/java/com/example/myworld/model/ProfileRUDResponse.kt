package com.example.myworld.model

data class ProfileRUDResponse(
    val id: Int,
    val gender: String,
    val birth_date: String,
    val profile_picture: String
)
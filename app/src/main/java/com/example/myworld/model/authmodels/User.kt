package com.example.myworld.model.authmodels

data class User(
    var id: Int,
    var email: String,
    var username: String = "",
    var profile_picture: String = "",
    var info: String = "",
    var gender: String = "",
    var birth_date: String = ""
)
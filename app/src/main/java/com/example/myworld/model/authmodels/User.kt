package com.example.myworld.model.authmodels

data class User(
    var userId: Int,
    var email: String,
    var username: String,
    var profileImgUrl: String = "",
    var info: String = "",
    var gender: String = ""
)
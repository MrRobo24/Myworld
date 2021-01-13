package com.example.myworld.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_table")
data class AuthEntity(

    @PrimaryKey val user_id: Int,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "profile_picture") var profile_picture: String = "",
    @ColumnInfo(name = "info") val info: String = "",
    @ColumnInfo(name = "gender") var gender: String = "",
    @ColumnInfo(name = "birth_date") var birth_date: String = "",
    @ColumnInfo(name = "token") val token: String = ""
)
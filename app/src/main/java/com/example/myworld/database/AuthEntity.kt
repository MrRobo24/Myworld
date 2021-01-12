package com.example.myworld.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_table")
data class AuthEntity(

    @PrimaryKey val user_id: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "profile_image_url") val profileImageUrl: String = "",
    @ColumnInfo(name = "info") val info: String = "",
    @ColumnInfo(name = "gender") val gender: String = "",
    @ColumnInfo(name = "token") val token: String
)
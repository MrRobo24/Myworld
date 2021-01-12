package com.example.myworld.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_table")
data class AuthEntity(
    @PrimaryKey val userId: Int,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "token") val token: String
)
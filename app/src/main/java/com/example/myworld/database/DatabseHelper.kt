package com.example.myworld.database

import com.example.myworld.model.authmodels.SignInResponseBody

interface DatabaseHelper {
    suspend fun insertAuth(authEntity: AuthEntity)
    suspend fun getAuthById(userId: Int): AuthEntity?
    suspend fun getAllAuth(): List<AuthEntity>

}
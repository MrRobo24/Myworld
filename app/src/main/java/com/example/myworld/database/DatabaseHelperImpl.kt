package com.example.myworld.database

import com.example.myworld.model.authmodels.SignInResponseBody

class DatabaseHelperImpl(private val myWorldDB: MyWorldDB) : DatabaseHelper {
    override suspend fun insertAuth(authEntity: AuthEntity) =
        myWorldDB.authDao().insertDao(authEntity)

    override suspend fun getAuthById(userId: Int): AuthEntity? =
        myWorldDB.authDao().getAuthById(userId)

    override suspend fun getAllAuth(): List<AuthEntity> = myWorldDB.authDao().getAllAuth()
}
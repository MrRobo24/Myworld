package com.example.myworld.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthDao {
    @Insert
    suspend fun insertDao(authEntity: AuthEntity)

    @Delete
    suspend fun deleteDao(authEntity: AuthEntity?)

    @Query("SELECT * FROM auth_table WHERE userId = :userId")
    suspend fun getAuthById(userId: Int): AuthEntity?

    @Query("SELECT * FROM auth_table")
    suspend fun getAllAuth(): List<AuthEntity>
}
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
    suspend fun deleteAuth(authEntity: AuthEntity?)

    @Query("DELETE FROM auth_table")
    suspend fun nukeTable()

    @Query("SELECT * FROM auth_table WHERE user_id = :user_id")
    suspend fun getAuthById(user_id: Int): AuthEntity?

    @Query("SELECT * FROM auth_table")
    suspend fun getAllAuth(): List<AuthEntity>
}
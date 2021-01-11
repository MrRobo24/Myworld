package com.example.myworld.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthDao {
    @Insert
    fun insertDao(authEntity: AuthEntity?)

    @Delete
    fun deleteDao(authEntity: AuthEntity?)

    @Query("SELECT * FROM auth_table WHERE userId = :userId")
    fun getAuthById(userId: Int): AuthEntity?

    @Query("SELECT * FROM auth_table")
    fun getAllAuth(): List<AuthEntity>
}
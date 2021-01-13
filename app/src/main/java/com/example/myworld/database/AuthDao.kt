package com.example.myworld.database

import androidx.room.*

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

    @Query("UPDATE auth_table SET email = :email WHERE user_id = :user_id")
    suspend fun updateEmailById(email: String, user_id: Int)

    @Query("UPDATE auth_table SET username = :username WHERE user_id = :user_id")
    suspend fun updateUsernameById(username: String, user_id: Int)

    @Query("UPDATE auth_table SET username = :profile_picture WHERE user_id = :user_id")
    suspend fun updateProfilePictureById(profile_picture: String, user_id: Int)

    @Query("UPDATE auth_table SET gender = :gender WHERE user_id = :user_id")
    suspend fun updateGenderById(gender: String, user_id: Int)

    @Query("UPDATE auth_table SET birth_date = :birth_date WHERE user_id = :user_id")
    suspend fun updateBirthDateById(birth_date: String, user_id: Int)


}
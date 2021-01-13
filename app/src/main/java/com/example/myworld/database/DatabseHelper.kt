package com.example.myworld.database

interface DatabaseHelper {
    suspend fun insertAuth(authEntity: AuthEntity)
    suspend fun getAuthById(userId: Int): AuthEntity?
    suspend fun getAllAuth(): List<AuthEntity>
    suspend fun updateEmailById(email: String, user_id: Int)
    suspend fun updateUsernameById(username: String, user_id: Int)
    suspend fun updateProfilePictureById(profile_picture: String, user_id: Int)
    suspend fun updateGenderById(gender: String, user_id: Int)
    suspend fun updateBirthDateById(birth_date: String, user_id: Int)
}
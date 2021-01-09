package com.example.myworld.repository

import com.example.myworld.preferences.UserPreferences
import com.example.myworld.webservices.ApiService

class AuthRepository(
    private val api: ApiService,
    private val preferences: UserPreferences
) : BaseRepository() {

    suspend fun login(
        username: String,
        password: String
    ) = safeApiCall {
        api.login(username, password)
    }

    suspend fun signUp(
        username: String,
        email: String,
        password: String
    ) = safeApiCall {
        api.signUp(username, email, password)
    }

    suspend fun saveLoginUserAuthToken(token: String, user_id: Int) {
        preferences.saveLoginUserAuthToken(token, user_id)
    }

    suspend fun saveSignUpUserAuthToken(token: String, email: String, id: Int, username: String) {
        preferences.saveSignUpUserAuthToken(token, email, id, username)
    }

}
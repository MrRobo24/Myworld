package com.example.myworld.repository

import android.app.Application
import android.util.Log
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseBuilder
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance

class ProfileRepository(application: Application) {

    private val url: String = "/profile/userdetails/"
    lateinit var username: String
    var user_id: Int = 0
    private val dbHelper: DatabaseHelperImpl =
        DatabaseHelperImpl(DatabaseBuilder.getInstance(application))

    suspend fun getUser(): AuthEntity? {
        val authList: List<AuthEntity> = dbHelper.getAllAuth() //need to return this later

        Log.d("DB", "Entity fetched from DB is: $authList")
        if (authList.isEmpty() || authList.size > 1) {
            Log.d("DB", "Multiple entities exist in DB")
            return null
        }

        username = authList[0].username
        user_id = authList[0].user_id

        Log.d("DB", "Returning User ${authList[0]} to ProfileViewModel")
        return authList[0]
    }

    suspend fun callFetchProfile(): AuthEntity {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val profileResponse = retIn.fetchProfile(url + user_id.toString())
        Log.d("FetchProfile", "Profile Fetched: $profileResponse")
        val authEntity = AuthEntity(
            user_id = profileResponse.id,
            email = profileResponse.email,
            username = profileResponse.username
        )

        return authEntity
    }

    suspend fun getAuthById(user_id: Int) {
        val authEntity: AuthEntity? = dbHelper.getAuthById(user_id) //need to return this later
        Log.d("DB", "Entity fetched from DB is: ${authEntity!!.copy(user_id = user_id)}")
    }

    suspend fun updateDB(authEntity: AuthEntity): AuthEntity {

        if (authEntity.email.isNotEmpty()) {
            dbHelper.updateUsernameById(authEntity.username, authEntity.user_id)
        }

        if (authEntity.username.isNotEmpty()) {
            dbHelper.updateUsernameById(authEntity.username, authEntity.user_id)
        }

        if (authEntity.profile_picture.isNotEmpty()) {
            dbHelper.updateProfilePictureById(authEntity.profile_picture, authEntity.user_id)
        }

        if (authEntity.gender.isNotEmpty()) {
            dbHelper.updateGenderById(authEntity.gender, authEntity.user_id)
        }

        if (authEntity.birth_date.isNotEmpty()) {
            dbHelper.updateBirthDateById(authEntity.birth_date, authEntity.user_id)
        }

        return dbHelper.getAllAuth()[0]
    }

    suspend fun updateEmailById(email: String, user_id: Int) {
        dbHelper.updateEmailById(email, user_id)
    }

    suspend fun updateProfilePictureById(profile_picture: String, user_id: Int) {
        dbHelper.updateProfilePictureById(profile_picture, user_id)
    }
}
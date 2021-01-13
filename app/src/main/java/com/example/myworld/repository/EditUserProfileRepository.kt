package com.example.myworld.repository

import android.app.Application
import android.util.Log
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseBuilder
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.model.EditUserProfileResponse
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance

class EditUserProfileRepository(application: Application) {

    private val url: String = "/profile/profilerud/"
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

        // username = authList[0].username
        Log.d("DB", "Updating user_id data member to ${authList[0].user_id}")
        user_id = authList[0].user_id
        // email = authList[0].email


        Log.d("DB", "Returning User ${authList[0]} to ProfileViewModel")
        return authList[0]
    }

    suspend fun fetchUserAPI(): EditUserProfileResponse {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

        return retIn.getProfileGenderDOB(url + user_id)
    }

    suspend fun saveEditedProfile(
        gender: String,
        dateOfBirth: String
    ): EditUserProfileResponse {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)

        return retIn.saveUserProfile(url + user_id, gender, dateOfBirth)

    }

    suspend fun updateGenderById(gender: String) {
        dbHelper.updateGenderById(gender, user_id)
    }

    suspend fun updateBirthDateById(dateOfBirth: String) {
        dbHelper.updateBirthDateById(dateOfBirth, user_id)
    }
}
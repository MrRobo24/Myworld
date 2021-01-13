package com.example.myworld.repository

import android.app.Application
import android.util.Log
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseBuilder
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.model.ProfileResponse
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository(application: Application) {

    private val url: String = "/profile/userdetails/"
    lateinit var username: String
    var user_id: Int = 0
    val dbHelper: DatabaseHelperImpl =
        DatabaseHelperImpl(DatabaseBuilder.getInstance(application))

    suspend fun getUsername(): String {
//        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
//        val call = retIn.getUsername(url + 12)
//        call.enqueue(object : Callback<ProfileResponse> {
//            override fun onResponse(
//                call: Call<ProfileResponse>,
//                response: Response<ProfileResponse>
//            ) {
//                if (response.isSuccessful)
//                    Log.d("Profile Response:", "" + response.body()!!.username)
//                username = response.body()!!.username
//            }
//
//            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                Log.d("Profile Response:", "" + t.message)
//            }
//
//        })

        val authList: List<AuthEntity> = dbHelper.getAllAuth() //need to return this later

        Log.d("DB", "Entity fetched from DB is: ${authList.toString()}")
        if (authList.isEmpty() || authList.size > 1) {
            Log.d("DB", "Multiple entities exist in DB")
            return "NA"
        }

        username = authList[0].username
        user_id = authList[0].user_id
        if (username.isEmpty()) {
            Log.d("DB", "Username in DB is empty: need to call API")
            return "NA"
        }

        Log.d("DB", "Returning Username $username to ProfileViewModel")
        return username
    }

    suspend fun callFetchProfile(): String {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val profileResponse = retIn.fetchProfile(url + user_id.toString())
        Log.d("FetchProfile", "Profile Fetched: $profileResponse")
        return profileResponse.username
    }

    suspend fun getAuthById(user_id: Int) {
        val authEntity: AuthEntity? = dbHelper.getAuthById(user_id) //need to return this later
        Log.d("DB", "Entity fetched from DB is: ${authEntity!!.copy(user_id = user_id)}")
    }
}
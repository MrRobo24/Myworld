package com.example.myworld.repository

import android.app.Application
import android.util.Log
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseBuilder
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.model.authmodels.SignUpBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance
import kotlin.math.sign

class AuthRepository(application: Application) {

    private val dbHelper: DatabaseHelperImpl =
        DatabaseHelperImpl(DatabaseBuilder.getInstance(application))

    suspend fun callSignIn(username: String, password: String): SignInResponseBody? {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signInInfo = SignInBody(
            username,
            password
        )

        return retIn.signIn(signInInfo)

    }

    suspend fun callSignUp(
        regEmail: String,
        regUsername: String,
        regPass: String
    ): SignUpResponseBody? {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signUpInfo = SignUpBody(
            regEmail,
            regUsername,
            regPass
        )

        return retIn.signUp(signUpInfo)
    }

    suspend fun getAuthById(dbHelper: DatabaseHelperImpl, user_id: Int) {
        val authEntity: AuthEntity? = dbHelper.getAuthById(user_id) //need to return this later
        Log.d("DB", "Entity fetched from DB is: ${authEntity.toString()}")
    }

    suspend fun insertAuth(
        signUpResponseBody: SignUpResponseBody
    ): AuthEntity {

        val authEntity = AuthEntity(
            user_id = signUpResponseBody.user.id,
            email = signUpResponseBody.user.email,
            username = signUpResponseBody.user.username,
            profileImageUrl = signUpResponseBody.user.profileImgUrl,
            info = signUpResponseBody.user.info,
            gender = signUpResponseBody.user.gender,
            token = signUpResponseBody.token
        )

        dbHelper.insertAuth(authEntity)
        return authEntity
    }

    suspend fun insertAuth(signInResponseBody: SignInResponseBody): AuthEntity {
        val authEntity = AuthEntity(
            user_id = signInResponseBody.user_id,
            email = signInResponseBody.email,
            token = signInResponseBody.token
        )

        dbHelper.insertAuth(authEntity)

        return authEntity

    }


}
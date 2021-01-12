package com.example.myworld.repository

import android.view.View
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.model.authmodels.SignUpBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance

class AuthRepository {

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

    suspend fun insertAuth(dbHelper: DatabaseHelperImpl, authEntity: AuthEntity): AuthEntity {
        dbHelper.insertAuth(authEntity)
        return authEntity
    }

    suspend fun getAuth() {

    }


}
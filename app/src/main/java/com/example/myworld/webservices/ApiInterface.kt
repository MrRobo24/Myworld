package com.example.myworld.webservices

import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.model.authmodels.SignUpBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("api-token-auth")
    suspend fun signIn(@Body info: SignInBody): SignInResponseBody


    @Headers("Content-Type:application/json")
    @POST("api/register")
    suspend fun signUp(
        @Body info: SignUpBody
    ): SignUpResponseBody
}
package com.example.myworld.webservices

import com.example.myworld.model.authresponse.LoginResponse
import com.example.myworld.model.authresponse.SignUpResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("/api-token-auth")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/api/register")
    suspend fun signUp(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): SignUpResponse
}
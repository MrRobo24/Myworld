package com.example.myworld.webservices

import com.example.myworld.model.EditUserProfileResponse
import com.example.myworld.model.ProfileResponse
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.model.authmodels.SignUpBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import retrofit2.http.*

interface ApiInterface {

    @Headers("Content-Type:application/json")
    @POST("api-token-auth")
    suspend fun signIn(@Body info: SignInBody): SignInResponseBody


    @Headers("Content-Type:application/json")
    @POST("api/register")
    suspend fun signUp(
        @Body info: SignUpBody
    ): SignUpResponseBody

    @GET
    suspend fun fetchProfile(@Url url: String): ProfileResponse

    @FormUrlEncoded
    @PUT
    suspend fun saveUserProfile(
        @Url url: String,
        @Field("gender") gender: String,
        @Field("birth_date") birth_date: String
    ): EditUserProfileResponse

}
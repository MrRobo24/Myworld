package com.example.myworld.webservices

import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignUpBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
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

class RetrofitInstance {
    companion object {
        private const val BASE_URL: String = "http://miworld.herokuapp.com/"

        private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val client: OkHttpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
package com.example.myworld.repository

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.camera.core.impl.LiveDataObservable
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {

    var signInResponse: SignInResponseBody? = null
    var signUpResponse: SignUpResponseBody? = null

    suspend fun callSignIn(view: View, username: String, password: String): SignInResponseBody? {
        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
        val signInInfo = SignInBody(
            username,
            password
        )

        signInResponse = retIn.signIn(signInInfo)
        return signInResponse
    }

//    fun signInCall(view: View, username: String, password: String): SignInResponseBody? {
//
//
//        val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
//        val signInInfo = SignInBody(
//            username,
//            password
//        )
//
//        val call: Call<SignInResponseBody> = retIn.signIn(signInInfo)
//        call.enqueue(object : Callback<SignInResponseBody> {
//            override fun onFailure(call: Call<SignInResponseBody>, t: Throwable) {
//                Toast.makeText(
//                    view.context,
//                    t.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//
//                Log.d("SignIn", t.message.toString())
//            }
//
//            override fun onResponse(
//                call: Call<SignInResponseBody>,
//                response: Response<SignInResponseBody>
//            ) {
//                if (response.code() == 200) {
//
//                    signInResponse = response.body()
//                    Log.d("SignIn", signInResponse.toString())
//
//                    Toast.makeText(
//                        view.context,
//                        "Sign In success!",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//
//                    Log.d("SignIn", "Success")
//
//                } else {
//
//                    Toast.makeText(
//                        view.context,
//                        "Sign In failed!",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    Log.d("SignIn", "Failed")
//                }
//            }
//        })
//
//
//        return signInResponse
//
//
//    }


}
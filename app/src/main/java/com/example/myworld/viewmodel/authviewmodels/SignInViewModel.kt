package com.example.myworld.viewmodel.authviewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworld.activity.authactivities.SignUpActivity
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.repository.AuthRepository
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInViewModel : ViewModel() {

    var txtLoginButton = MutableLiveData<String>()


    var usernameError = MutableLiveData<String>()
    var passError = MutableLiveData<String>()

    var usernameText = MutableLiveData<String>()
    var passwordText = MutableLiveData<String>()

    fun signIn(view: View) {

        if (isUsernameValid(usernameText.value.toString()) && isPasswordValid(passwordText.value.toString())) {

//            val signInResponseBody: SignInResponseBody? = AuthRepository().signInCall(
//                view,
//                usernameText.value.toString(),
//                passwordText.value.toString()
//            )
//
//            if (signInResponseBody == null) {
//                txtLoginButton.value = "Log In Failed! Try Again"
//            } else {
//                txtLoginButton.value = "Log In Successful"
//                //SAVE IN ROOM
//            }

            viewModelScope.launch {
                val result = kotlin.runCatching {
                    AuthRepository().callSignIn(
                        view,
                        usernameText.value.toString(),
                        passwordText.value.toString()
                    )
                }

                result.onSuccess {
                    Log.d("SIGNIN", "SUCCESS ${it.toString()}")
                }

                result.onFailure {
                    Log.d("SIGNIN", "FAILURE")
                }

            }


        } else {
            txtLoginButton.value = "Log In Failed! Try Again"
        }
    }

    fun isUsernameValid(username: String?): Boolean {
        if (username == null ||
            username.length < 6
        ) {
            return false
        }
        return true
    }

    fun isPasswordValid(password: String?): Boolean {
        if (password == null ||
            password.length < 6
        ) {
            return false
        }
        return true
    }

    fun goToSignUp(view: View) {
        val context: Context = view.context
        val intent = Intent(context, SignUpActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }


}
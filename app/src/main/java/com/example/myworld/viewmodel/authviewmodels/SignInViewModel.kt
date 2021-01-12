package com.example.myworld.viewmodel.authviewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworld.activity.authactivities.SignUpActivity
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseBuilder
import com.example.myworld.database.DatabaseHelperImpl

import com.example.myworld.repository.AuthRepository

import kotlinx.coroutines.launch


class SignInViewModel : ViewModel() {

    var txtLoginButton = MutableLiveData<String>()


    var usernameError = MutableLiveData<String>()
    var passError = MutableLiveData<String>()

    var usernameText = MutableLiveData<String>()
    var passwordText = MutableLiveData<String>()
    lateinit var dbHelper: DatabaseHelperImpl

    fun signIn(view: View) {

        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(view.context))

        if (isUsernameValid(usernameText.value.toString()) && isPasswordValid(passwordText.value.toString())) {

            viewModelScope.launch {
                val result = kotlin.runCatching {
                    AuthRepository().callSignIn(
                        usernameText.value.toString(),
                        passwordText.value.toString()
                    )
                }

                result.onSuccess {
                    Log.d("SignIn", "SUCCESS ${it.toString()}")
                    txtLoginButton.value = "Log In Successful"

                    //saving to ROOM
                    val authEntity = AuthEntity(it?.userId!!, it?.email!!, it?.token!!)
                    
                    viewModelScope.launch {
                        val result = kotlin.runCatching {
                            AuthRepository().insertAuth(dbHelper, authEntity)
                        }

                        result.onSuccess { authEntity ->
                            Log.d("DB", "Auth Inserted in DB ${authEntity.toString()}")
                        }

                        result.onFailure { error ->
                            Log.d("DB", "Auth insertion failed ${error.message}")
                        }


                    }

                }

                result.onFailure {
                    Log.d("SignIn", "FAILURE")
                    txtLoginButton.value = "Log In Failed! Try Again"
                }

            }


        } else {
            txtLoginButton.value = "Log In Failed! Try Again"
            Log.d("SignIn", "FAILURE: Invalid credentials submitted")
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
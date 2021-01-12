package com.example.myworld.viewmodel.authviewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myworld.activity.HomeActivity
import com.example.myworld.activity.authactivities.SignUpActivity
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.model.authmodels.SignInResponseBody

import com.example.myworld.repository.AuthRepository

import kotlinx.coroutines.launch


class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private var authRepository: AuthRepository = AuthRepository(application)

    init {
        viewModelScope.launch {
            fetchCurrentUserDB()
        }
    }

    var txtLoginButton = MutableLiveData<String>()

    var usernameError = MutableLiveData<String>()
    var passError = MutableLiveData<String>()

    var usernameText = MutableLiveData<String>()
    var passwordText = MutableLiveData<String>()
    lateinit var dbHelper: DatabaseHelperImpl

    fun signIn(view: View) {

        if (isUsernameValid(usernameText.value.toString()) && isPasswordValid(passwordText.value.toString())) {

            viewModelScope.launch {
                val result = kotlin.runCatching {
                    authRepository.callSignIn(
                        usernameText.value.toString(),
                        passwordText.value.toString()
                    )
                }

                result.onSuccess {
                    Log.d("SignIn", "SUCCESS ${it.toString()}")
                    txtLoginButton.value = "Log In Successful"

                    insertDataInDB(it)
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

    private fun insertDataInDB(signInResponseBody: SignInResponseBody?) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                authRepository.insertAuth(signInResponseBody!!)
            }

            result.onSuccess {
                Log.d("DB", "Auth Inserted in DB ${it.toString()}")
                goToHome()
            }

            result.onFailure {
                Log.d("DB", "Auth Inserted failed ${it.message}")
            }
        }
    }

    private suspend fun fetchCurrentUserDB() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                authRepository.getAllAuth()
            }

            result.onSuccess {
                if (it.size == 1) {
                    goToHome()
                } else {
                    // call clear DB
                }
            }

            result.onFailure {
                // call clear DB
            }

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

    fun goToHome() {
        val context: Context = getApplication()
        val intent = Intent(context, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }


}
package com.example.myworld.viewmodel.authviewmodels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworld.activity.authactivities.SignInActivity
import com.example.myworld.database.AuthEntity
import com.example.myworld.database.DatabaseBuilder
import com.example.myworld.database.DatabaseHelperImpl
import com.example.myworld.model.authmodels.SignUpResponseBody
import com.example.myworld.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.sign

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private var authRepository: AuthRepository = AuthRepository(application)
    var txtSignUpButton = MutableLiveData<String>()

    var regEmailError = MutableLiveData<String>()
    var regUsernameError = MutableLiveData<String>()
    var regPassError = MutableLiveData<String>()


    var regEmailText = MutableLiveData<String>()
    var regPassText = MutableLiveData<String>()
    var regUsernameText = MutableLiveData<String>()

    fun signUp(view: View) { //view will be used later
        if (isRegEmailValid(regEmailText.value.toString()) &&
            isRegUsernameValid(regUsernameText.value.toString()) &&
            isRegPasswordValid(regPassText.value.toString())
        ) {

            viewModelScope.launch {
                val result = kotlin.runCatching {
                    authRepository.callSignUp(
                        regUsernameText.value.toString(),
                        regEmailText.value.toString(),
                        regPassText.value.toString()
                    )
                }

                result.onSuccess {
                    Log.d("SignUp", "SUCCESS ${it.toString()}")
                    insertDataInDB(it)
                }

                result.onFailure {
                    Log.d("SignUp", "FAILURE ${it.message}")
                }

            }
        }
    }

    private fun insertDataInDB(signUpResponseBody: SignUpResponseBody?) {

        viewModelScope.launch {
            val result = kotlin.runCatching {

                authRepository.insertAuth(signUpResponseBody!!)
            }

            result.onSuccess {
                Log.d("DB", "Auth Inserted in DB ${it.toString()}")
            }

            result.onFailure {
                Log.d("DB", "Auth insertion failed ${it.message}")
            }
        }
    }


    fun isRegEmailValid(email: String?): Boolean {
        if (email == null) {
            return false
        }
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern: Pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isRegPasswordValid(password: String?): Boolean {
        if (password == null ||
            password.length < 6
        ) {
            return false
        }
        return true
    }

    fun isRegUsernameValid(username: String?): Boolean {
        if (username == null ||
            username.length < 6 ||
            username.isDigitsOnly()
        ) {
            return false
        }
        return true
    }

    fun goToSignIn(view: View) {
        val context: Context = view.context
        val intent = Intent(context, SignInActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }


}
package com.example.myworld.viewmodel.authviewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myworld.activity.authactivities.SignInActivity
import com.example.myworld.model.authmodels.SignUpBody
import com.example.myworld.model.authmodels.SignUpResponseBody
import com.example.myworld.repository.AuthRepository
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {
    var txtSignUpButton = MutableLiveData<String>()

    var regEmailError = MutableLiveData<String>()
    var regUsernameError = MutableLiveData<String>()
    var regPassError = MutableLiveData<String>()


    var regEmailText = MutableLiveData<String>()
    var regPassText = MutableLiveData<String>()
    var regUsernameText = MutableLiveData<String>()

    fun signUp(view: View) {
        if (isRegEmailValid(regEmailText.value.toString()) &&
            isRegUsernameValid(regUsernameText.value.toString()) &&
            isRegPasswordValid(regPassText.value.toString())
        ) {

            viewModelScope.launch {
                val result = kotlin.runCatching {
                    AuthRepository().callSignUp(
                        regUsernameText.value.toString(),
                        regEmailText.value.toString(),
                        regPassText.value.toString()
                    )
                }

                result.onSuccess {
                    Log.d("SignUp", "SUCCESS ${it.toString()}")
                }

                result.onFailure {
                    Log.d("SignUp", "FAILURE ${it.message}")
                }

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
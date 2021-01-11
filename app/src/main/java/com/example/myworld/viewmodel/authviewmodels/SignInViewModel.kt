package com.example.myworld.viewmodel.authviewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myworld.activity.authactivities.SignUpActivity
import com.example.myworld.model.authmodels.SignInBody
import com.example.myworld.model.authmodels.SignInResponseBody
import com.example.myworld.webservices.ApiInterface
import com.example.myworld.webservices.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInViewModel : ViewModel() {

    var txtLoginButton = MutableLiveData<String>()


    var usernameError = MutableLiveData<String>()
    var passError = MutableLiveData<String>()

    var usernameText = MutableLiveData<String>()
    var passwordText = MutableLiveData<String>()
    var imgUrl = MutableLiveData<String>()

    fun signIn(view: View) {


        if (isUsernameValid(usernameText.value.toString()) && isPasswordValid(passwordText.value.toString())) {

            if (isUsernameValid(usernameText.value.toString()) &&
                isPasswordValid(passwordText.value.toString())
            ) {

                val retIn = RetrofitInstance.getRetrofitInstance().create(ApiInterface::class.java)
                val signInInfo = SignInBody(
                    usernameText.value.toString(),
                    passwordText.value.toString()
                )

                val call: Call<SignInResponseBody> = retIn.signIn(signInInfo)
                call.enqueue(object : Callback<SignInResponseBody> {
                    override fun onFailure(call: Call<SignInResponseBody>, t: Throwable) {
                        Toast.makeText(
                            view.context,
                            t.message,
                            Toast.LENGTH_SHORT
                        ).show()

                        Log.d("SignIn", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<SignInResponseBody>,
                        response: Response<SignInResponseBody>
                    ) {
                        if (response.code() == 200) {

                            txtLoginButton.value = "Log In Successful"
                            imgUrl.value =
                                "https://www.kindpng.com/picc/m/3-30961_deal-with-it-android-cool-android-logo-png.png"

                            Toast.makeText(
                                view.context,
                                "Sign In success!",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            Log.d("SignIn", "Success")

                        } else {
                            Toast.makeText(
                                view.context,
                                "Sign In failed!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Log.d("SignIn", "Failed")
                        }
                    }
                })

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
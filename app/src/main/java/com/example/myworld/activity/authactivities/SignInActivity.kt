package com.example.myworld.activity.authactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myworld.R
import com.example.myworld.databinding.ActivitySignInBinding
import com.example.myworld.viewmodel.authviewmodels.SignInViewModel

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    lateinit var signInViewModel: SignInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        binding.scontext = this
        binding.sigin = signInViewModel
        binding.lifecycleOwner = this

        signInViewModel.txtLoginButton.value = "Log In"


        signInViewModel.usernameText.observe(this, Observer {
            if (signInViewModel.isUsernameValid(it?.toString())) {
                Log.d("Username", it.toString())
                signInViewModel.usernameError.value = null
            } else {
                signInViewModel.usernameError.value = "This username is not valid"
            }
        })

        signInViewModel.passwordText.observe(this, Observer {
            if (signInViewModel.isPasswordValid(it?.toString())) {
                Log.d("PasswordText", "Password Valid")
                signInViewModel.passError.value = null
            } else {
                signInViewModel.passError.value = "This password is not valid"
            }
        })
    }
}
package com.example.myworld.activity.authactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        viewModel.txtSignUpButton.value = "Sign Up"

        viewModel.regEmailText.observe(this, Observer {
            if (viewModel.isRegEmailValid(it?.toString())) {
                Log.d("EmailText", it.toString())
                viewModel.regEmailError.value = null
            } else {
                viewModel.regEmailError.value = "This email is not valid"
            }
        })

        viewModel.regUsernameText.observe(this, Observer {
            if (viewModel.isRegUsernameValid(it?.toString())) {
                Log.d("Username", it.toString())
                viewModel.regUsernameError.value = null
            } else {
                viewModel.regUsernameError.value = "This username is not valid"
            }
        })

        viewModel.regPassText.observe(this, Observer {
            if (viewModel.isRegPasswordValid(it?.toString())) {
                Log.d("PasswordText", "Password Valid")
                viewModel.regPassError.value = null
            } else {
                viewModel.regPassError.value = "This password is not valid"
            }
        })


    }
}
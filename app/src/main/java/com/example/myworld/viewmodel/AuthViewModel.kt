package com.example.myworld.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myworld.model.authresponse.LoginResponse
import com.example.myworld.model.authresponse.SignUpResponse
import com.example.myworld.repository.AuthRepository
import com.example.myworld.webservices.Resource
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : BaseViewModel(repository) {

    private val _loginResponse: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginResponse>>
        get() = _loginResponse

    private val _signUpResponse: MutableLiveData<Resource<SignUpResponse>> = MutableLiveData()
    val signUpResponse: LiveData<Resource<SignUpResponse>>
        get() = _signUpResponse

    fun login(
        username: String,
        password: String
    ) = viewModelScope.launch {
        _loginResponse.value = Resource.Loading
        _loginResponse.value = repository.login(username, password)
    }

    fun signUp(
        username: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _signUpResponse.value = Resource.Loading
        _signUpResponse.value = repository.signUp(username, email, password)
    }

    suspend fun saveLoginUserAuthToken(token: String, user_id: Int) {
        repository.saveLoginUserAuthToken(token, user_id)
    }

    suspend fun saveSignUpUserAuthToken(token: String, email: String, id: Int, username: String) {
        repository.saveSignUpUserAuthToken(token, email, id, username)
    }
}
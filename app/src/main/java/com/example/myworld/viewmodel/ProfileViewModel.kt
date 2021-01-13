package com.example.myworld.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myworld.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var username = MutableLiveData<String>()
    private var profileRepository: ProfileRepository = ProfileRepository(application)

    fun getUsername() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.getUsername()
            }

            result.onSuccess {
                Log.d("SignUp", "SUCCESS: " + it)
                //insertDataInDB(it)
            }

            result.onFailure {
                Log.d("SignUp", "FAILURE ${it.message}")
            }

        }
    }

}
package com.example.myworld.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myworld.database.AuthEntity
import com.example.myworld.repository.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    var username = MutableLiveData<String>()
    var profileEntity: AuthEntity? = null
    private var profileRepository: ProfileRepository = ProfileRepository(application)

    init {
        getUser()
    }


    private fun updateUIData() {
        // function to update data members of this class and update the UI
        username.value = profileEntity?.username
    }

    private fun updateDB() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.updateDB(profileEntity!!)
            }

            result.onSuccess {

            }

            result.onFailure {

            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.getUser()
            }

            result.onSuccess {
                Log.d("GetUser", "Received: $it")
                if (it == null ||
                    it.username == "" ||
                    it.profile_picture == ""
                ) {
                    fetchUserAPI()
                } else {
                    profileEntity = it
                }
            }

            result.onFailure {
                Log.d("GetUsername", "FAILURE ${it.message}")
            }

        }
    }

    private fun fetchUserAPI() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.callFetchProfile()
            }

            result.onSuccess {
                Log.d("profileViewModel", "Username updated to $it")
                profileEntity = it
                updateUIData()
                updateDB()
            }

            result.onFailure {
                Log.d("profileViewModel", "FAILURE ${it.message}")
            }
        }
    }

}
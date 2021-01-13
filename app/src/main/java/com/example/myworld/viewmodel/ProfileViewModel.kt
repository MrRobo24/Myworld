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
    var email = MutableLiveData<String>()
    var profilePicture = MutableLiveData<String>()
    private var profileEntity: AuthEntity? = null
    private var profileRepository: ProfileRepository = ProfileRepository(application)

    init {
        getUser()
    }


    private fun updateUIData() {
        // function to update data members of this class and update the UI
        username.value = profileEntity?.username
        email.value = profileEntity?.email
        profilePicture.value = profileEntity?.profile_picture
    }

    fun updateEmail() {
        profileEntity?.email = email.value.toString()
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.updateEmailById(profileEntity?.email!!, profileEntity?.user_id!!)
            }

            result.onSuccess {
                Log.d("ProfileViewModel", "Email updated in DB")
            }

            result.onFailure {
                Log.d("ProfileViewModel", "Email update in DB FAILED")
            }
        }
    }

    fun updateProfilePicture() {
        profileEntity?.profile_picture = profilePicture.value.toString()
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.updateProfilePictureById(
                    profileEntity?.profile_picture!!,
                    profileEntity?.user_id!!
                )
            }

            result.onSuccess {
                Log.d("ProfileViewModel", "ProfilePicture updated in DB")
            }

            result.onFailure {
                Log.d("ProfileViewModel", "ProfilePicture update in DB FAILED")
            }
        }
    }

    private fun updateDB() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.updateDB(profileEntity!!)
            }

            result.onSuccess {
                Log.d("ProfileViewModel", "DB is updated: $it")
            }

            result.onFailure {
                Log.d("ProfileViewModel", "DB update FAILURE ${it.message}")
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
                    it.username.isEmpty()
                ) {
                    Log.d("ProfileViewModel", "Some fields are empty in DB, calling API")
                    fetchUserAPI()
                } else {
                    profileEntity = it
                    updateUIData()
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
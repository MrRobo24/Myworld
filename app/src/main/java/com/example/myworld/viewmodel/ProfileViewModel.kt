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


    companion object {
        private const val TAG = "ProfileViewModel"
    }

    private var profileRepository: ProfileRepository = ProfileRepository(application)
    var username = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var profilePicture = MutableLiveData<String>()

    private var profileEntity: AuthEntity? = null


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
                Log.d(TAG, "Email updated in DB")
            }

            result.onFailure {
                Log.d(TAG, "Email update in DB FAILED")
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
                Log.d(TAG, "ProfilePicture updated in DB")
            }

            result.onFailure {
                Log.d(TAG, "ProfilePicture update in DB FAILED")
            }
        }
    }

    private fun updateDB() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.updateDB(profileEntity!!)
            }

            result.onSuccess {
                Log.d(TAG, "DB is updated: $it")
            }

            result.onFailure {
                Log.d(TAG, "DB update FAILURE ${it.message}")
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.getUser()
            }

            result.onSuccess {
                Log.d(TAG, "Received from DB: $it")
                if (it == null ||
                    it.username.isEmpty()
                ) {
                    Log.d(TAG, "Some fields are empty in DB, calling API")
                    fetchUserAPI()
                } else {
                    profileEntity = it
                    updateUIData()
                }

                if (it?.profile_picture?.isEmpty()!!) {
                    //making RUD api call for profile_picture for now. Will change the API later)
                    fetchProfilePictureAPI()
                }
            }

            result.onFailure {
                Log.d(TAG, "Get user FAILURE ${it.message}")
            }

        }
    }

    private fun fetchUserAPI() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.callFetchProfile()
            }

            result.onSuccess {
                Log.d(TAG, "Username updated to $it")
                profileEntity = it
                updateUIData()
                updateDB()
            }

            result.onFailure {
                Log.d(TAG, "FAILURE ${it.message}")
            }
        }
    }

    private fun fetchProfilePictureAPI() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                profileRepository.callFetchProfileRUD()
            }

            result.onSuccess {
                Log.d(TAG, "Profile Image fetched: $it")
                //updating data member
                profileEntity?.profile_picture = it
                //updating in UI
                updateUIData()
                //updating in DB
                profileRepository.updateProfilePictureById(it, profileEntity?.user_id!!)
            }

            result.onFailure {
                Log.d(TAG, "Profile Image fetch from RUD API failed: ${it.message}")
            }
        }
    }

}
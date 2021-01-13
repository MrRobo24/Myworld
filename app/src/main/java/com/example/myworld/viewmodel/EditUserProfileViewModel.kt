package com.example.myworld.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myworld.database.AuthEntity
import com.example.myworld.model.EditUserProfileResponse
import com.example.myworld.repository.EditUserProfileRepository
import kotlinx.coroutines.launch


class EditUserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "EditProfileVM"
    private val GALLERY_REQUEST_CODE = 123
    private var editUserProfileRepository: EditUserProfileRepository =
        EditUserProfileRepository(application)

    var username = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var dateOfBirth = MutableLiveData<String>()
    var gender = MutableLiveData<String>()
    var profileEntity: AuthEntity? = null

    init {
        getUserProfile()
    }


    private fun updateUI() {
        if (profileEntity?.birth_date?.isNotEmpty()!!) {
            dateOfBirth.value = profileEntity?.birth_date
        }

        if (profileEntity?.gender?.isNotEmpty()!!) {
            gender.value = profileEntity?.gender
        }

        if (profileEntity?.username?.isNotEmpty()!!) {
            username.value = profileEntity?.username
        }

        if (profileEntity?.email?.isNotEmpty()!!) {
            email.value = profileEntity?.email
        }
    }

    private fun getUserProfile() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                editUserProfileRepository.getUser()
            }

            result.onSuccess {
                Log.d(TAG, "Received from DB: $it")
                profileEntity = it

                if (profileEntity?.birth_date?.isEmpty()!! ||
                    profileEntity?.gender?.isEmpty()!!
                ) {
                    fetchUserAPI()
                } else {
                    updateUI()
                }
            }

            result.onFailure {
                Log.d(TAG, "Receiving from DB failed: ${it.message}")
            }
        }

    }

    private fun fetchUserAPI() {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                editUserProfileRepository.fetchUserAPI()
            }


            result.onSuccess {
                Log.d(TAG, "Fetched User from RUD API: $it")
                updateDB(it)
            }
            result.onFailure {
                Log.d(TAG, "User fetching from RUD API failed: ${it.message}")
            }
        }
    }


    fun saveEditProfile(view: View) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                editUserProfileRepository.saveEditedProfile(
                    gender.value.toString(),
                    dateOfBirth.value.toString()
                )
            }

            result.onSuccess {
                Log.d(TAG, "SUCCESS $it")
                updateDB(it)
            }

            result.onFailure {
                Log.d(TAG, "FAILURE ${it.message}")
            }
        }
    }

    private fun updateDB(it: EditUserProfileResponse) {
        if (it.birth_date.isNotEmpty()) {
            profileEntity?.birth_date = it.birth_date
            updateUI()
            updateBirthDateInDB(it.birth_date)
        }
        if (it.gender.isNotEmpty()) {
            profileEntity?.gender = it.gender
            updateUI()
            updateGenderInDB(it.gender)
        }
    }

    private fun updateBirthDateInDB(dateOfBirth: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                editUserProfileRepository.updateBirthDateById(dateOfBirth)
            }

            result.onSuccess {
                Log.d(TAG, "DOB updated in DB")
            }

            result.onFailure {
                Log.d(TAG, "DOB update in DB failed: ${it.message}")
            }

        }

    }

    private fun updateGenderInDB(gender: String) {
        viewModelScope.launch {
            val result = kotlin.runCatching {
                editUserProfileRepository.updateGenderById(gender)
            }

            result.onSuccess {
                Log.d(TAG, "Gender updated in DB")
            }

            result.onFailure {
                Log.d(TAG, "Gender update in DB failed: ${it.message}")
            }

        }
    }
}
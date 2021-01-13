package com.example.myworld.viewmodel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myworld.repository.EditUserProfileRepository
import kotlinx.coroutines.launch


class EditUserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG = "EditProfileVM"
    private var editUserProfileRepository: EditUserProfileRepository =
        EditUserProfileRepository(application)

    var dateOfBirth = MutableLiveData<String>()
    var gender = MutableLiveData<String>()

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
            }

            result.onFailure {
                Log.d(TAG, "FAILURE ${it.message}")
            }
        }
    }
}
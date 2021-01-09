package com.example.myworld.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myworld.repository.BaseRepository

abstract class BaseViewModel(
    private val repository: BaseRepository
) : ViewModel() {

}
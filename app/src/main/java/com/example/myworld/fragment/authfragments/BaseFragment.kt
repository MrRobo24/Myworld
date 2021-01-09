package com.example.myworld.fragment.authfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.example.myworld.preferences.UserPreferences
import com.example.myworld.repository.BaseRepository
import com.example.myworld.viewmodel.BaseViewModel
import com.example.myworld.viewmodel.ViewModelFactory
import com.example.myworld.webservices.ApiClient

abstract class BaseFragment<VM : BaseViewModel, B : ViewBinding, R : BaseRepository> : Fragment() {

    protected lateinit var binding: B
    protected lateinit var viewModel: VM
    protected val apiClient = ApiClient()
    protected lateinit var userPreferences: UserPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userPreferences = UserPreferences(requireContext())
        binding = getFragmentBinding(inflater, container)
        val factory = ViewModelFactory(getFragmentRepository())
        viewModel = ViewModelProvider(this, factory).get(getViewModel())
        return binding.root
    }

    abstract fun getViewModel(): Class<VM>

    abstract fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getFragmentRepository(): R

}
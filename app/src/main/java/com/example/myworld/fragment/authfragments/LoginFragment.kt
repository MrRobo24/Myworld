package com.example.myworld.fragment.authfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.myworld.R
import com.example.myworld.activity.HomeActivity
import com.example.myworld.databinding.FragmentLoginBinding
import com.example.myworld.repository.AuthRepository
import com.example.myworld.utilites.enable
import com.example.myworld.utilites.handleApiError
import com.example.myworld.utilites.startNewActivity
import com.example.myworld.utilites.visible
import com.example.myworld.viewmodel.AuthViewModel
import com.example.myworld.webservices.ApiService
import com.example.myworld.webservices.Resource
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>(),
    View.OnClickListener {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.btnLogin.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(
                            requireContext(),
                            "token:" + it.value.token,
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.saveLoginUserAuthToken(it.value.token, it.value.user_id)
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }
            }
        })

        binding.etLoginPassword.addTextChangedListener {
            val username = binding.etLoginUsername.text.toString().trim()
            binding.btnLogin.enable(username.isNotEmpty() && it.toString().isNotEmpty())
        }


        binding.btnLogin.setOnClickListener {
            login()
        }

        binding.tvSignUp.setOnClickListener(this)
    }

    private fun login() {
        val username = binding.etLoginUsername.text.toString().trim()
        val password = binding.etLoginPassword.text.toString().trim()
        viewModel.login(username, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(apiClient.buildApi(ApiService::class.java), userPreferences)

    override fun onClick(v: View?) {
        if (v != null) {
            Navigation.findNavController(v)
                .navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }
}
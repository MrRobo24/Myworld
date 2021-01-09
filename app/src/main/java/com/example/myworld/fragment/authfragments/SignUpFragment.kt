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
import com.example.myworld.databinding.FragmentSignUpBinding
import com.example.myworld.repository.AuthRepository
import com.example.myworld.utilites.enable
import com.example.myworld.utilites.handleApiError
import com.example.myworld.utilites.startNewActivity
import com.example.myworld.utilites.visible
import com.example.myworld.viewmodel.AuthViewModel
import com.example.myworld.webservices.ApiService
import com.example.myworld.webservices.Resource
import kotlinx.coroutines.launch

class SignUpFragment : BaseFragment<AuthViewModel, FragmentSignUpBinding, AuthRepository>(),
    View.OnClickListener {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressbar.visible(false)
        binding.btnSignUp.enable(false)

        viewModel.signUpResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        Toast.makeText(
                            requireContext(),
                            "token:" + it.value.token,
                            Toast.LENGTH_LONG
                        ).show()
                        viewModel.saveSignUpUserAuthToken(
                            it.value.token,
                            it.value.user.email,
                            it.value.user.id,
                            it.value.user.username
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { signUp() }
            }
        })

        binding.etSignUpPassword.addTextChangedListener {
            val username = binding.etSignUpUsername.text.toString().trim()
            val email = binding.etSignUpEmail.text.toString().trim()
            val password = binding.etSignUpPassword.text.toString().trim()

            binding.btnSignUp.enable(
                username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
            )
        }


        binding.btnSignUp.setOnClickListener {
            signUp()
        }

        binding.tvLogin.setOnClickListener(this)

    }

    private fun signUp() {
        val username = binding.etSignUpUsername.text.toString().trim()
        val email = binding.etSignUpEmail.text.toString().trim()
        val password = binding.etSignUpPassword.text.toString().trim()
        viewModel.signUp(username, email, password)
    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSignUpBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() =
        AuthRepository(apiClient.buildApi(ApiService::class.java), userPreferences)

    override fun onClick(v: View?) {
        if (v != null) {
            Navigation.findNavController(v)
                .navigate(R.id.action_signUpFragment_to_loginFragment)
        }
    }

}
package com.project.countryholiday.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.project.countryholiday.R
import com.project.countryholiday.databinding.FragmentLoginBinding
import com.project.countryholiday.util.enabled
import com.project.countryholiday.util.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding(FragmentLoginBinding::bind)

    private val viewModel by viewModel<LoginViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupClickListener()
        setupObservers()

        binding.emailEditText.addTextChangedListener {
            viewModel.setEmail(it.toString())
        }
        binding.passwordEditText.addTextChangedListener {
            viewModel.setPassword(it.toString())
        }
    }

    private fun setupClickListener() {
        binding.loginButton.setOnClickListener {
            viewModel.onLoginClicked()
        }
    }

    private fun setupViews() {
        binding.loginButton.enabled(value = false)
    }

    private fun setupObservers() {
        viewModel.validationState.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is LoginUiState.EmailAndPasswordError -> fieldState(
                    emailError = it.emailMessage,
                    passwordError = it.passwordMessage
                )
                is LoginUiState.PasswordError -> fieldState(passwordError = it.message)
                is LoginUiState.ValidationSuccess -> fieldState()
                is LoginUiState.EmailError -> fieldState(emailError = it.message)
                LoginUiState.Idle -> {
                    fieldState()
                    setupViews()
                }
            }
        }

        viewModel.interaction.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is LoginAction.Navigate -> findNavController().navigate(it.destination)
            }
        }
    }

    private fun fieldState(emailError: String? = null, passwordError: String? = null) {
        when {
            emailError != null && passwordError != null -> {
                binding.emailLayout.error = emailError
                binding.passwordLayout.error = passwordError
            }
            emailError == null && passwordError == null -> {
                onValidationSuccessful()
            }
            emailError != null -> {
                binding.emailLayout.error = emailError
                binding.passwordLayout.error = null
            }
            passwordError != null -> {
                binding.passwordLayout.error = passwordError
                binding.emailLayout.error = null
            }
            else -> {}
        }
    }

    private fun onValidationSuccessful() {
        binding.loginButton.enabled(value = true)
        binding.emailLayout.error = null
        binding.passwordLayout.error = null
    }
}

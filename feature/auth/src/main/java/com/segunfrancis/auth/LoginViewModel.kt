package com.segunfrancis.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.segunfrancis.shared.extension.handleThrowable
import com.segunfrancis.shared.extension.isValidEmail
import com.segunfrancis.shared.extension.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _interaction = MutableSharedFlow<LoginAction>(replay = 0)
    val interaction: SharedFlow<LoginAction> = _interaction

    private var email = MutableStateFlow("")
    private var password = MutableStateFlow("")

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.handleThrowable()
    }

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    val validationState = combine(email, password) { e, p ->
        return@combine when {
            e.isBlank() && p.isBlank() -> LoginUiState.Idle
            e.isValidEmail() && p.isBlank() -> LoginUiState.Idle
            e.isBlank() && p.isValidPassword() -> LoginUiState.Idle
            e.isValidEmail().not() && p.isBlank() -> LoginUiState.EmailError()
            e.isBlank() && p.isValidPassword().not() -> LoginUiState.PasswordError()
            e.isValidEmail() && p.isValidPassword().not() -> LoginUiState.PasswordError()
            e.isValidEmail().not() && p.isValidPassword() -> LoginUiState.EmailError()
            e.isValidEmail().not() && p.isValidPassword().not() -> LoginUiState.EmailAndPasswordError()
            e.isValidEmail() && p.isValidPassword() -> LoginUiState.ValidationSuccess
            else -> LoginUiState.Idle
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch(exceptionHandler) {
            _interaction.emit(LoginAction.Navigate)
        }
    }
}

sealed class LoginUiState {
    data class EmailAndPasswordError(
        val emailMessage: String = "Email address is incorrect",
        val passwordMessage: String = "Password is must be at least 6 characters long"
    ) : LoginUiState()

    data class PasswordError(val message: String = "Password is must be at least 6 characters long") :
        LoginUiState()

    data class EmailError(val message: String = "Email address is incorrect") : LoginUiState()
    object ValidationSuccess : LoginUiState()
    object Idle : LoginUiState()
}

sealed class LoginAction {
    object Navigate : LoginAction()
}

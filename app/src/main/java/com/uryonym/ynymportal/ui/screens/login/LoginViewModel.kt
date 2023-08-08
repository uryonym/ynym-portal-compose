package com.uryonym.ynymportal.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.model.Response
import com.uryonym.ynymportal.data.model.Response.Loading
import com.uryonym.ynymportal.data.model.Response.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val signInResponse: Response<Boolean> = Success(false),
    val email: String = "",
    val password: String = "",
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onChangeEmail(value: String) {
        _uiState.update {
            it.copy(email = value)
        }
    }

    fun onChangePassword(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }

    fun onClickLogin() {
        if (uiState.value.email.isNotEmpty() && uiState.value.password.isNotEmpty()) {
            viewModelScope.launch {
                _uiState.update {
                    it.copy(signInResponse = Loading)
                }

                val result = authRepository.signInWithEmailAndPassword(
                    email = uiState.value.email,
                    password = uiState.value.password
                )
                _uiState.update {
                    it.copy(signInResponse = result)
                }
            }
        }
    }

}
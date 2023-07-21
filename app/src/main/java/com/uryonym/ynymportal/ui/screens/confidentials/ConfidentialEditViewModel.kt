package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.DefaultAuthRepository
import com.uryonym.ynymportal.data.DefaultConfidentialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConfidentialEditUiState(
    val isLoading: Boolean = false,
    val confidential: Confidential? = null,
    val serviceName: String = "",
    val loginId: String = "",
    val password: String = "",
    val other: String = "",
    val isConfidentialSaved: Boolean = false
)

class ConfidentialEditViewModel constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val confidentialRepository: ConfidentialRepository = DefaultConfidentialRepository()
    private val authRepository: AuthRepository = DefaultAuthRepository()

    private val confidentialId: String = savedStateHandle["confidentialId"]!!

    private val _uiState = MutableStateFlow(ConfidentialEditUiState())
    val uiState: StateFlow<ConfidentialEditUiState> = _uiState.asStateFlow()

    init {
        getConfidential()
    }

    fun onChangeServiceName(value: String) {
        _uiState.update {
            it.copy(serviceName = value)
        }
    }

    fun onChangeLoginId(value: String) {
        _uiState.update {
            it.copy(loginId = value)
        }
    }

    fun onChangePassword(value: String) {
        _uiState.update {
            it.copy(password = value)
        }
    }

    fun onChangeOther(value: String) {
        _uiState.update {
            it.copy(other = value)
        }
    }

    fun onSaveEditConfidential() {
        if (uiState.value.serviceName.isNotEmpty() && uiState.value.loginId.isNotEmpty()) {
            val editConfidential = Confidential(
                serviceName = uiState.value.serviceName,
                loginId = uiState.value.loginId,
                password = uiState.value.password,
                other = uiState.value.other
            )
            viewModelScope.launch {
                val token = authRepository.getIdToken()
                confidentialRepository.editConfidential(confidentialId, editConfidential, token)
                _uiState.update {
                    it.copy(isConfidentialSaved = true)
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            val token = authRepository.getIdToken()
            confidentialRepository.deleteConfidential(confidentialId, token)
        }
    }

    private fun getConfidential() {
        viewModelScope.launch {
            val token = authRepository.getIdToken()
            confidentialRepository.getConfidential(confidentialId, token).let { confidential ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        serviceName = confidential.serviceName,
                        loginId = confidential.loginId,
                        password = confidential.password ?: "",
                        other = confidential.other ?: "",
                    )
                }
            }
        }
    }
}
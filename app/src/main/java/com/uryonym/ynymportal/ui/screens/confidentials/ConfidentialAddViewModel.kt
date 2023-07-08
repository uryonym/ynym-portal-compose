package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.DefaultConfidentialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ConfidentialAddUiState(
    val isLoading: Boolean = false,
    val confidential: Confidential? = null,
    val serviceName: String = "",
    val loginId: String = "",
    val password: String = "",
    val other: String = "",
    val isConfidentialSaved: Boolean = false
)

class ConfidentialAddViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val confidentialRepository: ConfidentialRepository = DefaultConfidentialRepository()

    private val _uiState = MutableStateFlow(ConfidentialAddUiState())
    val uiState: StateFlow<ConfidentialAddUiState> = _uiState.asStateFlow()

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

    fun onSaveNewConfidential() {
        if (uiState.value.serviceName.isNotEmpty() && uiState.value.loginId.isNotEmpty()) {
            val newConfidential = Confidential(
                serviceName = uiState.value.serviceName,
                loginId = uiState.value.loginId,
                password = uiState.value.password,
                other = uiState.value.other
            )
            viewModelScope.launch {
                confidentialRepository.addConfidential(newConfidential)
                _uiState.update {
                    it.copy(isConfidentialSaved = true)
                }
            }
        }
    }
}

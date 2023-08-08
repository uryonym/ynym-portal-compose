package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.model.Confidential
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConfidentialAddUiState(
    val isLoading: Boolean = false,
    val confidential: Confidential? = null,
    val serviceName: String = "",
    val loginId: String = "",
    val password: String = "",
    val other: String = "",
    val isConfidentialSaved: Boolean = false
)

@HiltViewModel
class ConfidentialAddViewModel @Inject constructor(
    private val confidentialRepository: ConfidentialRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ConfidentialAddUiState())
    val uiState: StateFlow<ConfidentialAddUiState> = _uiState

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
            viewModelScope.launch {
                confidentialRepository.insertConfidential(
                    serviceName = uiState.value.serviceName,
                    loginId = uiState.value.loginId,
                    password = uiState.value.password,
                    other = uiState.value.other
                )
                _uiState.update {
                    it.copy(isConfidentialSaved = true)
                }
            }
        }
    }
}

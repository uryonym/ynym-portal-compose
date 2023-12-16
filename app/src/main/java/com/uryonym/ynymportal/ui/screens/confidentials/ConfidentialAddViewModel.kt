package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.compose.ui.text.input.TextFieldValue
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
    val serviceName: TextFieldValue = TextFieldValue(""),
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

    fun onChangeServiceName(value: TextFieldValue) {
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
        viewModelScope.launch {
            if (uiState.value.serviceName.text.isNotEmpty() && uiState.value.loginId.isNotEmpty()) {
                val confidential = Confidential(
                    serviceName = uiState.value.serviceName.text,
                    loginId = uiState.value.loginId,
                    password = uiState.value.password,
                    other = uiState.value.other
                )
                confidentialRepository.insertConfidential(confidential)

                _uiState.update {
                    it.copy(isConfidentialSaved = true)
                }
            }
        }
    }

}

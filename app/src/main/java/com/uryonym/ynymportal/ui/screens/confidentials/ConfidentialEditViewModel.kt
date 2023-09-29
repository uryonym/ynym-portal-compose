package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.lifecycle.SavedStateHandle
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

data class ConfidentialEditUiState(
    val confidential: Confidential? = null,
    val serviceName: String = "",
    val loginId: String = "",
    val password: String = "",
    val other: String = "",
    val isShowDeleteDialog: Boolean = false,
    val isConfidentialSaved: Boolean = false
)

@HiltViewModel
class ConfidentialEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val confidentialRepository: ConfidentialRepository
) : ViewModel() {
    private val confidentialId: String = savedStateHandle["confidentialId"]!!

    private val _uiState = MutableStateFlow(ConfidentialEditUiState())
    val uiState: StateFlow<ConfidentialEditUiState> = _uiState

    init {
        viewModelScope.launch {
            val confidential = confidentialRepository.getConfidential(confidentialId)
            _uiState.update {
                it.copy(
                    confidential = confidential,
                    serviceName = confidential.serviceName,
                    loginId = confidential.loginId,
                    password = confidential.password,
                    other = confidential.other
                )
            }
        }
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

    fun onChangeShowDeleteDialog(value: Boolean) {
        _uiState.update {
            it.copy(isShowDeleteDialog = value)
        }
    }

    fun onSaveEditConfidential() {
        viewModelScope.launch {
            if (uiState.value.serviceName.isNotEmpty() && uiState.value.loginId.isNotEmpty()) {
                uiState.value.confidential?.let { confidential ->
                    val updateConfidential = confidential.copy(
                        serviceName = uiState.value.serviceName,
                        loginId = uiState.value.loginId,
                        password = uiState.value.password,
                        other = uiState.value.other
                    )
                    confidentialRepository.updateConfidential(updateConfidential)

                    _uiState.update {
                        it.copy(isConfidentialSaved = true)
                    }
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            confidentialRepository.deleteConfidential(confidentialId)

            _uiState.update {
                it.copy(isConfidentialSaved = true)
            }
        }
    }
}

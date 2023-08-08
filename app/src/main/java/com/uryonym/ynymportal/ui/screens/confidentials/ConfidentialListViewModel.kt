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

data class ConfidentialListUiState(
    val isLoading: Boolean = false,
    val confidentials: List<Confidential> = emptyList()
)

@HiltViewModel
class ConfidentialListViewModel @Inject constructor(
    private val confidentialRepository: ConfidentialRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    private val _uiState = MutableStateFlow(ConfidentialListUiState())
    val uiState: StateFlow<ConfidentialListUiState> = _uiState

    init {
        viewModelScope.launch {
            confidentialRepository.getConfidentials().collect { confidentials ->
                _uiState.update {
                    it.copy(confidentials = confidentials)
                }
            }
        }

        refreshConfidentials()
    }

    private fun refreshConfidentials() {
        viewModelScope.launch {
            confidentialRepository.refreshConfidentials()
        }
    }
}
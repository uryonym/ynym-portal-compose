package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.RefuelingRepository
import com.uryonym.ynymportal.data.model.Refueling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RefuelingListUiState(
    val isLoading: Boolean = false,
    val refuelings: List<Refueling> = emptyList()
)

@HiltViewModel
class RefuelingListViewModel @Inject constructor(
    private val refuelingRepository: RefuelingRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    private val _uiState = MutableStateFlow(RefuelingListUiState())
    val uiState: StateFlow<RefuelingListUiState> = _uiState

    init {
        viewModelScope.launch {
            refuelingRepository.getRefuelings().collect { refuelings ->
                _uiState.update {
                    it.copy(refuelings = refuelings)
                }
            }
        }
    }

    fun refreshRefuelings() {
        viewModelScope.launch {
            refuelingRepository.refreshRefuelings()
        }
    }
}

package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.DefaultConfidentialRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ConfidentialsUiState(
    val isLoading: Boolean = false, val confidentials: List<Confidential> = emptyList()
)

class ConfidentialViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val confidentialRepository: ConfidentialRepository = DefaultConfidentialRepository()

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<ConfidentialsUiState> = combine(
        _isLoading, confidentialRepository.getConfidentials()
    ) { isLoading, confidentials ->
        if (confidentials.isNotEmpty()) {
            ConfidentialsUiState(isLoading = false, confidentials = confidentials)
        } else {
            ConfidentialsUiState(isLoading = true)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = ConfidentialsUiState(isLoading = true)
        )
}
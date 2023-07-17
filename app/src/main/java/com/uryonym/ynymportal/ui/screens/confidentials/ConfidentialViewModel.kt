package com.uryonym.ynymportal.ui.screens.confidentials

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.Confidential
import com.uryonym.ynymportal.data.ConfidentialRepository
import com.uryonym.ynymportal.data.DefaultAuthRepository
import com.uryonym.ynymportal.data.DefaultConfidentialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

data class ConfidentialsUiState(
    val isLoading: Boolean = false, val confidentials: List<Confidential> = emptyList()
)

class ConfidentialViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val confidentialRepository: ConfidentialRepository = DefaultConfidentialRepository()
    private val authRepository: AuthRepository = DefaultAuthRepository()

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<ConfidentialsUiState> = combine(
        _isLoading, streamConfidentials()
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

    private fun streamConfidentials(): Flow<List<Confidential>> = flow {
        val token = authRepository.getIdToken()
        emit(confidentialRepository.getConfidentials(token))
    }
}
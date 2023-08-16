package com.uryonym.ynymportal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class YnymPortalAppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentNavigate = MutableStateFlow(YnymPortalScreen.RefuelingList.route)
    val currentNavigate: StateFlow<String> = _currentNavigate

    init {
        getAuthState()
    }

    fun onChangeNavigate(navigate: String) {
        _currentNavigate.value = navigate
    }

    fun getAuthState() = authRepository.getAuthState(viewModelScope)

    fun signOut() = authRepository.signOut()

}

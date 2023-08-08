package com.uryonym.ynymportal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class YnymPortalAppViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        getAuthState()
    }

    fun getAuthState() = authRepository.getAuthState(viewModelScope)

    fun signOut() = authRepository.signOut()

}

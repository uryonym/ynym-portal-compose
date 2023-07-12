package com.uryonym.ynymportal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthRepository
import com.uryonym.ynymportal.data.DefaultAuthRepository

class YnymPortalAppViewModel : ViewModel() {

    private val authRepository: AuthRepository = DefaultAuthRepository()

    init {
        getAuthState()
    }

    fun getAuthState() = authRepository.getAuthState(viewModelScope)

}
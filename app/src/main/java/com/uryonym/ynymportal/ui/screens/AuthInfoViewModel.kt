package com.uryonym.ynymportal.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.AuthInfo
import com.uryonym.ynymportal.data.network.YnymPortalApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthInfoViewModel : ViewModel() {

    private val _authInfoList = MutableStateFlow<List<AuthInfo>>(listOf())
    val authInfoList = _authInfoList.asStateFlow()

    var authInfo: AuthInfo by mutableStateOf(AuthInfo())
        private set

    var serviceName: String by mutableStateOf("")
        private set

    var loginId: String by mutableStateOf("")
        private set

    var password: String by mutableStateOf("")
        private set

    var other: String by mutableStateOf("")
        private set

    init {
        getAuthInfos()
    }

    fun onChangeServiceName(value: String) {
        serviceName = value
    }

    fun onChangeLoginId(value: String) {
        loginId = value
    }

    fun onChangePassword(value: String) {
        password = value
    }

    fun onChangeOther(value: String) {
        other = value
    }

    fun onClickAuthInfoItem(authInfo: AuthInfo) {
        this.authInfo = authInfo
        serviceName = authInfo.serviceName
        loginId = authInfo.loginId
        password = authInfo.password ?: ""
        other = authInfo.other ?: ""
    }

    fun onSaveNewAuthInfo() {
        viewModelScope.launch {
            val newAuthInfo = AuthInfo(
                serviceName = serviceName, loginId = loginId, password = password, other = other
            )
            YnymPortalApi.retrofitService.addAuthInfo(authInfo = newAuthInfo)
            val result = YnymPortalApi.retrofitService.getAuthInfos()
            _authInfoList.value = result
            serviceName = ""
            loginId = ""
            password = ""
            other = ""
        }
    }

    fun onSaveEditAuthInfo() {
        viewModelScope.launch {
            authInfo.serviceName = serviceName
            authInfo.loginId = loginId
            authInfo.password = password
            authInfo.other = other
            authInfo.id?.let {
                YnymPortalApi.retrofitService.editAuthInfo(id = it, authInfo = authInfo)
                val result = YnymPortalApi.retrofitService.getAuthInfos()
                _authInfoList.value = result
            }
            serviceName = ""
            loginId = ""
            password = ""
            other = ""
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            authInfo.id?.let {
                YnymPortalApi.retrofitService.deleteAuthInfo(it)
                val result = YnymPortalApi.retrofitService.getAuthInfos()
                _authInfoList.value = result
            }
            serviceName = ""
            loginId = ""
            password = ""
            other = ""
        }
    }

    private fun getAuthInfos() {
        viewModelScope.launch {
            val result = YnymPortalApi.retrofitService.getAuthInfos()
            _authInfoList.value = result
        }
    }
}
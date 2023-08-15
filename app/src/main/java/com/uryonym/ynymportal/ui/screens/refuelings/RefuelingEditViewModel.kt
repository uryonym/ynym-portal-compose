package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.RefuelingRepository
import com.uryonym.ynymportal.data.model.Refueling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

data class RefuelingEditUiState(
    val isLoading: Boolean = false,
    val refuelingId: String = "",
    val refueling: Refueling? = null,
    val refuelDateTime: Instant = Clock.System.now(),
    val odometer: Int = 0,
    val fuelType: String = "",
    val price: Int = 0,
    val quantity: Int = 0,
    val fullFlag: Boolean = true,
    val gasStand: String = "",
    val selectedCar: Car = Car(),
    val isRefuelingSaved: Boolean = false
)

@HiltViewModel
class RefuelingEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, private val refuelingRepository: RefuelingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RefuelingEditUiState())
    val uiState: StateFlow<RefuelingEditUiState> = _uiState

    init {
        _uiState.update {
            it.copy(refuelingId = savedStateHandle["refuelingId"]!!)
        }

        getRefueling()
    }

    fun onChangeRefuelDateTime(value: Instant) {
        _uiState.update {
            it.copy(refuelDateTime = value)
        }
    }

    fun onChangeOdometer(value: String) {
        _uiState.update {
            it.copy(odometer = value.toInt())
        }
    }

    fun onChangeFuelType(value: String) {
        _uiState.update {
            it.copy(fuelType = value)
        }
    }

    fun onChangePrice(value: String) {
        _uiState.update {
            it.copy(price = value.toInt())
        }
    }

    fun onChangeQuantity(value: String) {
        _uiState.update {
            it.copy(quantity = value.toInt())
        }
    }

    fun onChangeFullFlag(value: Boolean) {
        _uiState.update {
            it.copy(fullFlag = value)
        }
    }

    fun onChangeGasStand(value: String) {
        _uiState.update {
            it.copy(gasStand = value)
        }
    }

    fun onSaveEditRefueling() {
        if (uiState.value.fuelType.isNotEmpty()) {
            viewModelScope.launch {
                refuelingRepository.updateRefueling(
                    id = uiState.value.refuelingId,
                    refuelDateTime = uiState.value.refuelDateTime,
                    odometer = uiState.value.odometer,
                    fuelType = uiState.value.fuelType,
                    price = uiState.value.price,
                    quantity = uiState.value.quantity,
                    fullFlag = uiState.value.fullFlag,
                    gasStand = uiState.value.gasStand,
                    carId = uiState.value.selectedCar.id
                )
                _uiState.update {
                    it.copy(isRefuelingSaved = true)
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            refuelingRepository.deleteRefueling(uiState.value.refuelingId)
            _uiState.update {
                it.copy(isRefuelingSaved = true)
            }
        }
    }

    private fun getRefueling() {
        viewModelScope.launch {
            refuelingRepository.getRefueling(uiState.value.refuelingId).let { refueling ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        refuelDateTime = refueling.refuelDateTime,
                        odometer = refueling.odometer,
                        fuelType = refueling.fuelType,
                        price = refueling.price,
                        quantity = refueling.quantity,
                        fullFlag = refueling.fullFlag,
                        gasStand = refueling.gasStand
                    )
                }
            }
        }
    }

    private fun refreshRefueling() {
        viewModelScope.launch {
            refuelingRepository.refreshRefueling(uiState.value.refuelingId).let { refueling ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        refuelDateTime = refueling.refuelDateTime,
                        odometer = refueling.odometer,
                        fuelType = refueling.fuelType,
                        price = refueling.price,
                        quantity = refueling.quantity,
                        fullFlag = refueling.fullFlag,
                        gasStand = refueling.gasStand
                    )
                }
            }
        }
    }
}
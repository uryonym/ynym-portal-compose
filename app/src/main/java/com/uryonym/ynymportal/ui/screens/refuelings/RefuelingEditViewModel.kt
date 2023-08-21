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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

data class RefuelingEditUiState(
    val isLoading: Boolean = false,
    val refuelingId: String = "",
    val refueling: Refueling? = null,
    val refuelDateTime: Instant = Clock.System.now(),
    val odometer: Int? = null,
    val fuelTypeListExtended: Boolean = false,
    val fuelTypeList: List<String> = listOf("ガソリン", "ハイオク", "軽油"),
    val fuelType: String = "ガソリン",
    val price: Int = 0,
    val quantity: Int = 0,
    val fullFlag: Boolean = true,
    val gasStand: String = "",
    val selectedCar: Car = Car(),
    val isShowDatePicker: Boolean = false,
    val isShowTimePicker: Boolean = false,
    val isRefuelingSaved: Boolean = false
)

@HiltViewModel
class RefuelingEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val refuelingRepository: RefuelingRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RefuelingEditUiState())
    val uiState: StateFlow<RefuelingEditUiState> = _uiState

    init {
        _uiState.update {
            it.copy(refuelingId = savedStateHandle["refuelingId"]!!)
        }

        getRefueling()
    }

    fun onChangeRefuelDate(value: LocalDate) {
        val localDateTime =
            uiState.value.refuelDateTime.toLocalDateTime(TimeZone.currentSystemDefault())
        val newDateTime = LocalDateTime(
            date = value,
            time = localDateTime.time
        ).toInstant(TimeZone.currentSystemDefault())

        _uiState.update {
            it.copy(refuelDateTime = newDateTime)
        }
    }

    fun onChangeRefuelTime(value: LocalTime) {
        val localDateTime =
            uiState.value.refuelDateTime.toLocalDateTime(TimeZone.currentSystemDefault())
        val newDateTime = LocalDateTime(
            date = localDateTime.date,
            time = value
        ).toInstant(TimeZone.currentSystemDefault())

        _uiState.update {
            it.copy(refuelDateTime = newDateTime)
        }
    }

    fun onChangeOdometer(value: String) {
        _uiState.update {
            it.copy(odometer = if (value.isEmpty()) null else value.toInt())
        }
    }

    fun onChangeFuelTypeListExpanded(status: Boolean) {
        _uiState.update {
            it.copy(fuelTypeListExtended = status)
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

    fun onChangeShowDatePicker(value: Boolean) {
        _uiState.update {
            it.copy(isShowDatePicker = value)
        }
    }

    fun onChangeShowTimePicker(value: Boolean) {
        _uiState.update {
            it.copy(isShowTimePicker = value)
        }
    }

    fun onSaveEditRefueling() {
        if (uiState.value.fuelType.isNotEmpty()) {
            uiState.value.odometer?.let { odometer ->
                viewModelScope.launch {
                    refuelingRepository.updateRefueling(
                        id = uiState.value.refuelingId,
                        refuelDateTime = uiState.value.refuelDateTime,
                        odometer = odometer,
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
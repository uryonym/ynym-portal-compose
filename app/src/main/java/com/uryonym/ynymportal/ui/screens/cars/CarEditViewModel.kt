package com.uryonym.ynymportal.ui.screens.cars

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.CarRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CarEditUiState(
    val isLoading: Boolean = false,
    val carId: String = "",
    val car: Car? = null,
    val name: String = "",
    val maker: String = "",
    val model: String = "",
    val modelYear: Int = 0,
    val licensePlate: String = "",
    val tankCapacity: Int = 0,
    val isShowDeleteDialog: Boolean = false,
    val isCarSaved: Boolean = false
)

@HiltViewModel
class CarEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val carRepository: CarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarEditUiState())
    val uiState: StateFlow<CarEditUiState> = _uiState

    init {
        _uiState.update {
            it.copy(carId = savedStateHandle["carId"]!!)
        }

        getCar()
    }

    fun onChangeName(value: String) {
        _uiState.update {
            it.copy(name = value)
        }
    }

    fun onChangeMaker(value: String) {
        _uiState.update {
            it.copy(maker = value)
        }
    }

    fun onChangeModel(value: String) {
        _uiState.update {
            it.copy(model = value)
        }
    }

    fun onChangeModelYear(value: String) {
        _uiState.update {
            it.copy(modelYear = value.toInt())
        }
    }

    fun onChangeLicensePlate(value: String) {
        _uiState.update {
            it.copy(licensePlate = value)
        }
    }

    fun onChangeTankCapacity(value: String) {
        _uiState.update {
            it.copy(tankCapacity = value.toInt())
        }
    }

    fun onChangeShowDeleteDialog(value: Boolean) {
        _uiState.update {
            it.copy(isShowDeleteDialog = value)
        }
    }

    fun onSaveEditCar() {
        if (
            uiState.value.name.isNotEmpty() &&
            uiState.value.maker.isNotEmpty() &&
            uiState.value.model.isNotEmpty()
        ) {
            viewModelScope.launch {
                carRepository.updateCar(
                    id = uiState.value.carId,
                    name = uiState.value.name,
                    maker = uiState.value.maker,
                    model = uiState.value.model,
                    modelYear = uiState.value.modelYear,
                    licensePlate = uiState.value.licensePlate,
                    tankCapacity = uiState.value.tankCapacity
                )
                _uiState.update {
                    it.copy(isCarSaved = true)
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            carRepository.deleteCar(uiState.value.carId)
            _uiState.update {
                it.copy(isCarSaved = true)
            }
        }
    }

    private fun getCar() {
        viewModelScope.launch {
            carRepository.getCar(uiState.value.carId).let { car ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        name = car.name,
                        maker = car.maker,
                        model = car.model,
                        modelYear = car.modelYear,
                        licensePlate = car.licensePlate ?: "",
                        tankCapacity = car.tankCapacity ?: 0
                    )
                }
            }
        }
    }

    private fun refreshCar() {
        viewModelScope.launch {
            carRepository.refreshCar(uiState.value.carId).let { car ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        name = car.name,
                        maker = car.maker,
                        model = car.model,
                        modelYear = car.modelYear,
                        licensePlate = car.licensePlate ?: "",
                        tankCapacity = car.tankCapacity ?: 0
                    )
                }
            }
        }
    }
}
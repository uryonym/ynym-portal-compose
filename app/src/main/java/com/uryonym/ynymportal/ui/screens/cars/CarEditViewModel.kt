package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
    val car: Car? = null,
    val name: TextFieldValue = TextFieldValue(""),
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

    private val carId: String = savedStateHandle["carId"]!!

    private val _uiState = MutableStateFlow(CarEditUiState())
    val uiState: StateFlow<CarEditUiState> = _uiState

    init {
        viewModelScope.launch {
            val car = carRepository.getCar(carId)
            _uiState.update {
                it.copy(
                    name = TextFieldValue(car.name, selection = TextRange(car.name.length)),
                    maker = car.maker,
                    model = car.model,
                    modelYear = car.modelYear,
                    licensePlate = car.licensePlate,
                    tankCapacity = car.tankCapacity
                )
            }
        }
    }

    fun onChangeName(value: TextFieldValue) {
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
        viewModelScope.launch {
            if (
                uiState.value.name.text.isNotEmpty() &&
                uiState.value.maker.isNotEmpty() &&
                uiState.value.model.isNotEmpty()
            ) {
                uiState.value.car?.let { car ->
                    val updateCar = car.copy(
                        name = uiState.value.name.text,
                        maker = uiState.value.maker,
                        model = uiState.value.model,
                        modelYear = uiState.value.modelYear,
                        licensePlate = uiState.value.licensePlate,
                        tankCapacity = uiState.value.tankCapacity
                    )
                    carRepository.updateCar(updateCar)

                    _uiState.update {
                        it.copy(isCarSaved = true)
                    }
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            carRepository.deleteCar(carId)

            _uiState.update {
                it.copy(isCarSaved = true)
            }
        }
    }

}

package com.uryonym.ynymportal.ui.screens.cars

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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

data class CarListUiState(
    val cars: List<Car> = emptyList(),
    var currentCar: Car? = null,
    val name: TextFieldValue = TextFieldValue(""),
    val maker: String = "",
    val model: String = "",
    val modelYear: Int = 0,
    val licensePlate: String = "",
    val tankCapacity: Int = 0,
    val isShowModal: Boolean = false
)

@HiltViewModel
class CarListViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CarListUiState())
    val uiState: StateFlow<CarListUiState> = _uiState

    init {
        viewModelScope.launch {
            carRepository.getCars().collect { cars ->
                _uiState.update {
                    it.copy(cars = cars)
                }
            }
        }
    }

    fun setCar(car: Car) {
        _uiState.update {
            it.copy(
                currentCar = car,
                name = TextFieldValue(car.name, selection = TextRange(car.name.length)),
                maker = car.maker,
                model = car.model,
                modelYear = car.modelYear,
                licensePlate = car.licensePlate,
                tankCapacity = car.tankCapacity
            )
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

    fun onChangeShowModal(value: Boolean) {
        _uiState.update {
            it.copy(isShowModal = value)
        }
        if (!value) {
            onClearState()
        }
    }

    fun onSave() {
        viewModelScope.launch {
            if (
                uiState.value.name.text.isNotEmpty() &&
                uiState.value.maker.isNotEmpty() &&
                uiState.value.model.isNotEmpty()
            ) {
                val car = Car(
                    name = uiState.value.name.text,
                    maker = uiState.value.maker,
                    model = uiState.value.model,
                    modelYear = uiState.value.modelYear,
                    licensePlate = uiState.value.licensePlate,
                    tankCapacity = uiState.value.tankCapacity
                )
                carRepository.insertCar(car)
            }
        }
    }

    fun onDelete(carId: String) {
        viewModelScope.launch {
            carRepository.deleteCar(carId)
        }
    }

    private fun onClearState() {
        _uiState.update {
            it.copy(
                currentCar = null,
                name = TextFieldValue(""),
                maker = "",
                model = "",
                modelYear = 0,
                licensePlate = "",
                tankCapacity = 0
            )
        }
    }

}

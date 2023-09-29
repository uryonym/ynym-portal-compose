package com.uryonym.ynymportal.ui.screens.cars

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

data class CarAddUiState(
    val name: String = "",
    val maker: String = "",
    val model: String = "",
    val modelYear: Int = 0,
    val licensePlate: String = "",
    val tankCapacity: Int = 0,
    val isCarSaved: Boolean = false
)

@HiltViewModel
class CarAddViewModel @Inject constructor(
    private val carRepository: CarRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(CarAddUiState())
    val uiState: StateFlow<CarAddUiState> = _uiState

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

    fun onSaveNewCar() {
        viewModelScope.launch {
            if (
                uiState.value.name.isNotEmpty() &&
                uiState.value.maker.isNotEmpty() &&
                uiState.value.model.isNotEmpty()
            ) {
                val car = Car(
                    name = uiState.value.name,
                    maker = uiState.value.maker,
                    model = uiState.value.model,
                    modelYear = uiState.value.modelYear,
                    licensePlate = uiState.value.licensePlate,
                    tankCapacity = uiState.value.tankCapacity
                )
                carRepository.insertCar(car)

                _uiState.update {
                    it.copy(isCarSaved = true)
                }
            }
        }
    }
}

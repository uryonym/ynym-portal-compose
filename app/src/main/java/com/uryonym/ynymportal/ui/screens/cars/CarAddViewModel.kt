package com.uryonym.ynymportal.ui.screens.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Car
import com.uryonym.ynymportal.data.CarRepository
import com.uryonym.ynymportal.data.DefaultCarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CarAddUiState(
    val isLoading: Boolean = false,
    val car: Car? = null,
    val name: String = "",
    val maker: String = "",
    val model: String = "",
    val modelYear: Int = 0,
    val licensePlate: String = "",
    val tankCapacity: Int? = null,
    val isCarSaved: Boolean = false
)

class CarAddViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val carRepository: CarRepository = DefaultCarRepository()

    private val _uiState = MutableStateFlow(CarAddUiState())
    val uiState: StateFlow<CarAddUiState> = _uiState.asStateFlow()

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
        if (
            uiState.value.name.isNotEmpty() &&
            uiState.value.maker.isNotEmpty() &&
            uiState.value.model.isNotEmpty()
        ) {
            val newCar = Car(
                name = uiState.value.name,
                maker = uiState.value.maker,
                model = uiState.value.model,
                modelYear = uiState.value.modelYear,
                licensePlate = uiState.value.licensePlate,
                tankCapacity = uiState.value.tankCapacity
            )
            viewModelScope.launch {
                carRepository.addCar(newCar)
                _uiState.update {
                    it.copy(isCarSaved = true)
                }
            }
        }
    }
}

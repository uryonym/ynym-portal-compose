package com.uryonym.ynymportal.ui.screens.cars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.Car
import com.uryonym.ynymportal.data.CarRepository
import com.uryonym.ynymportal.data.DefaultCarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class CarsUiState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList()
)

class CarViewModel : ViewModel() {

    // ViewModelの中でRepositoryのインスタンスを作っているのが依存関係になっている
    // hiltを使って解消すべき部分
    private val carRepository: CarRepository = DefaultCarRepository()

    private val _isLoading = MutableStateFlow(false)

    val uiState: StateFlow<CarsUiState> = combine(
        _isLoading, carRepository.getCars()
    ) { isLoading, cars ->
        if (cars.isNotEmpty()) {
            CarsUiState(isLoading = false, cars = cars)
        } else {
            CarsUiState(isLoading = true)
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = CarsUiState(isLoading = true)
        )
}
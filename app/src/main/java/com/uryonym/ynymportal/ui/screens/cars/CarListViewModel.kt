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

data class CarListUiState(
    val cars: List<Car> = emptyList()
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

    fun refresh() {
        viewModelScope.launch {
            carRepository.refreshCars()
        }
    }
}

package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.CarRepository
import com.uryonym.ynymportal.data.RefuelingRepository
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.Refueling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RefuelingListUiState(
    val isLoading: Boolean = false,
    val refuelings: List<Refueling> = emptyList(),
    val carListExpanded: Boolean = false,
    val cars: List<Car> = emptyList(),
    val selectedCar: Car = Car()
)

@HiltViewModel
class RefuelingListViewModel @Inject constructor(
    private val refuelingRepository: RefuelingRepository,
    private val carRepository: CarRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)

    private val _uiState = MutableStateFlow(RefuelingListUiState())
    val uiState: StateFlow<RefuelingListUiState> = _uiState

    init {
        viewModelScope.launch {
            carRepository.getCars().collect { cars ->
                _uiState.update {
                    it.copy(cars = cars)
                }
            }
            refuelingRepository.getRefuelings().collect { refuelings ->
                _uiState.update {
                    it.copy(refuelings = refuelings)
                }
            }
        }
    }

    fun onChangeCarListExpanded(status: Boolean) {
        _uiState.update {
            it.copy(carListExpanded = status)
        }
    }

    fun onChangeSelectedCar(car: Car) {
        _uiState.update {
            it.copy(selectedCar = car)
        }
    }

    fun refreshRefuelings() {
        viewModelScope.launch {
            refuelingRepository.refreshRefuelings()
        }
    }
}

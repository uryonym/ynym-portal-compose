package com.uryonym.ynymportal.ui.screens.refuelings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uryonym.ynymportal.data.CarRepository
import com.uryonym.ynymportal.data.RefuelingRepository
import com.uryonym.ynymportal.data.model.Car
import com.uryonym.ynymportal.data.model.Refueling
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RefuelingListUiState(
    val cars: List<Car> = emptyList(),
    val refuelings: List<Refueling> = emptyList(),
    val carListExpanded: Boolean = false,
    val selectedCar: Car? = null
)

@HiltViewModel
class RefuelingListViewModel @Inject constructor(
    private val carRepository: CarRepository,
    private val refuelingRepository: RefuelingRepository
) : ViewModel() {
    private val _carListExpanded: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _selectedCar: MutableStateFlow<Car?> = MutableStateFlow(null)

    val uiState: StateFlow<RefuelingListUiState> = combine(
        carRepository.getCars(),
        refuelingRepository.getRefuelings(),
        _carListExpanded,
        _selectedCar
    ) { cars, refuelings, carListExpanded, selectedCar ->
        if (selectedCar != null) {
            RefuelingListUiState(
                cars = cars,
                refuelings = refuelings.filter { it.carId == selectedCar.id },
                carListExpanded = carListExpanded,
                selectedCar = selectedCar
            )
        } else {
            RefuelingListUiState(
                cars = cars,
                refuelings = if (cars.isNotEmpty()) refuelings.filter { it.carId == cars.first().id } else emptyList(),
                carListExpanded = carListExpanded,
                selectedCar = if (cars.isNotEmpty()) cars.first() else null
            )
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RefuelingListUiState()
        )

    fun onChangeCarListExpanded(status: Boolean) {
        _carListExpanded.value = status
    }

    fun onChangeSelectedCar(car: Car) {
        _selectedCar.value = car
    }

    fun refresh() {
        viewModelScope.launch {
            carRepository.refreshCars()
            refuelingRepository.refreshRefuelings()
        }
    }
}

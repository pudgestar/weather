package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private lateinit var city: String
    lateinit var router: Router

    private val _screenState = MutableStateFlow<WeatherListState>(WeatherListState(rows = emptyList()))
    val screenState: StateFlow<WeatherListState> = _screenState

    fun setQuery(city: String) {
        this.city = city
        viewModelScope.launch {
            val response = weatherRepository.getWeatherForCity(city)
            _screenState.emit(WeatherListState(title = city, rows = response.toPointRows()))
        }
    }

    fun onRowTapped(dateId: String) {
        router.navigateToDetail(city, dateId)
    }

    fun goBack() {
        router.goBack()
    }


}

data class WeatherListState(val title: String = "", val rows: List<WeatherUiRow>)


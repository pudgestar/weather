package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.domain.RepositoryResult
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val weatherUiUtil: WeatherUiUtil
) : ViewModel() {

    private lateinit var city: String
    lateinit var router: Router

    private val _screenState = MutableStateFlow<WeatherListState>(WeatherListState(rows = emptyList()))
    val screenState: StateFlow<WeatherListState> = _screenState

    fun setQuery(city: String) {
        this.city = city
        viewModelScope.launch {

            when( val result = weatherRepository.getWeatherForCity(city)) {
                is RepositoryResult.Error -> throw IllegalStateException("Should never fail retrieving cached value")
                is RepositoryResult.Success -> {
                    val rows = weatherUiUtil.toPointRows(result.response)
                    _screenState.emit(WeatherListState(title = city, rows = rows))
                }
            }

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


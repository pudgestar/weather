package io.duron.weather.search.ui

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.domain.RepositoryResult
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val searchRepository: WeatherRepository
) : ViewModel() {

    lateinit var router: Router

    private val _loadingState = MutableStateFlow(false)
    private val _textState = MutableStateFlow("")
    private val _errorState = MutableStateFlow<Int?>(null)
    private val _screenState = MutableStateFlow(WeatherInput())

    val screenState: StateFlow<WeatherInput> = _screenState

    init {
        combinePieces()
    }

    /**
     * Combines different flows to produce one view state flow
     */
    private fun combinePieces() {
        combine(_loadingState, _textState, _errorState) { loading, text, error ->
            WeatherInput(error = error, loading = loading, text = text)
        }.onEach { _screenState.value = it }.launchIn(viewModelScope)
    }

    fun onLookupTapped(text: String) {
        val city = text.trim()
        if (city.isEmpty() || city.isBlank()) {
            _errorState.value = R.string.error_city_input
            return
        }
        _errorState.value = null
        fetchCityWeather(city)
    }

    /**
     * make repository call to fetch weather by city
     * if successful then navigate to hourly view,
     * if fails then display error message
     */
    private fun fetchCityWeather(city: String) {
        _loadingState.value = true
        viewModelScope.launch {
            when(searchRepository.getWeatherForCity(city.trim())) {
                is RepositoryResult.Success -> navigateToWeatherList(city)
                is RepositoryResult.Error -> {
                    _errorState.value = R.string.error_fetching_city
                }
            }
            _loadingState.value = false
        }
    }


    fun onTextUpdated(text: String) {
        _textState.value = text
    }

    private fun navigateToWeatherList(query: String) {
        router.navigateToWeatherList(query)
    }

}

data class WeatherInput(
    @StringRes val error: Int? = null,
    val loading: Boolean = false,
    val text: String = ""
)


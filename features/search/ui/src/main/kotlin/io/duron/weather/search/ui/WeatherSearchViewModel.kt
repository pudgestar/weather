package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _errorState = MutableStateFlow<String?>(null)
    private val _screenState = MutableStateFlow(WeatherInput())

    val screenState: StateFlow<WeatherInput> = _screenState

    init {
        combinePieces()
    }

    private fun combinePieces() {
        combine(_loadingState, _textState, _errorState) { loading, text, error ->
            WeatherInput(error = error, loading = loading, text = text)
        }.onEach { _screenState.value = it }.launchIn(viewModelScope)
    }

    fun onLookupTapped(text: String) {
        val city = text.trim()
        if (city.isEmpty() || city.isBlank()) {
            _errorState.value = "Invalid city input"
            return
        }
        _errorState.value = null
        fetchCityWeather(city)
    }

    private fun fetchCityWeather(city: String) {
        _loadingState.value = true
        viewModelScope.launch {
            try {
                searchRepository.getWeatherForCity(city.trim())
                navigateToWeatherList(city)
            } catch (e: Exception) {
                _errorState.value = "Error fetching city"
            }
            _loadingState.value = false

        }
    }

    fun onTextUpdated(text: String) {
        _textState.value = text
    }

    fun navigateToWeatherList(query: String) {
        router.navigateToWeatherList(query)
    }

}

data class WeatherInput(
    val error: String? = null,
    val loading: Boolean = false,
    val text: String = ""
)


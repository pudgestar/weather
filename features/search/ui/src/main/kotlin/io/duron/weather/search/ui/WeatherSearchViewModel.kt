package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.domain.WeatherResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val searchRepository: WeatherRepository
): ViewModel() {

    private val _screenState = MutableStateFlow<WeatherScreenState>(getWeatherInputScreen())
    val screenState: StateFlow<WeatherScreenState> = _screenState

    fun onLookupTapped(text: String) {
        _screenState.value = getWeatherInputScreen(text = text, loading = true)
        viewModelScope.launch {
            val response = searchRepository.getWeatherForCity(text.trim())
            navigateToWeatherList(response)
        }
    }

    fun onTextUpdated(text: String) {
        _screenState.value = getWeatherInputScreen(text)
    }

    private fun getWeatherInputScreen(text: String = "", loading: Boolean = false) = WeatherScreenState.WeatherInput(
        text = text,
        loading = loading
    ) {
            onTextUpdated(it)
        }

    fun navigateToWeatherList(response: WeatherResponse) {
        _screenState.value = WeatherScreenState.WeatherList(response.toPointRows())
    }

}


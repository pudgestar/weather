package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSearchViewModel @Inject constructor(
    private val searchRepository: WeatherRepository
): ViewModel() {

    lateinit var router: Router

    private val _loadingState = MutableStateFlow(false)
    private val _textState = MutableStateFlow("")
    private val _errorState = MutableStateFlow("")
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
        _loadingState.value = true
        viewModelScope.launch {
            searchRepository.getWeatherForCity(text.trim())
            _loadingState.value = false
            navigateToWeatherList(text)
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


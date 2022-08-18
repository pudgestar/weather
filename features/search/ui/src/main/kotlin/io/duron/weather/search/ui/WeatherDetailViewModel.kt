package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.weather.search.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _detailState = MutableStateFlow<WeatherDetailState>(WeatherDetailState.Default)
    val detailState: StateFlow<WeatherDetailState> = _detailState

    fun setCityAndIndex(city: String, index: Int) {
        val point = weatherRepository.getHourlyInfo(city, index)
        _detailState.value = WeatherDetailState.DetailContent(
            temp = point.main.temp.toInt().toString(),
            feelsLike = point.main.feels_like.toInt().toString(),
            summaryTitle = point.weather.first().main,
            summaryBody = point.weather.first().description,
            time = point.date.toString("mm/dd HH:mm")
        )
    }

}


sealed class WeatherDetailState {
    object Default: WeatherDetailState()
    data class DetailContent(
        val temp: String,
        val feelsLike: String,
        val time: String,
        val summaryTitle: String,
        val summaryBody: String
    ): WeatherDetailState()
}

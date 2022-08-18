package io.duron.weather.search.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.duron.weather.router.Router
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

    lateinit var router: Router

    fun setCityAndTime(city: String, dateId: String) {
        val point = weatherRepository.getHourlyInfo(city, dateId)
        _detailState.value = WeatherDetailState.DetailContent(
            temp = point.main.temp.toInt().toString(),
            feelsLike = point.main.feels_like.toInt().toString(),
            summaryTitle = point.weather.first().main,
            summaryBody = point.weather.first().description,
            time = point.date.toString("MM/dd HH:mm"),
            title = city
        )
    }

    fun goBack() {
        router.goBack()
    }

}


sealed class WeatherDetailState {
    object Default: WeatherDetailState()
    data class DetailContent(
        val title: String,
        val temp: String,
        val feelsLike: String,
        val time: String,
        val summaryTitle: String,
        val summaryBody: String
    ): WeatherDetailState()
}

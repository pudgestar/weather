package io.duron.weather.search.data

import io.duron.weather.search.domain.WeatherPoint
import io.duron.weather.search.domain.WeatherResponse
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeatherForCity(city: String): WeatherResponse

    fun getHourlyInfo(city: String, index: Int): WeatherPoint
}
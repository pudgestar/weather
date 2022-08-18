package io.duron.weather.search.data.impl

import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.data.WeatherService
import io.duron.weather.search.domain.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
): WeatherRepository {
    override suspend fun getWeatherForCity(city: String): WeatherResponse {
        return weatherService.getForecastForCity(city)
    }
}
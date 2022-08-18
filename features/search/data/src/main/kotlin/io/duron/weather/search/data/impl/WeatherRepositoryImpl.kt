package io.duron.weather.search.data.impl

import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.data.WeatherService
import io.duron.weather.search.domain.WeatherPoint
import io.duron.weather.search.domain.WeatherResponse
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
): WeatherRepository {

    private val cache = mutableMapOf<String, WeatherResponse>()

    override suspend fun getWeatherForCity(city: String): WeatherResponse {
        return if(cache.containsKey(city)) {
            cache.getValue(city)
        } else {
            weatherService.getForecastForCity(city).also { cache[city] = it }
        }
    }

    override fun getHourlyInfo(city: String, index: Int) = cache.getValue(city).list[index]
}
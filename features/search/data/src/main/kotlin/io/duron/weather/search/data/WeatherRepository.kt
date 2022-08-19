package io.duron.weather.search.data

import io.duron.domain.RepositoryResult
import io.duron.weather.search.domain.WeatherPoint
import io.duron.weather.search.domain.WeatherResponse
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeatherForCity(city: String): RepositoryResult<WeatherResponse>

    fun getHourlyInfo(city: String, dateId: String): WeatherPoint

    fun getTimeOffsetForCity(city: String): Int
}
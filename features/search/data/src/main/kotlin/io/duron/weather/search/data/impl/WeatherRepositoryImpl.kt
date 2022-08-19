package io.duron.weather.search.data.impl

import io.duron.domain.RepositoryResult
import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.data.WeatherService
import io.duron.weather.search.domain.WeatherPoint
import io.duron.weather.search.domain.WeatherResponse
import java.lang.Exception
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {

    private val cache = mutableMapOf<String, WeatherResponse>()

    override suspend fun getWeatherForCity(city: String): RepositoryResult<WeatherResponse> {
        return if (cache.containsKey(city)) {
            RepositoryResult.Success(cache.getValue(city))
        } else {
            try {
                RepositoryResult.Success(weatherService.getForecastForCity(city))
                    .also { cache[city] = it.response }
            } catch (e: Exception) {
                RepositoryResult.Error(e)
            }

        }
    }

    override fun getHourlyInfo(city: String, dateId: String) =
        cache.getValue(city).list.first { it.dt_txt == dateId }

    override fun getTimeOffsetForCity(city: String) = cache.getValue(city).city.timezone
}
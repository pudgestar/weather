package io.duron.weather.search.data

import io.duron.weather.search.domain.WeatherResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast")
    suspend fun getForecastForCity(
        @Query("q") city: String,
        @Query("appid") appId: String = "65d00499677e59496ca2f318eb68c049",
        @Query("units") units: String = "imperial"
    ): WeatherResponse

}
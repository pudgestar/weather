package io.duron.weather.search.data

import io.duron.domain.RepositoryResult
import io.duron.weather.search.data.impl.WeatherRepositoryImpl
import io.duron.weather.search.domain.WeatherResponse
import io.duron.weather.search.test.CITY
import io.duron.weather.search.test.weatherResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    @MockK
    lateinit var service: WeatherService

    lateinit var repository: WeatherRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = WeatherRepositoryImpl(service)
    }

    @Test
    fun `when service successful then return Success result`() = runTest {
        coEvery { service.getForecastForCity(CITY) } returns weatherResponse
        val result = repository.getWeatherForCity(CITY)
        assertThat(result).isEqualTo(RepositoryResult.Success(weatherResponse))
    }

    @Test
    fun `when service fails then return Success result`() = runTest {
        val exception = IllegalStateException()
        coEvery { service.getForecastForCity(CITY) } throws exception
        val result = repository.getWeatherForCity(CITY)
        assertThat(result).isEqualTo(RepositoryResult.Error<WeatherResponse>(exception))
    }

    @Test
    fun `when request made for cached city then cached result used`() = runTest {
        coEvery { service.getForecastForCity(CITY) } returns weatherResponse
        repository.getWeatherForCity(CITY)
        val newResponse = weatherResponse.copy(list = emptyList())
        coEvery { service.getForecastForCity(CITY) } returns newResponse
        assertThat(repository.getWeatherForCity(CITY)).isEqualTo(
            RepositoryResult.Success(
                weatherResponse
            )
        )
    }

    @Test
    fun `correct timezone returned for city`() = runTest {
        coEvery { service.getForecastForCity(CITY) } returns weatherResponse
        repository.getWeatherForCity(CITY)
        assertThat(repository.getTimeOffsetForCity(CITY)).isEqualTo(0)
    }

    @Test
    fun `correct hourly info returned for city and time`() = runTest {
        coEvery { service.getForecastForCity(CITY) } returns weatherResponse
        repository.getWeatherForCity(CITY)
        assertThat(repository.getHourlyInfo(CITY, weatherResponse.list.first().dt_txt)).isEqualTo(
            weatherResponse.list.first()
        )
    }

}
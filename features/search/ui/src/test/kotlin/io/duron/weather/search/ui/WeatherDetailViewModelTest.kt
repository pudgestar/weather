package io.duron.weather.search.ui

import app.cash.turbine.test
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.test.CITY
import io.duron.weather.search.test.weatherResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class WeatherDetailViewModelTest {

    @MockK
    lateinit var repo: WeatherRepository

    @MockK
    lateinit var router: Router

    lateinit var viewModel: WeatherDetailViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = WeatherDetailViewModel(weatherRepository = repo)
        viewModel.router = router
    }

    @Test
    fun `when city and date are set then state is emitted`() = runTest {
        every { repo.getHourlyInfo(CITY, "123") } returns weatherResponse.list.first()
        every { repo.getTimeOffsetForCity(CITY) } returns 0
        viewModel.setCityAndTime(CITY, "123")
        viewModel.detailState.test {
            assertThat(awaitItem()).isEqualTo(WeatherDetailState.DetailContent(
                temp = "100",
                feelsLike = "110",
                summaryTitle = "hot1",
                summaryBody = "really hot",
                title = CITY,
                time = "08/18 01:00"
            ))
        }
    }

    @Test
    fun `when goBack called then router goBack invoked`() {
        viewModel.goBack()
        verify { router.goBack() }
    }


}
package io.duron.weather.search.ui

import app.cash.turbine.test
import io.duron.domain.RepositoryResult
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.domain.*
import io.duron.weather.search.test.CITY
import io.duron.weather.search.test.weatherResponse
import io.duron.weather.testing.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherListViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var repository: WeatherRepository

    @MockK
    lateinit var router: Router

    @MockK
    lateinit var uiUtil: WeatherUiUtil

    lateinit var viewModel: WeatherListViewModel


    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = WeatherListViewModel(weatherRepository = repository, weatherUiUtil = uiUtil)
        viewModel.router = router
        every { uiUtil.toPointRows(any()) } returns weatherRows
        coEvery { repository.getWeatherForCity(CITY) } returns RepositoryResult.Success(
            weatherResponse
        )
    }

    @Test
    fun `when city set, then state is emitted`() = runTest {
        viewModel.setQuery(CITY)
        viewModel.screenState.test {
            awaitItem() // Drop first emission of default state
            assertThat(awaitItem()).isEqualTo(
                WeatherListState(
                    title = CITY,
                    rows = weatherRows
                )
            )

        }
    }

    @Test
    fun `when rowTapped, then navigate to rowDetail`() {
        viewModel.setQuery(CITY)
        viewModel.onRowTapped("dateId1")
        verify { router.navigateToDetail(city = CITY, dateId = "dateId1") }
    }

    @Test
    fun `when goBack called then router goBack invoked`() {
        viewModel.goBack()
        verify { router.goBack() }
    }

}
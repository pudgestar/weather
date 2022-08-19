package io.duron.weather.search.ui

import app.cash.turbine.test
import io.duron.domain.RepositoryResult
import io.duron.weather.router.Router
import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.domain.WeatherResponse
import io.duron.weather.search.test.CITY
import io.duron.weather.testing.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherSearchViewModelTest {


    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @MockK
    lateinit var repo: WeatherRepository

    @MockK
    lateinit var router: Router

    lateinit var viewModel: WeatherSearchViewModel

    private val expectedState = WeatherInput()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = WeatherSearchViewModel(repo)
        viewModel.router = router
    }

    @Test
    fun `when text type then state updated`() = runTest {
        viewModel.screenState.test {
            assertThat(awaitItem()).isEqualTo(expectedState)
            viewModel.onTextUpdated(CITY)
            assertThat(awaitItem().text).isEqualTo(CITY)
        }
    }

    @Test
    fun `when submit is tapped, then loading state is emited`() = runTest {
        val mockWeatherResponse: WeatherResponse = mockk()
        coEvery { repo.getWeatherForCity(CITY) } returns RepositoryResult.Success(mockWeatherResponse)
        viewModel.screenState.test {
            assertThat(awaitItem()).isEqualTo(expectedState)
            viewModel.onTextUpdated(CITY)
            awaitItem() // drop emission
            viewModel.onLookupTapped(CITY)
            assertThat(awaitItem().loading).isTrue()
        }
    }

    @Test
    fun `when input is empty, then error state is emited`() = runTest {
        viewModel.screenState.test {
            assertThat(awaitItem()).isEqualTo(expectedState)
            viewModel.onLookupTapped("")
            assertThat(awaitItem().error).isEqualTo(R.string.error_city_input)
        }
    }

    @Test
    fun `when city call successful, then route user to list`() = runTest {
        val mockWeatherResponse: WeatherResponse = mockk()
        coEvery { repo.getWeatherForCity(CITY) } returns RepositoryResult.Success(mockWeatherResponse)
        viewModel.screenState.test {
            assertThat(awaitItem()).isEqualTo(expectedState)
            viewModel.onTextUpdated(CITY)
            awaitItem() // drop emission
            viewModel.onLookupTapped(CITY)
            awaitItem() // drop loading = true
            awaitItem() // drop loading = false
            verify { router.navigateToWeatherList(CITY) }
        }
    }

    @Test
    fun `when city call fails, then display error to user`() = runTest {
        coEvery { repo.getWeatherForCity(CITY) } returns RepositoryResult.Error(IllegalStateException("Error"))
        viewModel.screenState.test {
            assertThat(awaitItem()).isEqualTo(expectedState)
            viewModel.onTextUpdated(CITY)
            awaitItem() // drop emission
            viewModel.onLookupTapped(CITY)
            awaitItem() // drop loading = true
            val lastEmission = awaitItem()
            assertThat(lastEmission.loading).isFalse()
            assertThat(lastEmission.error).isEqualTo(R.string.error_fetching_city)
        }
    }
}
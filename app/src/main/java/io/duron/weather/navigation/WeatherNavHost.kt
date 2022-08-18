package io.duron.weather.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.duron.weather.router.Router
import io.duron.weather.search.ui.*

@Composable
fun WeatherNavHost(navController: NavHostController) {
    val router: Router = remember {
        AppRouter(navController)
    }
    NavHost(navController = navController, startDestination = "weather" ) {
        composable("weather") {
            val viewModel = hiltViewModel<WeatherSearchViewModel>()
            viewModel.router = router
            WeatherSearchScreen(viewModel = viewModel)
        }

        composable("weather/{city}") {
            val viewModel = hiltViewModel<WeatherListViewModel>()
            viewModel.router = router
            viewModel.setQuery(it.arguments?.getString("city") ?: throw IllegalArgumentException("No city passed"))
            WeatherListScreen(viewModel = viewModel)
        }

        composable("weather/{city}/{dateId}") {
            val city = it.arguments?.getString("city") ?: throw IllegalArgumentException("No city passed")
            val dateId = it.arguments?.getString("dateId") ?: throw IllegalArgumentException("No index passed")
            val viewModel = hiltViewModel<WeatherDetailViewModel>()
            viewModel.router = router
            viewModel.setCityAndTime(city, dateId)
            WeatherDetailScreen(viewModel = viewModel)

        }
    }
}
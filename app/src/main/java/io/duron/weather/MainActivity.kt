package io.duron.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.duron.weather.search.ui.WeatherSearchViewModel
import io.duron.weather.search.ui.WeatherSearchScreen
import io.duron.weather.ui.theme.WeatherTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            WeatherTheme {
               WeatherNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun WeatherNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "cityInput" ) {
        composable("cityInput") {
            val viewModel = hiltViewModel<WeatherSearchViewModel>()
            WeatherSearchScreen(viewModel = viewModel)
        }
    }
}
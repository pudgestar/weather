package io.duron.weather.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun WeatherSearchScreen(viewModel: WeatherSearchViewModel) {
    val screenState = viewModel.screenState.collectAsState()
    val state = screenState.value
    when (state) {
        is WeatherScreenState.WeatherInput -> WeatherInputScreen(
            input = state,
            onLookupTapped = { viewModel.onLookupTapped(it) })
        is WeatherScreenState.WeatherList -> WeatherListScreen(state = state)
        is WeatherScreenState.WeatherDetail -> {}
    }
}
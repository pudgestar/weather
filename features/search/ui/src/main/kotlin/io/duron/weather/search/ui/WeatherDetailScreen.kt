package io.duron.weather.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherDetailScreen(viewModel: WeatherDetailViewModel) {
    val state = viewModel.detailState.collectAsState().value

    Scaffold() {
        if(state is WeatherDetailState.DetailContent) {
            WeatherDetailContent(content = state)
        }
    }
}

@Composable
fun WeatherDetailContent(content: WeatherDetailState.DetailContent) {
    Column(Modifier.fillMaxSize(1f)) {
        Text(content.temp)
        Text(content.feelsLike)
        Text(content.summaryTitle)
        Text(content.summaryBody)
    }
}
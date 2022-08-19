package io.duron.weather.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherDetailScreen(viewModel: WeatherDetailViewModel) {
    val state = viewModel.detailState.collectAsState().value
    val title = (state as? WeatherDetailState.DetailContent)?.title ?: ""
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { SimpleAppBar(title = title, onBack = viewModel::goBack) }
    ) {
        if (state is WeatherDetailState.DetailContent) {
            WeatherDetailContent(content = state)
        }
    }
}

@Composable
fun WeatherDetailContent(content: WeatherDetailState.DetailContent) {
    Column(Modifier.fillMaxSize(1f), horizontalAlignment = Alignment.CenterHorizontally) {
        TemperatureLabel(temp = content.temp, style = MaterialTheme.typography.h1)
        Text(stringResource(R.string.time, content.time), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(16.dp))
        Row() {

            Text(
                stringResource(R.string.feels_like),
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
            TemperatureLabel(temp = content.feelsLike, style = MaterialTheme.typography.h3)
        }

        Spacer(modifier = Modifier.height(64.dp))
        Text(
            content.summaryTitle,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
        Text(
            content.summaryBody,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground
        )
    }
}
package io.duron.weather.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun WeatherSearchScreen(viewModel: WeatherSearchViewModel) {
    val input = viewModel.screenState.collectAsState().value


    Box(modifier = Modifier.fillMaxSize(1f)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = input.text,
                onValueChange = { value -> viewModel.onTextUpdated(value)},
                placeholder = { Text("City Name") })
            OutlinedButton(onClick = { viewModel.onLookupTapped(input.text) }, enabled = !input.loading) {
                Text("Lookup")
            }
        }

        if(input.loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}
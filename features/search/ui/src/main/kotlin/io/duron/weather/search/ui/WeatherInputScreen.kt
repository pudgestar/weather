package io.duron.weather.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun WeatherInputScreen(
    input: WeatherScreenState.WeatherInput,
    onLookupTapped: (String) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize(1f)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = input.text,
                onValueChange = { value -> input.onTextUpdated(value) },
                placeholder = { Text("City Name") })
            OutlinedButton(onClick = { onLookupTapped(input.text) }, enabled = !input.loading) {
                Text("Lookup")
            }
        }

        if(input.loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

    }
}
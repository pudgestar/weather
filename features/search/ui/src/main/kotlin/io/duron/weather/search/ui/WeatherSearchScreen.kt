package io.duron.weather.search.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun WeatherSearchScreen(viewModel: WeatherSearchViewModel) {
    val input = viewModel.screenState.collectAsState().value


    Box(modifier = Modifier
        .fillMaxSize(1f)
        .background(MaterialTheme.colors.background)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherInputField(text = input.text, error = input.error, onTextUpdated = viewModel::onTextUpdated)

            if(input.error != null) {
                Text(stringResource(input.error), color = Color.Red)
            }
            OutlinedButton(onClick = { viewModel.onLookupTapped(input.text) }, enabled = !input.loading) {
                Text(stringResource(R.string.lookup))
            }
        }

        if(input.loading) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun WeatherInputField(text: String, error: Int?, onTextUpdated: (String) -> Unit) {
    OutlinedTextField(
        value = text,
        onValueChange = { value -> onTextUpdated(value)},
        placeholder = { Text(stringResource(R.string.city_name)) },
        isError = error != null,
        colors = TextFieldDefaults.textFieldColors(textColor = MaterialTheme.colors.onBackground),
        trailingIcon = {
            if(text.isNotBlank()) {
                Icon(
                    Icons.Default.Clear,
                    contentDescription = "clear text",
                    modifier = Modifier
                        .clickable {
                            onTextUpdated("")
                        }
                )
            }

        }
    )
}
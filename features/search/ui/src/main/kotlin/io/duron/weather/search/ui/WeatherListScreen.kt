package io.duron.weather.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherListScreen(viewModel: WeatherListViewModel) {
    val state = viewModel.screenState.collectAsState().value
    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = { SimpleAppBar(title = state.title, onBack = viewModel::goBack) }
    ) {

        WeatherSchedule(state = state, onRowTapped = { viewModel.onRowTapped(it) })
    }

}

@Composable
fun WeatherSchedule(state: WeatherListState, onRowTapped: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier

    ) {
        items(state.rows.size) { index ->
            val row = state.rows[index]

            when (row) {
                is WeatherUiRow.DateRow -> WeatherDateRow(date = row.date)
                is WeatherUiRow.WeatherPointRow -> WeatherPointRow(row = row, modifier = Modifier.clickable { onRowTapped(row.dateTxt) })
            }

        }
    }
}

@Composable
fun WeatherDateRow(date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .padding(8.dp),
    ) {
        Column() {
            Text(date, style = MaterialTheme.typography.h3, color = MaterialTheme.colors.onBackground)
            Divider(modifier = Modifier.fillMaxWidth(1f), color = Color.Gray)
        }
    }
}

@Composable
fun WeatherPointRow(row: WeatherUiRow.WeatherPointRow, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp), horizontalArrangement = SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Text(row.summary, style = MaterialTheme.typography.h4, color = MaterialTheme.colors.onBackground)
            Text(row.time, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.onBackground)
        }

        TemperatureLabel(temp = row.temp)
    }
}

@Composable
fun TemperatureLabel(temp: String, style: TextStyle = MaterialTheme.typography.h5) {
    val formattedString = stringResource(id = R.string.farenheit_temp, temp)
    Text(formattedString, style = style, color = MaterialTheme.colors.onBackground)
}

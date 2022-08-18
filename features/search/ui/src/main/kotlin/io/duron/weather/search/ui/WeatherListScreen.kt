package io.duron.weather.search.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherListScreen(viewModel: WeatherListViewModel) {
    val state = viewModel.screenState.collectAsState().value
    Scaffold() {
        WeatherSchedule(state = state, onRowTapped = { viewModel.onRowTapped(it) })
    }

}

@Composable
fun WeatherSchedule(state: WeatherListState, onRowTapped: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier

    ) {
        items(state.rows.size) { index ->
            val row = state.rows[index]
            val clickMod = Modifier.clickable { onRowTapped(index) }
            when (row) {
                is WeatherUiRow.DateRow -> WeatherDateRow(date = row.date, modifier = clickMod)
                is WeatherUiRow.WeatherPointRow -> WeatherPointRow(row = row, modifier = clickMod)
            }

        }
    }
}

@Composable
fun WeatherDateRow(date: String, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp),
    ) {
        Column() {
            Text(date, style = MaterialTheme.typography.h3)
            Divider(modifier = Modifier.fillMaxWidth(1f), color = Color.Gray)
        }
    }
}

@Composable
fun WeatherPointRow(row: WeatherUiRow.WeatherPointRow, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth(1f)
            .padding(8.dp), horizontalArrangement = SpaceBetween
    ) {
        Text(row.time, style = MaterialTheme.typography.h6)
        Column() {
            Text(row.summary, style = MaterialTheme.typography.body1)
            Text(row.temp, style = MaterialTheme.typography.body2)
        }

    }
}
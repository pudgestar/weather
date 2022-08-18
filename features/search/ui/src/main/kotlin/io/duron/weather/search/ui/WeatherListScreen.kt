package io.duron.weather.search.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Colors
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WeatherListScreen(state: WeatherScreenState.WeatherList) {
        LazyColumn(
            modifier = Modifier

        ) {
            items(state.rows.size) { index ->
                val row = state.rows[index]

                when (row) {
                    is WeatherUiRow.DateRow -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .padding(8.dp),
                        ) {
                            Column() {
                                Text(row.date, style = MaterialTheme.typography.h3)
                                Divider(modifier = Modifier.fillMaxWidth(1f), color = Color.Gray)
                            }
                        }
                    }
                    is WeatherUiRow.WeatherPointRow -> {
                        Row(
                            modifier = Modifier
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
                }

            }
        }

}
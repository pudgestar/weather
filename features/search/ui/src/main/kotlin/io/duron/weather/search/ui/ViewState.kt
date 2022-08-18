package io.duron.weather.search.ui

import io.duron.weather.search.domain.WeatherResponse
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

sealed class WeatherScreenState {
    data class WeatherInput(
        val error: String? = null,
        val loading: Boolean = false,
        val text: String = "",
        val onTextUpdated: (String) -> Unit
    ) : WeatherScreenState()

    data class WeatherList(val rows: List<WeatherUiRow>) : WeatherScreenState()
    class WeatherDetail() : WeatherScreenState()
}



sealed class WeatherUiRow {
    data class DateRow(val date: String): WeatherUiRow()
    data class WeatherPointRow(
        val summary: String,
        val temp: String,
        val time: String,
        val date: LocalDateTime
    ): WeatherUiRow()
}

fun WeatherResponse.toPointRows(): List<WeatherUiRow> {
    val pointRows =  this.list.map {
        val date = LocalDateTime.parse(it.dt_txt, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss"))

        WeatherUiRow.WeatherPointRow(
            summary = it.weather.first().main,
            temp = "${it.main.temp}",
            time = date.toString("HH:mm"),
            date = date
        )
    }

    val dates = pointRows.groupBy { it.date.toLocalDate() }
    val sortedEntries = dates.entries.sortedBy { it.key }
    val uiRows = mutableListOf<WeatherUiRow>()
    sortedEntries.forEach {
        uiRows.add(WeatherUiRow.DateRow(it.key.toString("MM/dd")))
        uiRows.addAll(it.value)
    }
    return uiRows
}
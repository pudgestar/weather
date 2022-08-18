package io.duron.weather.search.ui

import io.duron.weather.search.domain.WeatherResponse
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat



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

        WeatherUiRow.WeatherPointRow(
            summary = it.weather.first().main,
            temp = "${it.main.temp.toInt()}",
            time = it.date.toString("HH:mm"),
            date = it.date
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
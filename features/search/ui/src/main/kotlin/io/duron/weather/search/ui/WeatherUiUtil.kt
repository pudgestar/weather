package io.duron.weather.search.ui

import io.duron.weather.search.domain.WeatherResponse
import io.duron.weather.search.domain.getLocalDateTime
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject


sealed class WeatherUiRow {
    data class DateRow(val date: String): WeatherUiRow()
    data class WeatherPointRow(
        val summary: String,
        val temp: String,
        val time: String,
        val date: DateTime,
        val dateTxt: String
    ): WeatherUiRow()
}

class WeatherUiUtil @Inject constructor() {
    fun toPointRows(weatherResponse: WeatherResponse): List<WeatherUiRow> {
        val offset = weatherResponse.city.timezone
        val pointRows =  weatherResponse.list.map {
            val localDate = it.getLocalDateTime(offset)
            WeatherUiRow.WeatherPointRow(
                summary = it.weather.first().main,
                temp = it.main.temp.toInt().toString(),
                time = localDate.toString("HH:mm"),
                date = localDate,
                dateTxt = it.dt_txt
            )
        }

        val dates = pointRows.groupBy { groupString(it.date) }
        val sortedEntries = dates.entries.sortedBy { it.key }
        val uiRows = mutableListOf<WeatherUiRow>()
        sortedEntries.forEach {
            uiRows.add(WeatherUiRow.DateRow(it.key))
            uiRows.addAll(it.value)
        }
        return uiRows
    }

    private fun groupString(time: DateTime) = "${time.monthOfYear}-${time.dayOfMonth}"
}


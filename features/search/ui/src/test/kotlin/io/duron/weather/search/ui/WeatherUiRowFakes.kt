package io.duron.weather.search.ui

import org.joda.time.DateTime

val weatherRows = listOf<WeatherUiRow>(
    WeatherUiRow.DateRow("8-18"),
    WeatherUiRow.WeatherPointRow(
        summary = "hot1",
        temp = "100",
        date = DateTime.parse("2022-08-18T01:00:00"),
        dateTxt = "2022-08-18 01:00:00",
        time = "01:00"
    ),
    WeatherUiRow.WeatherPointRow(
        summary = "hot2",
        temp = "100",
        date = DateTime.parse("2022-08-18T01:00:00"),
        dateTxt = "2022-08-18 01:00:00",
        time = "01:00"
    )
)
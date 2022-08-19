package io.duron.weather.search.domain

import com.squareup.moshi.JsonClass
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val list: List<WeatherPoint>,
    val city: City
)

@JsonClass(generateAdapter = true)
data class WeatherPoint(
    val main: WeatherMain,
    val weather: List<Summary>,
    val dt_txt: String
) {
    val date: DateTime
    get() {
        return DateTime.parse(dt_txt, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss").withZoneUTC())
    }
}

fun WeatherPoint.getLocalDateTime(offset: Int): DateTime = date.plusSeconds(offset)

    @JsonClass(generateAdapter = true)
data class WeatherMain(
    val temp: Float,
    val feels_like: Float,
)

@JsonClass(generateAdapter = true)
data class Summary(
    val main: String,
    val description: String,

)

data class City(
    val timezone: Int
)
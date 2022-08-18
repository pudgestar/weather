package io.duron.weather.search.domain

import com.squareup.moshi.JsonClass
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val cod: String,
    val cnt: Int,
    val list: List<WeatherPoint>,
    val city: City
)

@JsonClass(generateAdapter = true)
data class WeatherPoint(
    val dt: Long,
    val main: WeatherMain,
    val weather: List<Summary>,
    val dt_txt: String
) {
    val date: LocalDateTime
    get() = LocalDateTime.parse(dt_txt, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss"))
}

@JsonClass(generateAdapter = true)
data class WeatherMain(
    val temp: Float,
    val feels_like: Float,
    val temp_min: Float,
    val temp_max: Float,
    val pressure: Float,
)

@JsonClass(generateAdapter = true)
data class Summary(
    val main: String,
    val description: String,

)

data class City(
    val timezone: Long
)
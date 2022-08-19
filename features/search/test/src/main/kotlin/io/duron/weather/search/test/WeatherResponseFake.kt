package io.duron.weather.search.test

import io.duron.weather.search.domain.*
import org.joda.time.DateTime


val weatherResponse = WeatherResponse(
    list = listOf(
        WeatherPoint(
            main = WeatherMain(
                temp = 100.5f,
                feels_like = 110.3f
            ),
            weather = listOf(
                Summary(main = "hot1", description = "really hot")
            ),
            dt_txt = "2022-08-18 01:00:00"
        ),
        WeatherPoint(
            main = WeatherMain(
                temp = 100.5f,
                feels_like = 110.3f
            ),
            weather = listOf(
                Summary(main = "hot2", description = "still hot")
            ),
            dt_txt = "2022-08-18 03:00:00"
        ),

        ),
    city = City(timezone = 0)
)



const val CITY = "new york"
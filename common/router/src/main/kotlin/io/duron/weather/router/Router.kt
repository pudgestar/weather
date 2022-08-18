package io.duron.weather.router

interface Router {

    fun navigateToWeatherList(city: String)

    fun navigateToDetail(city: String, dateId: String)

    fun goBack()
}
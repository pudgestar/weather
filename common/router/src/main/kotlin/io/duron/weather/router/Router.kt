package io.duron.weather.router

interface Router {

    fun navigateToWeatherList(city: String)

    fun navigateToDetail(city: String, index: Int)
}
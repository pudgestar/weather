package io.duron.weather.navigation

import androidx.navigation.NavHostController
import io.duron.weather.router.Router

class AppRouter(
    private val navHostController: NavHostController
) : Router {


    override fun navigateToWeatherList(city: String) {
        navHostController.navigate("weather/$city")
    }

    override fun navigateToDetail(city: String, dateId: String) {
        navHostController.navigate("weather/$city/$dateId")
    }

    override fun goBack() {
        navHostController.navigateUp()
    }
}
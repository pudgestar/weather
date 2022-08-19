package io.duron.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import io.duron.weather.design.theme.DarkGreen
import io.duron.weather.design.theme.LightGreen
import io.duron.weather.design.theme.WeatherTheme
import io.duron.weather.navigation.WeatherNavHost

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()
            DisposableEffect(systemUiController, useDarkIcons) {
                val color = if(useDarkIcons) LightGreen else DarkGreen
                systemUiController.setSystemBarsColor(
                    color = color ,
                    darkIcons = useDarkIcons
                )

                onDispose {}
            }
            val navController = rememberNavController()
            WeatherTheme {
               WeatherNavHost(navController = navController)
            }
        }
    }
}


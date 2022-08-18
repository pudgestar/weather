package io.duron.weather.search.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.duron.weather.search.data.WeatherRepository
import io.duron.weather.search.data.WeatherService
import io.duron.weather.search.data.impl.WeatherRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideWeatherService(retrofit: Retrofit): WeatherService = retrofit.create(WeatherService::class.java)

    @Provides
    fun provideWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository = impl

}
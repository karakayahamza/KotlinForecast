package com.example.kotlinforecast

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class weatherApiServices {


    private val URL = "https://api.openweathermap.org/data/2.5/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(weatherApi::class.java)

    fun getData(name: String, app_id: String): io.reactivex.Observable<WeatherModel> {
        return retrofit.getData(name, app_id,"metric")
    }
}
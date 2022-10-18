package com.example.kotlinforecast

import android.database.Observable
import retrofit2.http.GET
import retrofit2.http.Query
    interface weatherApi {
        @GET("forecast?")
        fun getData(
            @Query("q") name: String?,
            @Query("APPID") app_id: String?,
            @Query("units") units : String?
        ): io.reactivex.Observable<WeatherModel>
    }
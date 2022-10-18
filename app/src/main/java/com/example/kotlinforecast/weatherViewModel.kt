package com.example.kotlinforecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers




class weatherViewModel() : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    private val weatherApiServices = weatherApiServices()

    val weathers = MutableLiveData<WeatherModel>()
    val error = MutableLiveData<Boolean>()
    fun loadData(name:String,app_id: String){
        compositeDisposable = CompositeDisposable()

        compositeDisposable?.add(
            weatherApiServices.getData(name, app_id)
                ?.subscribeOn(Schedulers.newThread())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    weathers.value = it

                },{
                    error.value = false
                })!!
        )

    }
}
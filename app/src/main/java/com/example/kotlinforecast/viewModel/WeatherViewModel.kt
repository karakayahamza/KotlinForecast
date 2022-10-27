package com.example.kotlinforecast.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinforecast.model.WeatherModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers




class weatherViewModel() : ViewModel() {
    private var compositeDisposable: CompositeDisposable? = null
    private val weatherApiServices = com.example.kotlinforecast.apiAndServices.weatherApiServices()

    val weathers = MutableLiveData<WeatherModel>()
    private val error = MutableLiveData<Boolean>()
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
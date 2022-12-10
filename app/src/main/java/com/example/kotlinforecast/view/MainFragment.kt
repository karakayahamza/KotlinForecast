package com.example.kotlinforecast.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinforecast.databinding.FragmentMainBinding
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.kotlinforecast.R
import com.example.kotlinforecast.model.WeatherModel
import java.text.SimpleDateFormat
import java.util.*
import com.example.kotlinforecast.viewModel.WeatherViewModel
import com.google.android.material.snackbar.Snackbar

class MainFragment : Fragment(){

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherModel : WeatherModel
    private val fragmentTagArg = "tag"

    fun newInstance(cityName: String?): MainFragment {
        return newInstance(fragmentTagArg, cityName)
    }

     fun newInstance(cityName: String?, weatherModel: WeatherModel): MainFragment {
        this.weatherModel = weatherModel
        return newInstance(fragmentTagArg, cityName)
    }

    private fun newInstance(tag: String, cityName: String?): MainFragment {
        val fragment = MainFragment()
        val args = Bundle()
        args.putString("cityname", cityName)
        args.putString(fragmentTagArg, tag + "_" + fragment.hashCode())
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this)[WeatherViewModel::class.java]
        arguments?.getString("cityname")
            ?.let { viewModel.loadData(it,"61e8b0259c092b1b9a15474cd800ee25") }
        observeLiveData()
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun observeLiveData(){
        viewModel.weathers.observe(viewLifecycleOwner,{weather ->
            weather?.let {
                weatherModel = it
                @SuppressLint("SimpleDateFormat") val input =
                    SimpleDateFormat("yyyy-MM-dd HH:mm")
                @SuppressLint("SimpleDateFormat") val output = SimpleDateFormat("EEE, d MMM yyyy")


                var convertTime = it.weatherList[0].dt_txt

                val t: Date = input.parse(convertTime)
                convertTime = output.format(t)

                val iconURL = "http://openweathermap.org/img/w/" + it.weatherList[0].weather!![0].icon + ".png"

                binding.textDay.text = convertTime
                binding.cityName.text = it.city.name
                val solution:Double = String.format("%.1f", it.weatherList[0].main?.temp).toDouble()
                binding.textViewTempeture.text = solution.toString()
                binding.textHumm.text = it.weatherList[0].main?.humidity.toString()
                binding.textpressure.text = it.weatherList[0].main?.pressure.toString()
                binding.textwing.text = it.weatherList[0].wind?.speed.toString()
                Glide.with(this).load(iconURL).into(binding.mainTempImage)

                // Hourly tempetures
                for (i in 0 until 8) {
                    val tv :TextView= requireView().findViewById(textViews[i]) as TextView
                    val time : TextView = requireView().findViewById(textTimes[i]) as TextView
                    val tempIcon:ImageView = requireView().findViewById(tempTimesImages[i]) as ImageView

                    val iconurl = "http://openweathermap.org/img/w/" + it.weatherList[i].weather!![0].icon + ".png"
                    Glide.with(this).load(iconurl).into(tempIcon)

                    tv.text = it.weatherList[i].main?.temp.toString().substringBefore(".")+"°C"

                    @SuppressLint("SimpleDateFormat") val input =
                        SimpleDateFormat("yyyy-MM-dd HH:mm")
                    @SuppressLint("SimpleDateFormat") val output = SimpleDateFormat("HH:mm")


                    var convertTime = it.weatherList[i].dt_txt

                    val t: Date = input.parse(convertTime)
                    convertTime = output.format(t)

                    time.text = convertTime

                }
                var a = 0
                for (x in 0..39){

                    @SuppressLint("SimpleDateFormat") val input =
                        SimpleDateFormat("yyyy-MM-dd HH:mm")
                    @SuppressLint("SimpleDateFormat") val output = SimpleDateFormat("HH:mm")


                    var convertTime = it.weatherList[x].dt_txt

                    val t: Date = input.parse(convertTime)
                    convertTime = output.format(t)



                    @SuppressLint("SimpleDateFormat") val input2 =
                        SimpleDateFormat("yyyy-MM-dd HH:mm")
                    @SuppressLint("SimpleDateFormat") val output2 = SimpleDateFormat("EEEE")

                    var convertDay = it.weatherList[x].dt_txt
                    val d : Date = input2.parse(convertDay)
                    convertDay = output2.format(d)


                    if (convertTime.toString() =="12:00"){
                        val tv :TextView= requireView().findViewById(textDays[a]) as TextView

                        tv.text = convertDay

                        val temp :TextView= requireView().findViewById(textMinTemps[a]) as TextView
                        temp.text = it.weatherList[x].main?.temp.toString().substringBefore(".")+"°C"

                        val tempIcon:ImageView = requireView().findViewById(tempDaysImages[a]) as ImageView
                        val icons = "http://openweathermap.org/img/w/" + it.weatherList[x].weather!![0].icon + ".png"
                        Glide.with(this).load(icons).into(tempIcon)
                        a++
                    }
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner,{error->
            error?.let {
                if (it){
                    viewModel.weathers.removeObservers(viewLifecycleOwner)
                    Snackbar.make(binding.root.rootView, "Please check your internet connection***.", Snackbar.LENGTH_LONG).show()
                }

            }
        })
    }


    // XML id's
    private val textViews = intArrayOf(
        R.id.tempeture2,
        R.id.tempeture3,
        R.id.tempeture4,
        R.id.tempeture5,
        R.id.tempeture6,
        R.id.tempeture7,
        R.id.tempeture8,
        R.id.tempeture9
    )

    private val textTimes = intArrayOf(
        R.id.time2,
        R.id.time3,
        R.id.time4,
        R.id.time5,
        R.id.time6,
        R.id.time7,
        R.id.time8,
        R.id.time9
    )

    private val textDays = intArrayOf(
        R.id.firstDay1,
        R.id.firstDay2,
        R.id.firstDay3,
        R.id.firstDay4,
        R.id.firstDay5
    )

    private val textMinTemps = intArrayOf(
        R.id.firstMinTemp1,
        R.id.firstMinTemp2,
        R.id.firstMinTemp3,
        R.id.firstMinTemp4,
        R.id.firstMinTemp5,
        )


    private val tempTimesImages = intArrayOf(
        R.id.imageViewTemp1,
        R.id.imageViewTemp2,
        R.id.imageViewTemp3,
        R.id.imageViewTemp4,
        R.id.imageViewTemp5,
        R.id.imageViewTemp6,
        R.id.imageViewTemp7,
        R.id.imageViewTemp8,
        )
    private val tempDaysImages = intArrayOf(
        R.id.imageViewTemp10,
        R.id.imageViewTemp11,
        R.id.imageViewTemp12,
        R.id.imageViewTemp13,
        R.id.imageViewTemp14,

    )

}
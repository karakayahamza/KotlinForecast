package com.example.kotlinforecast

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.kotlinforecast.databinding.FragmentMainBinding
import android.widget.TextView





class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: weatherViewModel
    private lateinit var weatherModel : WeatherModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this)[weatherViewModel::class.java]
        viewModel.loadData("Ankara","61e8b0259c092b1b9a15474cd800ee25")


        observeLiveData()


        binding.imageView2.setOnClickListener { view ->
            val popup = PopupMenu(context, view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId){
                    R.id.menu_settings-> {
                        Toast.makeText(context,"Setting", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.rain)
                    }
                    R.id.add_newPlace-> {
                        Toast.makeText(context,"Add new place", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.fog)
                    }
                    R.id.about-> {
                        Toast.makeText(context,"About credit by HK", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.snow)
                    }
                    R.id.about2-> {
                        Toast.makeText(context,"About credit by HK", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.sunny)
                    }
                }
                true
            }
            popup.show()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeLiveData(){
        viewModel.weathers.observe(viewLifecycleOwner,{weather ->
            weather?.let {
                weatherModel = it
                println(it.city.name)

                binding.cityName.text = it.city.name
                binding.textViewTempeture.text = it.weatherList[0].main?.temp.toString().substringBefore(".")+"°C"
                binding.textHumm.text = it.weatherList[0].main?.humidity.toString()
                binding.textpressure.text = it.weatherList[0].main?.pressure.toString()
                binding.textwing.text = it.weatherList[0].wind?.speed.toString()

                // Hourly tempetures
                val textViews = intArrayOf(
                    R.id.tempeture1,
                    R.id.tempeture2,
                    R.id.tempeture3,
                    R.id.tempeture4,
                    R.id.tempeture5,
                    R.id.tempeture6,
                    R.id.tempeture7,
                    R.id.tempeture8,
                    R.id.tempeture9,
                )
                for (i in 1..9) {
                    val tv :TextView= requireView().findViewById<TextView>(textViews[i]) as TextView
                    tv.text = it.weatherList[i].main?.temp.toString().substringBefore(".")+"°C"}

            }
        })
    }
}
package com.example.kotlinforecast.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kotlinforecast.R
import com.example.kotlinforecast.adapter.ViewPagerAdapter
import com.example.kotlinforecast.anim.ZoomOutPageTransformer
import com.example.kotlinforecast.databinding.FragmentTempBinding
import com.example.kotlinforecast.viewModel.LiveDataInternetConnections
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import android.net.NetworkCapabilities
import android.util.Log
import androidx.test.core.app.ApplicationProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import androidx.test.core.app.ApplicationProvider.getApplicationContext





class TempFragment : Fragment() {
    private var _binding: FragmentTempBinding? = null
    private val binding get() = _binding!!
    private val fragmentTagArg = "tag"
    private lateinit var mCustomPagerAdapter: ViewPagerAdapter
    private lateinit var cld : LiveDataInternetConnections
    private val mainFragment = MainFragment()
    private var citiesHashSet : HashSet<String> = HashSet()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTempBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mCustomPagerAdapter = ViewPagerAdapter(parentFragmentManager, fragmentTagArg)
        binding.pager.adapter = mCustomPagerAdapter
        binding.pager.setPageTransformer(true, ZoomOutPageTransformer())

        val pref = requireContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        citiesHashSet = pref.getStringSet("cities", HashSet()) as HashSet<String>




        citiesHashSet.forEach { s->
            mCustomPagerAdapter.addPage(mainFragment.newInstance(s))
            binding.pager.currentItem = mCustomPagerAdapter.count
            mCustomPagerAdapter.notifyDataSetChanged()
            println(s)
        }

        //mCustomPagerAdapter.addPage(mainFragment.newInstance("IZMIR"))
        //mCustomPagerAdapter.addPage(mainFragment.newInstance("Ankara"))

        cld = LiveDataInternetConnections(activity?.application!!)

        binding.imageView2.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.goOtherFragment -> {
                        Toast.makeText(requireContext(), "Setting", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(com.example.kotlinforecast.R.id.action_tempFragment_to_mainFragment)
                        popup.dismiss()
                    }
                    R.id.add_newCity -> {

                        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_alert_dialog, null)
                        val mBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView).setTitle("Add new place").show()


                        mBuilder.btn_dialog_add.setOnClickListener {
                                if (isOnline(view.context)){
                                    val text : EditText = mDialogView.findViewById(R.id.et_dialog_add)
                                    val placeName = text.text

                                    if (!(citiesHashSet.contains(placeName.toString()))){
                                        mCustomPagerAdapter.addPage(mainFragment.newInstance(text.text.toString()))
                                        binding.pager.currentItem = mCustomPagerAdapter.count
                                        citiesHashSet.add(placeName.toString().uppercase())
                                        pref.edit().putStringSet("cities",citiesHashSet).apply()
                                        mCustomPagerAdapter.notifyDataSetChanged()
                                    }
                                }
                            else{
                                    Snackbar.make(binding.root.rootView, "Please check your internet connection.", Snackbar.LENGTH_LONG).show()
                                }
                            mBuilder.dismiss()
                        }
                        popup.dismiss()
                    }
                }
                true
            }
            popup.show()
        }

        binding.pager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // your code here
            }

            override fun onPageScrollStateChanged(state: Int) {
                mCustomPagerAdapter.notifyDataSetChanged()
            }
        })
    }
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}
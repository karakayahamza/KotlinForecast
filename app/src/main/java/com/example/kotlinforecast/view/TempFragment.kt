package com.example.kotlinforecast.view

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinforecast.adapter.ViewPagerAdapter
import com.example.kotlinforecast.databinding.FragmentTempBinding
import com.example.kotlinforecast.viewModel.LiveDataInternetConnections
import android.net.NetworkCapabilities
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.gson.Gson
import java.util.ArrayList
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import com.example.kotlinforecast.anim.ZoomOutPageTransformer
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.kotlinforecast.R

class TempFragment : Fragment() {
    private var _binding: FragmentTempBinding? = null
    private val binding get() = _binding!!
    private val fragmentTagArg = "tag"
    private lateinit var mCustomPagerAdapter: ViewPagerAdapter
    private lateinit var cld : LiveDataInternetConnections
    private val mainFragment = MainFragment()
    private lateinit var  toggle : ActionBarDrawerToggle
    private  val activity =  AppCompatActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTempBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mCustomPagerAdapter = ViewPagerAdapter(parentFragmentManager, fragmentTagArg)
        binding.pager.adapter = mCustomPagerAdapter
        binding.pager.setPageTransformer(true, ZoomOutPageTransformer())
        binding.pager.offscreenPageLimit = 5


        // Save and load data
        val sharedPreferences = requireActivity().getSharedPreferences("com.example.kotlinforecast.view",
            Context.MODE_PRIVATE)

        val gson1 = Gson()
        val json1 = sharedPreferences.getString("TAG", "")
        val type: Type =
            object : TypeToken<List<String?>?>() {}.type
        val arrayList: ArrayList<String> =
            gson1.fromJson(json1, type)

        for (a in arrayList){
            mCustomPagerAdapter.addPage(mainFragment.newInstance(a))
            binding.pager.currentItem = mCustomPagerAdapter.count
            mCustomPagerAdapter.notifyDataSetChanged()
            println(a)
        }
        // Internet Check LiveData
        cld = LiveDataInternetConnections(activity?.application!!)


        // Navigation Drawer Initilazite and Settings
        activity.setSupportActionBar(binding.myToolbar)
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toggle = ActionBarDrawerToggle(requireActivity(),binding.myDrawerLayout,binding.myToolbar,
            R.string.open, R.string.close)
        binding.myDrawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        binding.navView.getHeaderView(0).findViewById<EditText>(R.id.placeName).setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm!!.hideSoftInputFromWindow(view.windowToken,0)
            }
        }

        binding.navView.getHeaderView(0).findViewById<Button>(R.id.add_button).setOnClickListener {
            Toast.makeText(requireContext(),"Helloodaskdasodkas",Toast.LENGTH_SHORT).show()
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken,0)

        }

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> Toast.makeText(requireContext(),"Clicked Home",Toast.LENGTH_SHORT).show()
                R.id.nav_users -> Toast.makeText(requireContext(),"Clicked User",Toast.LENGTH_SHORT).show()
                R.id.nav_addblogs -> Toast.makeText(requireContext(),"Clicked Blogs",Toast.LENGTH_SHORT).show()
                R.id.nav_chat -> Toast.makeText(requireContext(),"Clicked Chat",Toast.LENGTH_SHORT).show()
            }
            true
        }



        /*val popup = PopupMenu(requireContext(), view)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(com.example.kotlinforecast.R.menu.menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_city -> {
                    Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                    mCustomPagerAdapter.removePage(binding.pager.currentItem)
                    mCustomPagerAdapter.notifyDataSetChanged()

                    popup.dismiss()
                }
                R.id.add_newCity -> {

                    val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_alert_dialog, null)
                    val mBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView).setTitle("Add new place").show()


                    mBuilder.btn_dialog_add.setOnClickListener {
                        if (isOnline(view.context)){
                            val text : EditText = mDialogView.findViewById(R.id.et_dialog_add)

                            if (!(arrayList.contains(text.text.toString()))){
                                mCustomPagerAdapter.addPage(mainFragment.newInstance(text.text.toString()))
                                binding.pager.currentItem = mCustomPagerAdapter.count
                                arrayList.add(text.text.toString().uppercase())

                                val gson = Gson()

                                val json = gson.toJson(arrayList)

                                sharedPreferences.edit().putString("TAG",json).apply()
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
        popup.show()*/



        /*binding.imageView2.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.delete_city -> {
                        Toast.makeText(requireContext(), "Deleted", Toast.LENGTH_SHORT).show()
                        mCustomPagerAdapter.removePage(binding.pager.currentItem)
                        mCustomPagerAdapter.notifyDataSetChanged()

                        popup.dismiss()
                    }
                    R.id.add_newCity -> {

                        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_alert_dialog, null)
                        val mBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView).setTitle("Add new place").show()


                        mBuilder.btn_dialog_add.setOnClickListener {
                                if (isOnline(view.context)){
                                    val text : EditText = mDialogView.findViewById(R.id.et_dialog_add)

                                    if (!(arrayList.contains(text.text.toString()))){
                                        mCustomPagerAdapter.addPage(mainFragment.newInstance(text.text.toString()))
                                        binding.pager.currentItem = mCustomPagerAdapter.count
                                        arrayList.add(text.text.toString().uppercase())

                                        val gson = Gson()

                                        val json = gson.toJson(arrayList)

                                        sharedPreferences.edit().putString("TAG",json).apply()
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
        }*/

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

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
        }
        return super.onOptionsItemSelected(menuItem)
    }
}
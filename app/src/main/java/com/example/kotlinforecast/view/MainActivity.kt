package com.example.kotlinforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinforecast.adapter.ViewPagerAdapter
import com.example.kotlinforecast.databinding.ActivityMainBinding
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.kotlinforecast.R
import kotlinx.android.synthetic.main.custom_alert_dialog.*
import com.example.kotlinforecast.anim.ZoomOutPageTransformer
class MainActivity : AppCompatActivity() {
    private val fragmentTagArg = "tag"
    private var mCustomPagerAdapter: ViewPagerAdapter? = null
    var mainFragment: MainFragment = MainFragment()
    private var citiesHashSet: HashSet<String> = HashSet()
    private lateinit var preferences: SharedPreferences

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        //preferences = applicationContext.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        //citiesHashSet = preferences.getStringSet("cities", HashSet<String>()) as HashSet<String>
        mCustomPagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentTagArg)
        binding.mViewPager.adapter = mCustomPagerAdapter
        binding.mViewPager.setPageTransformer(true, ZoomOutPageTransformer())

        binding.imageView2.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_settings -> {
                        Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.rain)
                    }
                    R.id.add_newPlace -> {
                        Toast.makeText(this, "Add new place", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.fog)
                    }
                    R.id.about -> {
                        Toast.makeText(this, "About credit by HK", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.snow)
                    }
                    R.id.about2 -> {
                        Toast.makeText(this, "About credit by HK", Toast.LENGTH_SHORT).show()
                        binding.mainLayout.setBackgroundResource(R.drawable.sunny)
                    }
                    R.id.add_newCity -> {

                        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_alert_dialog, null)
                        val mBuilder = AlertDialog.Builder(this).setView(mDialogView).setTitle("Add new place").show()


                        mBuilder.btn_dialog_add.setOnClickListener {

                            val text : EditText = mDialogView.findViewById(R.id.et_dialog_add)
                            mCustomPagerAdapter!!.addPage(mainFragment.newInstance(text.text.toString()))
                        }

                    }
                }
                true
            }
            popup.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }
}
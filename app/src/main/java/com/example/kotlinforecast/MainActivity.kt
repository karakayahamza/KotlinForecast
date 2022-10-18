package com.example.kotlinforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.example.kotlinforecast.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }



    fun showPopup(v : View){
        val popup = PopupMenu(this, v)
        val inflater: MenuInflater = popup.menuInflater
        inflater.inflate(R.menu.menu, popup.menu)
        popup.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.menu_settings-> {
                Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show()
                    binding.mainLayout.setBackgroundResource(R.drawable.rain)

                }
                R.id.add_newPlace-> {
                    Toast.makeText(this,"Add new place",Toast.LENGTH_SHORT).show()
                    binding.mainLayout.setBackgroundResource(R.drawable.fog)
                }
                R.id.about-> {
                    Toast.makeText(this,"About credit by HK",Toast.LENGTH_SHORT).show()
                    binding.mainLayout.setBackgroundResource(R.drawable.snow)
                }
                R.id.about2-> {
                    Toast.makeText(this,"About credit by HK",Toast.LENGTH_SHORT).show()
                    binding.mainLayout.setBackgroundResource(R.drawable.sunny)
                }
            }
            true
        }
        popup.show()
    }*/

}
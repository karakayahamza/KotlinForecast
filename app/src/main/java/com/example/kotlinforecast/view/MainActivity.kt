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
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
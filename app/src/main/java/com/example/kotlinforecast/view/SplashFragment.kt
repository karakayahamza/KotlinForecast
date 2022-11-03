package com.example.kotlinforecast.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kotlinforecast.databinding.FragmentSplashBinding
import android.os.Handler
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import android.view.animation.AnimationUtils
import com.example.kotlinforecast.R


class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val topAnimation = AnimationUtils.loadAnimation(requireContext(),R.anim.top_anim)
        binding.imageView4.startAnimation(topAnimation)

        Glide.with(this).load(R.drawable.weatheranim).into(binding.imageView4)


        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashFragment_to_tempFragment)
        }, 2500)
    }
}
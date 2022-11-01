package com.example.kotlinforecast.view

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kotlinforecast.R
import com.example.kotlinforecast.adapter.ViewPagerAdapter
import com.example.kotlinforecast.anim.ZoomOutPageTransformer
import com.example.kotlinforecast.databinding.FragmentTempBinding
import kotlinx.android.synthetic.main.custom_alert_dialog.*

class TempFragment : Fragment() {
    private var _binding: FragmentTempBinding? = null
    private val binding get() = _binding!!
    private val fragmentTagArg = "tag"
    private lateinit var mCustomPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTempBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainFragment = MainFragment()
        mCustomPagerAdapter = ViewPagerAdapter(parentFragmentManager, fragmentTagArg)
        binding.pager1.adapter = mCustomPagerAdapter
        binding.pager1.setPageTransformer(true, ZoomOutPageTransformer())
        mCustomPagerAdapter.addPage(mainFragment.newInstance("IZMIR"))
        mCustomPagerAdapter.addPage(mainFragment.newInstance("Ankara"))


        binding.imageView2.setOnClickListener { view ->
            val popup = PopupMenu(requireContext(), view)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu, popup.menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.goOtherFragment -> {
                        Toast.makeText(requireContext(), "Setting", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(com.example.kotlinforecast.R.id.action_tempFragment_to_mainFragment)
                    }
                    R.id.add_newCity -> {

                        val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_alert_dialog, null)
                        val mBuilder = AlertDialog.Builder(requireContext()).setView(mDialogView).setTitle("Add new place").show()


                        mBuilder.btn_dialog_add.setOnClickListener {

                            val text : EditText = mDialogView.findViewById(R.id.et_dialog_add)
                            mCustomPagerAdapter.addPage(mainFragment.newInstance(text.text.toString()))
                        }

                    }
                }
                true
            }
            popup.show()
        }

    }
}
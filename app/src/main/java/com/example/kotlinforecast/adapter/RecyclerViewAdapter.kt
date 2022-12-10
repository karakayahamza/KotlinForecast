package com.example.kotlinforecast.adapter

import android.R.attr
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinforecast.databinding.RowLayoutBinding
import android.R.attr.data
import android.app.AlertDialog

import android.widget.CompoundButton
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.kotlinforecast.R
import kotlinx.android.synthetic.main.custom_alert_dialog.*


class RecyclerViewAdapter(private var placeName: ArrayList<String>) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>(){

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return placeName.count()
    }

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: RowHolder, @SuppressLint("RecyclerView") position: Int) {

        holder.binding.name.setOnLongClickListener {
            val builder = AlertDialog.Builder(holder.itemView.context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure delete this place?")


            builder.setPositiveButton("Yes") { _, _ ->
                removeItem(position)
            }

            // performing negative action
            builder.setNegativeButton("No") { _, _ ->

            }

            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()

            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
            true
        }

        holder.binding.name.text = placeName[position]
    }
    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        placeName.removeAt(position)
        notifyDataSetChanged()
    }

}
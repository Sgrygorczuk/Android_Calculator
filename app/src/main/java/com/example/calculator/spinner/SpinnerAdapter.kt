package com.example.calculator.spinner

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.roomtestthree.R
import com.example.roomtestthree.databinding.SpinnerLayoutBinding

class SpinnerAdapter(ctx: Context, private val spinnerItems: List<SpinnerItem>) : ArrayAdapter<SpinnerItem>(ctx, 0, spinnerItems) {
    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return this.createView(position, recycledView, parent)
    }
    private fun createView(position: Int, recycledView: View?, parent: ViewGroup): View {
        //Sets the background behind the textboxes to be black, otherwise it'd be white
        parent.setBackgroundResource(R.drawable.spinner_unpressed)
        //Connects the view to the custom layout
        val view = recycledView ?: SpinnerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false).root
        //Connects the position to the current data class
        val spinnerItem = getItem(position)
        if (spinnerItem != null) {
            view.findViewById<TextView>(R.id.spinnerText).text = spinnerItem.description
            view.findViewById<TextView>(R.id.spinnerText).setTextColor(Color.parseColor(spinnerItem.color))
            view.findViewById<ImageView>(R.id.spinnerImage).visibility = spinnerItem.visibility }

        //Picks what kind of corners the textbox will have
        when (position) {
            0 -> {view.setBackgroundResource(R.drawable.spinner_top)}
            spinnerItems.size-1 -> {view.setBackgroundResource(R.drawable.spinner_bottom)}
            else -> {view.setBackgroundResource(R.drawable.spinner_middle)}
        }
        return view
    }
}
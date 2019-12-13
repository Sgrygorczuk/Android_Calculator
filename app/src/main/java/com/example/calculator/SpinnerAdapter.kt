package com.example.spinnertest

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.calculator.R
import com.example.calculator.SpinnerItem
import kotlinx.android.synthetic.main.spinner_layout.view.*

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
        val view = recycledView ?: LayoutInflater.from(context).inflate(R.layout.spinner_layout, parent, false)
        //Connects the position to the current data class
        val spinnerItem = getItem(position)
        if (spinnerItem != null) {
            view.spinnerText.text = spinnerItem.description
            view.spinnerText.setTextColor(Color.parseColor(spinnerItem.color))
            view.spinnerImage.visibility = spinnerItem.visibility }

        //Picks what kind of corners the textbox will have
        when (position) {
            0 -> {view.setBackgroundResource(R.drawable.spinner_top)}
            spinnerItems.size-1 -> {view.setBackgroundResource(R.drawable.spinner_bottom)}
            else -> {view.setBackgroundResource(R.drawable.spinner_middle)}
        }
        return view
    }
}
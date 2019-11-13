package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_conversion.*

class ConversionActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)

        fun loadUnitTable(errorMsg : String, type : String) : Unit {
            Log.d("Admin", "ConversionActivity: $errorMsg was clicked")
        }

        var areaTypes = arrayOf("Acre")

        fun buttonHighlight(type : String) : Unit {
            areaButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            lengthButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            temperatureButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            volumeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            massButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            dataButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            speedButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
            timeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))

            when (type) {
                "areaButton" -> areaButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "lengthButton" -> lengthButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "temperatureButton" -> temperatureButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "volumeButton" -> volumeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "massButton" -> massButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "dataButton" -> dataButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "speedButton" -> speedButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
                "timeButton" -> timeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
            }
        }

        backButton.setOnClickListener {
            Log.d("Admin", "ConversionActivity: backButton was clicked")
            startActivity(Intent(this, MainActivity::class.java))
        }

        areaButton.setOnClickListener {
            loadUnitTable("areaButton", "area")
            buttonHighlight("areaButton")
        }

        lengthButton.setOnClickListener {
            loadUnitTable("lengthButton", "length")
            buttonHighlight("lengthButton")

        }

        temperatureButton.setOnClickListener {
            loadUnitTable("temperatureButton", "temperature")
            buttonHighlight("temperatureButton")
        }

        volumeButton.setOnClickListener {
            loadUnitTable("volumeButton", "volume")
            buttonHighlight("volumeButton")
        }

        massButton.setOnClickListener {
            loadUnitTable("massButton", "mass")
            buttonHighlight("massButton")
        }

        dataButton.setOnClickListener {
            loadUnitTable("dataButton", "data")
            buttonHighlight("dataButton")
        }

        speedButton.setOnClickListener {
            loadUnitTable("speedButton", "speed")
            buttonHighlight("speedButton")
        }

        timeButton.setOnClickListener {
            loadUnitTable("timeButton", "time")
            buttonHighlight("timeButton")
        }
    }
}
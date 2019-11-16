package com.example.calculator

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_conversion.*
import kotlin.math.abs
import android.text.InputType
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.AdapterView


class ConversionActivity: AppCompatActivity() {

    val areaTypes = arrayOf("Acre (ac)","Are (a)","Hectare (h)","Square Centimeter (cm2)", "Square Foot (ft2)", "Square Inch (in2)", "Square Meter (m2)")
    val areaUnits = arrayOf("ac","a","h","cm2","ft2","in2","m2")
    val lengthTypes = arrayOf("Millimeter (mm)", "Centimeter (cm)", "Meter (m)", "Kilometer (km)", "Inch (in)", "Foot (ft)", "Yard (yard)", "Mile (mi)", "Nautical Mile (NM)", "Mil (mil)")
    val lengthUnits = arrayOf("mm", "cm", "m", "km", "in", "ft", "yard", "mi", "NM", "mil")
    val temperatureTypes = arrayOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)")
    val temperatureUnits = arrayOf("°C","°F","K")
    val volumeTypes = arrayOf("UK Gallon (gal)", "US Gallon (gal)", "Liter (L)", "Milliliter (mL)", "Cubic Centimeter (cm3)", "Cubic Meter (m3)", "Cubic Inch (in3)", "Cubic Foot (ft3)")
    val volumeUnits = arrayOf("gal", "gal", "L", "mL", "cm3", "m3", "in3", "ft3")
    val massTypes = arrayOf("Ton (t)", "UK Ton (t)", "US Ton (t)", "Pound (lb)", "Ounce (oz)", "Kilogram (kg)", "Gram (g)")
    val massUnits = arrayOf("t","t","t","lb","oz","kg","g")
    val dataTypes = arrayOf("Bit (bit)", "Byte (B)", "Kilobyte (KB)", "Megabyte (MB)", "Gigabyte (GM)", "Terabyte (TB)")
    val dataUnits = arrayOf("bit", "B", "KB", "MB", "GB", "TB")
    val speedTypes = arrayOf("Meters per Second (m/s)", "Meters per Hour (m/h)", "Kilometers per Second (km/s)", "Kilometers per Hour (km/h)",
        "Inches per Second (in/s)", "Inches per Hour (in/h)", "Feet per Second (ft/s)", "Feet per Hour (ft/h)", "Miles per Second (mi/s)", "Miles per Hour (mi/h)",
        "Knots (k)")
    val speedUnits = arrayOf("m/s", "m/h", "km/s", "km/h", "in/s", "in/h", "ft/s", "ft/h", "mi/s", "mi/h", "kn")
    val timeTypes = arrayOf("Milliseconds (ms)", "Seconds (s)", "Minutes (m)", "Hours (h)", "Days (d)", "Weeks (wk)")
    val timeUnits = arrayOf("ms", "s", "min", "h", "d", "wk")

    var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)
        //Sets the Area button to be selected as that's the screen we start from
        areaButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))

        /*
        Set up and Functionality of the Spinners
            First 3 Lines connect the spinner to the array that will be displayed in the drop down menu
            Then we have the object that will handle the clicking and selecting that will change information
            in the text boxes
         */

        /*
        Top Area Spinner
         */
        val areaTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaTypes)
        areaTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerArea!!.adapter = areaTop

        topSpinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewArea.text = areaUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Area Spinner
        */
        val areaBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaTypes)
        areaBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerArea!!.adapter = areaBottom

        bottomSpinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewArea.text = areaUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Length Spinner
        */
        val lengthTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, lengthTypes)
        lengthTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerLength!!.adapter = lengthTop

        topSpinnerLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewLength.text = lengthUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Length Spinner
        */
        val lengthBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, lengthTypes)
        lengthBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerLength!!.adapter = lengthBottom

        bottomSpinnerLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewLength.text = lengthUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Temperature Spinner
        */
        val temperatureTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, temperatureTypes)
        temperatureTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerTemperature!!.adapter = temperatureTop

        topSpinnerTemperature.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewTemperature.text = temperatureUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Temperature Spinner
        */
        val temperatureBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, temperatureTypes)
        temperatureBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerTemperature!!.adapter = temperatureBottom

        bottomSpinnerTemperature.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewTemperature.text = temperatureUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Volume Spinner
        */
        val volumeTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, volumeTypes)
        volumeTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerVolume!!.adapter = volumeTop

        topSpinnerVolume.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewVolume.text = volumeUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Volume Spinner
        */
        val volumeBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, volumeTypes)
        volumeBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerVolume!!.adapter = volumeBottom

        bottomSpinnerVolume.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewVolume.text = volumeUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Mass Spinner
        */
        val massTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, massTypes)
        massTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerMass!!.adapter = massTop

        topSpinnerMass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewMass.text = massUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Mass Spinner
        */
        val massBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, massTypes)
        massBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerMass!!.adapter = massBottom

        bottomSpinnerMass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewMass.text = massUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Data Spinner
        */
        val dataTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataTypes)
        dataTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerData!!.adapter = dataTop

        topSpinnerData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewData.text = dataUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Data Spinner
        */
        val dataBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataTypes)
        dataBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerData!!.adapter = dataBottom

        bottomSpinnerData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewData.text = dataUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Speed Spinner
        */
        val speedTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, speedTypes)
        speedTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerSpeed!!.adapter = speedTop

        topSpinnerSpeed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewSpeed.text = speedUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Speed Spinner
        */
        val speedBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, speedTypes)
        speedBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerSpeed!!.adapter = speedBottom

        bottomSpinnerSpeed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewSpeed.text = speedUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Time Spinner
        */
        val timeTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeTypes)
        timeTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerTime!!.adapter = timeTop

        topSpinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewTime.text = timeUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Time Spinner
        */
        val timeBottom = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeTypes)
        timeBottom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bottomSpinnerTime!!.adapter = timeBottom

        bottomSpinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                bottomTextViewTime.text = timeUnits[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Disabling the keyboard when selecting the textEditors
        */
        //Area
        topTextEditorArea!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorArea!!.isFocusable = true
        topTextEditorArea!!.setTextIsSelectable(true)

        bottomTextEditorArea!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorArea!!.isFocusable = true
        bottomTextEditorArea!!.setTextIsSelectable(true)

        //Length
        topTextEditorLength!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorLength!!.isFocusable = true
        topTextEditorLength!!.setTextIsSelectable(true)

        bottomTextEditorLength!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorLength!!.isFocusable = true
        bottomTextEditorLength!!.setTextIsSelectable(true)

        //Temperature
        topTextEditorTemperature!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorTemperature!!.isFocusable = true
        topTextEditorTemperature!!.setTextIsSelectable(true)

        bottomTextEditorTemperature!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorTemperature!!.isFocusable = true
        bottomTextEditorTemperature!!.setTextIsSelectable(true)

        //Volume
        topTextEditorVolume!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorVolume!!.isFocusable = true
        topTextEditorVolume!!.setTextIsSelectable(true)

        bottomTextEditorVolume!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorVolume!!.isFocusable = true
        bottomTextEditorVolume!!.setTextIsSelectable(true)

        //Mass
        topTextEditorMass!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorMass!!.isFocusable = true
        topTextEditorMass!!.setTextIsSelectable(true)

        bottomTextEditorMass!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorMass!!.isFocusable = true
        bottomTextEditorMass!!.setTextIsSelectable(true)

        //Data
        topTextEditorData!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorData!!.isFocusable = true
        topTextEditorData!!.setTextIsSelectable(true)

        bottomTextEditorData!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorData!!.isFocusable = true
        bottomTextEditorData!!.setTextIsSelectable(true)

        //Speed
        topTextEditorSpeed!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorSpeed!!.isFocusable = true
        topTextEditorSpeed!!.setTextIsSelectable(true)

        bottomTextEditorSpeed!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorSpeed!!.isFocusable = true
        bottomTextEditorSpeed!!.setTextIsSelectable(true)

        //Time
        topTextEditorTime!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorTime!!.isFocusable = true
        topTextEditorTime!!.setTextIsSelectable(true)

        bottomTextEditorTime!!.setRawInputType(InputType.TYPE_NULL)
        bottomTextEditorTime!!.isFocusable = true
        bottomTextEditorTime!!.setTextIsSelectable(true)

        fun moveScroll(newPosition : Int) : Unit {
            var counter = abs(currentPosition-newPosition)+1
            //17 = Left, 66 = Right
            var direction : Int = 17
            //currentPosition = 0, newPosition = 2, move to right
            if(currentPosition <= newPosition){direction = 66} else {direction = 17}
            while(counter > 0 && currentPosition != newPosition) {
                Log.d("Admin","Scroll : $direction, Counter : $counter")
                conversionScroll.pageScroll(direction)
                counter--
            }
        }

        fun loadUnitTable(errorMsg : String, type : String, newPosition : Int) : Unit {
            Log.d("Admin", "ConversionActivity: $errorMsg was clicked")
            moveScroll(newPosition)
            currentPosition = newPosition
        }

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
            loadUnitTable("areaButton", "area", 0)
            buttonHighlight("areaButton")
        }

        lengthButton.setOnClickListener {
            loadUnitTable("lengthButton", "length", 1)
            buttonHighlight("lengthButton")

        }

        temperatureButton.setOnClickListener {
            loadUnitTable("temperatureButton", "temperature", 2)
            buttonHighlight("temperatureButton")
        }

        volumeButton.setOnClickListener {
            loadUnitTable("volumeButton", "volume", 3)
            buttonHighlight("volumeButton")
        }

        massButton.setOnClickListener {
            loadUnitTable("massButton", "mass",4)
            buttonHighlight("massButton")
        }

        dataButton.setOnClickListener {
            loadUnitTable("dataButton", "data", 5)
            buttonHighlight("dataButton")
        }

        speedButton.setOnClickListener {
            loadUnitTable("speedButton", "speed", 6)
            buttonHighlight("speedButton")
        }

        timeButton.setOnClickListener {
            loadUnitTable("timeButton", "time", 7)
            buttonHighlight("timeButton")
        }
    }
}
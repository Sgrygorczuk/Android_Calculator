package com.example.calculator

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_conversion.*
import android.text.InputType
import android.view.View
import android.view.View.*
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


    var conversionLogicUnit = ConversionLogic()
    var isTop: Boolean = true
    var currentTop : Int = 0
    var currentBottom : Int = 0
    var currentButton : String = "areaButton"
    var currentPosition : Int = 0

    fun buttonHighlight(type : String) : Unit {
        areaButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        lengthButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        temperatureButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        volumeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        massButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        dataButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        speedButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        timeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))

        Log.d("Admin", "$type")
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

    fun showLayout(type : String) : Unit {
        conversionLayoutArea.visibility = GONE
        conversionLayoutLength.visibility = GONE
        conversionLayoutTemperature.visibility = GONE
        conversionVolume.visibility = GONE
        conversionLayoutMass.visibility = GONE
        conversionLayoutData.visibility = GONE
        conversionLayoutSpeed.visibility = GONE
        conversionLayoutTime.visibility = GONE

        when (type) {
            "areaButton" -> conversionLayoutArea.visibility = VISIBLE
            "lengthButton" -> conversionLayoutLength.visibility = VISIBLE
            "temperatureButton" -> conversionLayoutTemperature.visibility = VISIBLE
            "volumeButton" -> conversionVolume.visibility = VISIBLE
            "massButton" -> conversionLayoutMass.visibility = VISIBLE
            "dataButton" -> conversionLayoutData.visibility = VISIBLE
            "speedButton" -> conversionLayoutSpeed.visibility = VISIBLE
            "timeButton" -> conversionLayoutTime.visibility = VISIBLE
        }
    }

    fun inputFocus(type : String) : Unit{
        if(isTop)
        {
            when (type) {
                "areaButton" -> topTextEditorArea.requestFocus()
                "lengthButton" -> topTextEditorLength.requestFocus()
                "temperatureButton" -> topTextEditorTemperature.requestFocus()
                "volumeButton" -> topTextEditorVolume.requestFocus()
                "massButton" -> topTextEditorMass.requestFocus()
                "dataButton" -> topTextEditorData.requestFocus()
                "speedButton" -> topTextEditorSpeed.requestFocus()
                "timeButton" -> topTextEditorTime.requestFocus()
            }
        }
        else
        {
            when (type) {
                "areaButton" -> bottomTextEditorArea.requestFocus()
                "lengthButton" -> bottomTextEditorLength.requestFocus()
                "temperatureButton" -> bottomTextEditorTemperature.requestFocus()
                "volumeButton" -> bottomTextEditorVolume.requestFocus()
                "massButton" -> bottomTextEditorMass.requestFocus()
                "dataButton" -> bottomTextEditorData.requestFocus()
                "speedButton" -> bottomTextEditorSpeed.requestFocus()
                "timeButton" -> bottomTextEditorTime.requestFocus()
            }
        }
    }

    fun plusMinusAble(){
        if(currentPosition == 2){
            plusMinusButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_selector))
            plusMinusButton.setTextColor(Color.parseColor("#FFFFFF"))
            plusMinusButton.isEnabled = true
        }
        else{
            plusMinusButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_disabled_button))
            plusMinusButton.setTextColor(Color.parseColor("#9C9C9C"))
            plusMinusButton.isEnabled = false
        }
    }

    fun upDownArrows(){
        if(isTop){
            bottomButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_selector))
            bottomButton.setTextColor(Color.parseColor("#00C604"))
            bottomButton.isEnabled = true

            topButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_disabled_button))
            topButton.setTextColor(Color.parseColor("#006303"))
            topButton.isEnabled = false
        }
        else{
            topButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_selector))
            topButton.setTextColor(Color.parseColor("#00C604"))
            topButton.isEnabled = true

            bottomButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_disabled_button))
            bottomButton.setTextColor(Color.parseColor("#006303"))
            bottomButton.isEnabled = false
        }
    }

    fun loadUnitTable(buttonName : String, newPosition : Int) : Unit {
        Log.d("Admin", "ConversionActivity: $buttonName was clicked")
        buttonHighlight(buttonName)
        showLayout(buttonName)
        inputFocus(buttonName)
        currentButton = buttonName
        currentPosition = newPosition
        plusMinusAble()
        upDownArrows()
    }

    fun updateLocationTop(view : View) : Unit{
        Log.d("Admin", "Top text box was clicked")
        isTop = true
        loadUnitTable(currentButton ,currentPosition)

    }

    fun updateLocationBottom(view : View) : Unit {
        Log.d("Admin", "Bottom text box was clicked")
        isTop = false
        loadUnitTable(currentButton ,currentPosition)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)
        //Sets the Area button to be selected as that's the screen we start from
        loadUnitTable("areaButton", 0)

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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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
                currentTop = position
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
                currentBottom = position
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

        backButton.setOnClickListener {
            Log.d("Admin", "ConversionActivity: backButton was clicked")
            startActivity(Intent(this, MainActivity::class.java))
        }

        areaButton.setOnClickListener {
            loadUnitTable("areaButton",  0)
        }

        lengthButton.setOnClickListener {
            loadUnitTable("lengthButton",  1)
        }

        temperatureButton.setOnClickListener {
            conversionLogicUnit.conversion(2, currentTop, currentBottom)
            Log.d("Admin", "Array  ${conversionLogicUnit.conversionValue}")
            loadUnitTable("temperatureButton",  2)
        }

        volumeButton.setOnClickListener {
            loadUnitTable("volumeButton", 3)
        }

        massButton.setOnClickListener {
            loadUnitTable("massButton",4)
        }

        dataButton.setOnClickListener {
            loadUnitTable("dataButton", 5)
        }

        speedButton.setOnClickListener {
            loadUnitTable("speedButton", 6)
        }

        timeButton.setOnClickListener {
            conversionLogicUnit.conversion(7, currentTop, currentBottom)
            Log.d("Admin", "Array  ${conversionLogicUnit.conversionValue}")
            loadUnitTable("timeButton", 7)
        }
    }
}
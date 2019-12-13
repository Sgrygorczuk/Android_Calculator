package com.example.calculator

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_conversion.*
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.spinnertest.SpinnerAdapter


class ConversionActivity: AppCompatActivity() {

    /*
    Tables used to fill out the Spinners and the Text boxes
        Type Tables are for spinners
        Unit tables are for the text boxes
    */
    private val areaTypes = listOf(SpinnerItem(GONE, "Acre (ac)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Are (a)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Hectare (ha)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Square Centimeter (cm2)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Square Foot (ft2)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Square Inch (in2)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Square Meter (m2)", "#FFFFFF"))
    private val areaUnits = arrayOf("ac","a","ha","cm2","ft2","in2","m2")

    private val lengthTypes = listOf(SpinnerItem(GONE, "Millimeter (mm)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Centimeter (cm)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Meter (m)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Kilometer (km)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Inch (in)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Foot (ft)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Yard (yard)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Mile (mi)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Nautical Mile (NM)", "#FFFFFF"),
                                      SpinnerItem(GONE, "Mil (mil)", "#FFFFFF"))
    private val lengthUnits = arrayOf("mm", "cm", "m", "km", "in", "ft", "yard", "mi", "NM", "mil")

    private val temperatureTypes = listOf(SpinnerItem(GONE, "Celsius (°C)","#FFFFFF"),
                                          SpinnerItem(GONE, "Fahrenheit (°F)", "#FFFFFF"),
                                          SpinnerItem(GONE, "Kelvin (K)", "#FFFFFF"))
    private val temperatureUnits = arrayOf("°C","°F","K")

    private val volumeTypes = listOf(SpinnerItem(GONE, "UK Gallon (gal)","#FFFFFF"),
                                     SpinnerItem(GONE, "US Gallon (gal)","#FFFFFF"),
                                     SpinnerItem(GONE, "Liter (L)","#FFFFFF"),
                                     SpinnerItem(GONE, "Milliliter (mL)","#FFFFFF"),
                                     SpinnerItem(GONE, "Cubic Centimeter (cm3)","#FFFFFF"),
                                     SpinnerItem(GONE, "Cubic Meter (m3)","#FFFFFF"),
                                     SpinnerItem(GONE, "Cubic Inch (in3)","#FFFFFF"),
                                     SpinnerItem(GONE, "Cubic Foot (ft3)","#FFFFFF"))
    private val volumeUnits = arrayOf("gal", "gal", "L", "mL", "cm3", "m3", "in3", "ft3")

    private val massTypes = listOf(SpinnerItem(GONE, "Ton (t)","#FFFFFF"),
                                   SpinnerItem(GONE, "UK Ton (t)","#FFFFFF"),
                                   SpinnerItem(GONE, "US Ton (t)","#FFFFFF"),
                                   SpinnerItem(GONE, "Pound (lb)","#FFFFFF"),
                                   SpinnerItem(GONE, "Ounce (oz)","#FFFFFF"),
                                   SpinnerItem(GONE, "Kilogram (kg)","#FFFFFF"),
                                   SpinnerItem(GONE, "Gram (g)","#FFFFFF"))
    private val massUnits = arrayOf("t","t","t","lb","oz","kg","g")

    private val dataTypes = listOf(SpinnerItem(GONE, "Bit (bit)", "#FFFFFF"),
                                   SpinnerItem(GONE, "Byte (B)","#FFFFFF"),
                                   SpinnerItem(GONE, "Kilobyte (KB)","#FFFFFF"),
                                   SpinnerItem(GONE, "Megabyte (MB)","#FFFFFF"),
                                   SpinnerItem(GONE, "Gigabyte (GM)","#FFFFFF"),
                                   SpinnerItem(GONE, "Terabyte (TB)","#FFFFFF"))
    private val dataUnits = arrayOf("bit", "B", "KB", "MB", "GB", "TB")

    private val speedTypes = listOf(SpinnerItem(GONE, "Meters per Second (m/s)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Meters per Hour (m/h)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Kilometers per Second (km/s)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Kilometers per Hour (km/h)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Inches per Second (in/s)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Inches per Hour (in/h)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Feet per Second (ft/s)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Feet per Hour (ft/h)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Miles per Second (mi/s)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Miles per Hour (mi/h)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Knots (kn)", "#FFFFFF"))
    private val speedUnits = arrayOf("m/s", "m/h", "km/s", "km/h", "in/s", "in/h", "ft/s", "ft/h", "mi/s", "mi/h", "kn")

    private val timeTypes = listOf(SpinnerItem(GONE, "Milliseconds (ms)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Seconds (s)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Minutes (m)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Hours (h)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Days (d)", "#FFFFFF"),
                                    SpinnerItem(GONE, "Weeks (wk)", "#FFFFFF"))
    private val timeUnits = arrayOf("ms", "s", "min", "h", "d", "wk")

    /*
    Initialization of all the of variables update the UI
    */
    private var conversionLogicUnit = ConversionLogic() //Initializes the Logic Class
    private var isTop: Boolean = true                   //Keeps track of if we are focused on the top or bottom editTextBox
    private var currentValueTop : String = "1"          //Keeps track of the value of the top editTextBox
    private var currentValueBottom : String = "1"       //Keeps track of the value of the bottom editTextBox
    private var currentButton : String = "areaButton"   //Keeps track of what is the string of current button (Same as currentLayoutPosition?)
    private var currentLayoutPosition : Int = 0         //Keeps track of which layout we are looking at
    private var currentPositionTop : Int = 0            //Keeps track of which top spinner is selected on current layout
    private var currentPositionBottom : Int = 0         //Keeps track of which bottom spinner is selected on current layout
    private var currentPositionsSpinner = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) //Keeps track of each spinner selection for each layout
    private var oldPositionsSpinner = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) //Keeps track of each spinner selection for each layout
    /*
    Input: Void
    Output: Void
    Purpose: Deselects all of the layout buttons and set all of the layouts to GONE
             Highlights the clicked on layout button and brings up that layout
    */
    private fun updateLayout(){
        Log.d("Admin", "ConversionActivity: $currentButton layout was chosen")

        var newButton : View = areaButton
        var newPosition = 0
        var distance = 0

        areaButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        lengthButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        temperatureButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        volumeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        massButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        dataButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        speedButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        timeButton.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))

        when (currentButton) {
            "areaButton" -> {
                newButton = areaButton
                newPosition = 0
                distance = 0
            }
            "lengthButton" -> {
                newButton = lengthButton
                newPosition = 1
                distance = 0
            }
            "temperatureButton" -> {
                newButton = temperatureButton
                newPosition = 2
                distance = 30
            }
            "volumeButton" -> {
                newButton = volumeButton
                newPosition = 3
                distance = 350
            }
            "massButton" -> {
                newButton = massButton
                newPosition = 4
                distance = 600
            }
            "dataButton" -> {
                newButton = dataButton
                newPosition = 5
                distance = 750
            }
            "speedButton" -> {
                newButton = speedButton
                newPosition = 6
                distance = 850
            }
            "timeButton" -> {
                newButton = timeButton
                newPosition = 7
                distance = 850
            }
        }
        newButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
        animateLayoutTransition(newPosition, distance)
    }

    private fun animateLayoutTransition(position : Int, scrollDistance : Int){
        val distance : Float = conversionLayoutArea.width.toFloat()*(currentLayoutPosition-position)
        val time : Long = 200

        conversionLayoutArea.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutLength.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutTemperature.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutVolume.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutMass.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutData.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutSpeed.animate()
            .translationXBy(distance)
            .duration = time

        conversionLayoutTime.animate()
            .translationXBy(distance)
            .duration = time

        Log.d("Admin", "Area: ${conversionLayoutArea.isVisible} Length: ${conversionLayoutLength.isVisible}")
        unitScroll.smoothScrollTo(scrollDistance,0)

        currentLayoutPosition = position
    }

    /*
    Input: Void
    Output: Void
    Purpose: When switching between layouts moves focus to editTextBox to current layout
    */
    private fun inputFocus(){
        Log.d("Admin", "ConversionActivity: editTextBox focus updated")
        if(isTop)
        {
            when (currentButton) {
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
            when (currentButton) {
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

    /*
    Input: Void
    Output: Void
    Purpose: Turns on and off the +/- button based on which layout we are looking at
    */
    private fun plusMinusAble(){
        Log.d("Admin", "ConversionActivity: +/- button updated")
        if(currentLayoutPosition == 2){
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

    /*
    Input: Void
    Output: Void
    Purpose: Turns on and off the the Up and Down buttons based on which editTextBox we are focused on
    */
    private fun upDownArrows(){
        Log.d("Admin", "ConversionActivity: editTextBox buttons updated")
        if(isTop){
            bottomButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_selector))
            bottomButton.setImageResource(R.drawable.ic_down_on)
            bottomButton.isEnabled = true

            topButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_disabled_button))
            topButton.setImageResource(R.drawable.ic_up_off)
            topButton.isEnabled = false
        }
        else{
            topButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_selector))
            topButton.setImageResource(R.drawable.ic_up_on)
            topButton.isEnabled = true

            bottomButton.setBackgroundDrawable(resources.getDrawable(R.drawable.gray_disabled_button))
            bottomButton.setImageResource(R.drawable.ic_down_off)
            bottomButton.isEnabled = false
        }
    }

    /*
    Input: Takes in buttonName which is the name of button that was pressed
           we also take the position to tell which layout we are on.
    Output: Void
    Purpose: This is the function that resets the info and moves to the new layout
    */
    private fun loadUnitTable(buttonName : String){
        Log.d("Admin", "ConversionActivity: $buttonName was clicked")
        currentButton = buttonName
        updateLayout()
        inputFocus()
        plusMinusAble()
        upDownArrows()
        currentValueTop = "1"
        passToLogic()
    }

    /*
    Input: The current View (?)
    Output: Void
    Purpose: Updates which editTextBox we are looking at using the Button and updates the UI
    */
    fun updateLocationTop(view : View){
        Log.d("Admin", "ConversionActivity: Top text box was clicked")
        isTop = true
        upDownArrows()
        inputFocus()
    }

    /*
    Input: The current View (?)
    Output: Void
    Purpose: Updates which editTextBox we are looking at using the Button and updates the UI
    */
    fun updateLocationBottom(view : View){
        Log.d("Admin", "ConversionActivity: Bottom text box was clicked")
        isTop = false
        upDownArrows()
        inputFocus()
    }

    /*
    Input: Void
    Output: Void
    Purpose: Tell the user that they reach max length input using Toast
    */
    private fun showToast(){
        if (conversionLogicUnit.isMaxLength(currentValueTop) && isTop || conversionLogicUnit.isMaxLength(currentValueBottom) && !isTop) {
            val toast = Toast.makeText(this@ConversionActivity, "You reached the max input of 9", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    /*
    Input: errorMsg for the debugger to see which button was clicked
       choice is passed into the logicUnit to perform addChar() function
    Output: Void
    Purpose: Input any new numbers or decimals to the input
    */
    private fun clickedNumberButton(errorMsg : String, choice : String){
        Log.d("Admin", "ConversionActivity: $errorMsg was clicked, choice : $choice")
        updateChar(choice)
        showToast()
        passToLogic()
    }

    /*
    Input: Choice is passed into the conversionLogicUnit to perform addChar() function
    Output: Void
    Purpose: Update the current string to add or subtract chars
    */
    private fun updateChar(choice : String){
        if(isTop){
            currentValueTop = conversionLogicUnit.addChar(currentValueTop, choice)
            when (currentButton) {
                "areaButton" -> topTextEditorArea.setText(currentValueTop)
                "lengthButton" -> topTextEditorLength.setText(currentValueTop)
                "temperatureButton" -> topTextEditorTemperature.setText(currentValueTop)
                "volumeButton" -> topTextEditorVolume.setText(currentValueTop)
                "massButton" -> topTextEditorMass.setText(currentValueTop)
                "dataButton" -> topTextEditorData.setText(currentValueTop)
                "speedButton" -> topTextEditorSpeed.setText(currentValueTop)
                "timeButton" -> topTextEditorTime.setText(currentValueTop)
            }
        }
        else {
            currentValueBottom = conversionLogicUnit.addChar(currentValueBottom, choice)
            when (currentButton) {
                "areaButton" -> bottomTextEditorArea.setText(currentValueBottom)
                "lengthButton" -> bottomTextEditorLength.setText(currentValueBottom)
                "temperatureButton" -> bottomTextEditorTemperature.setText(currentValueBottom)
                "volumeButton" -> bottomTextEditorVolume.setText(currentValueBottom)
                "massButton" -> bottomTextEditorMass.setText(currentValueBottom)
                "dataButton" -> bottomTextEditorData.setText(currentValueBottom)
                "speedButton" -> bottomTextEditorSpeed.setText(currentValueBottom)
                "timeButton" -> bottomTextEditorTime.setText(currentValueBottom)
            }
        }
    }

    /*
    Input: Void
    Output: Void
    Purpose: Grabs the current top and bottom values from the editTextBoxes
             Passes them to the conversionLogicUnit to perform the conversion
             Then updates the editTextBoxes
    */
    private fun passToLogic(){
        when (currentButton) {
            "areaButton" -> {
                currentPositionTop = currentPositionsSpinner[0]
                currentPositionBottom = currentPositionsSpinner[1]
                currentValueTop = topTextEditorArea.text.toString()
                currentValueBottom = bottomTextEditorArea.text.toString()
            }
            "lengthButton" -> {
                currentPositionTop = currentPositionsSpinner[2]
                currentPositionBottom = currentPositionsSpinner[3]
                currentValueTop = topTextEditorLength.text.toString()
                currentValueBottom = bottomTextEditorLength.text.toString()
            }
            "temperatureButton" -> {
                currentPositionTop = currentPositionsSpinner[4]
                currentPositionBottom = currentPositionsSpinner[5]
                currentValueTop = topTextEditorTemperature.text.toString()
                currentValueBottom = bottomTextEditorTemperature.text.toString()
            }
            "volumeButton" -> {
                currentPositionTop = currentPositionsSpinner[6]
                currentPositionBottom = currentPositionsSpinner[7]
                currentValueTop = topTextEditorVolume.text.toString()
                currentValueBottom = bottomTextEditorVolume.text.toString()
            }
            "massButton" -> {
                currentPositionTop = currentPositionsSpinner[8]
                currentPositionBottom = currentPositionsSpinner[9]
                currentValueTop = topTextEditorMass.text.toString()
                currentValueBottom = bottomTextEditorMass.text.toString()
            }
            "dataButton" ->{
                currentPositionTop = currentPositionsSpinner[10]
                currentPositionBottom = currentPositionsSpinner[11]
                currentValueTop = topTextEditorData.text.toString()
                currentValueBottom = bottomTextEditorData.text.toString()
            }
            "speedButton" ->{
                currentPositionTop = currentPositionsSpinner[12]
                currentPositionBottom = currentPositionsSpinner[13]
                currentValueTop = topTextEditorSpeed.text.toString()
                currentValueBottom = bottomTextEditorSpeed.text.toString()
            }
            "timeButton" -> {
                currentPositionTop = currentPositionsSpinner[14]
                currentPositionBottom = currentPositionsSpinner[15]
                currentValueTop = topTextEditorTime.text.toString()
                currentValueBottom = bottomTextEditorTime.text.toString()
            }
        }
        if(isTop){
            currentValueBottom = conversionLogicUnit.conversion(currentLayoutPosition, currentPositionTop, currentPositionBottom, currentValueTop, isTop)
            when (currentButton) {
                "areaButton" -> bottomTextEditorArea.text = currentValueBottom
                "lengthButton" -> bottomTextEditorLength.setText(currentValueBottom)
                "temperatureButton" -> bottomTextEditorTemperature.setText(currentValueBottom)
                "volumeButton" -> bottomTextEditorVolume.setText(currentValueBottom)
                "massButton" -> bottomTextEditorMass.setText(currentValueBottom)
                "dataButton" -> bottomTextEditorData.setText(currentValueBottom)
                "speedButton" -> bottomTextEditorSpeed.setText(currentValueBottom)
                "timeButton" -> bottomTextEditorTime.setText(currentValueBottom)
            }
        }
        else{
            currentValueTop = conversionLogicUnit.conversion(currentLayoutPosition, currentPositionTop, currentPositionBottom, currentValueBottom, isTop)
            when (currentButton) {
                "areaButton" -> topTextEditorArea.setText(currentValueTop)
                "lengthButton" -> topTextEditorLength.setText(currentValueTop)
                "temperatureButton" -> topTextEditorTemperature.setText(currentValueTop)
                "volumeButton" -> topTextEditorVolume.setText(currentValueTop)
                "massButton" -> topTextEditorMass.setText(currentValueTop)
                "dataButton" -> topTextEditorData.setText(currentValueTop)
                "speedButton" -> topTextEditorSpeed.setText(currentValueTop)
                "timeButton" -> topTextEditorTime.setText(currentValueTop)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conversion)
        //Sets the Area button to be selected as that's the screen we start from
        loadUnitTable("areaButton")

        /*
        Set up and Functionality of the Spinners
            First 3 Lines connect the spinner to the array that will be displayed in the drop down menu
            Then we have the object that will handle the clicking and selecting that will change information
            in the text boxes
         */

        /*
        Top Area Spinner
         */
        topSpinnerArea.adapter = SpinnerAdapter(this, areaTypes)

        topSpinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                areaTypes[oldPositionsSpinner[0]].color = "#FFFFFF"
                areaTypes[oldPositionsSpinner[0]].visibility = GONE

                areaTypes[position].color = "#00C604"
                areaTypes[position].visibility = VISIBLE

                topTextViewArea.text = areaUnits[position]
                currentPositionsSpinner[0] = position
                passToLogic()

                oldPositionsSpinner[0] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Area Spinner
        */
        bottomSpinnerArea.adapter = SpinnerAdapter(this, areaTypes)

        bottomSpinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                areaTypes[oldPositionsSpinner[1]].color = "#FFFFFF"
                areaTypes[oldPositionsSpinner[1]].visibility = GONE

                areaTypes[position].color = "#00C604"
                areaTypes[position].visibility = VISIBLE

                bottomTextViewArea.text = areaUnits[position]
                currentPositionsSpinner[1] = position
                passToLogic()

                oldPositionsSpinner[1] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Length Spinner
        */
        topSpinnerLength.adapter = SpinnerAdapter(this, lengthTypes)

        topSpinnerLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                lengthTypes[oldPositionsSpinner[2]].color = "#FFFFFF"
                lengthTypes[oldPositionsSpinner[2]].visibility = GONE

                lengthTypes[position].color = "#00C604"
                lengthTypes[position].visibility = VISIBLE

                topTextViewLength.text = lengthUnits[position]
                currentPositionsSpinner[2] = position
                passToLogic()

                oldPositionsSpinner[2] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Length Spinner
        */
        bottomSpinnerLength.adapter = SpinnerAdapter(this, lengthTypes)

        bottomSpinnerLength.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                lengthTypes[oldPositionsSpinner[3]].color = "#FFFFFF"
                lengthTypes[oldPositionsSpinner[3]].visibility = GONE

                lengthTypes[position].color = "#00C604"
                lengthTypes[position].visibility = VISIBLE

                bottomTextViewLength.text = lengthUnits[position]
                currentPositionsSpinner[3] = position
                passToLogic()

                oldPositionsSpinner[3] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Temperature Spinner
        */
        topSpinnerTemperature.adapter = SpinnerAdapter(this, temperatureTypes)

        topSpinnerTemperature.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                temperatureTypes[oldPositionsSpinner[4]].color = "#FFFFFF"
                temperatureTypes[oldPositionsSpinner[4]].visibility = GONE

                temperatureTypes[position].color = "#00C604"
                temperatureTypes[position].visibility = VISIBLE

                topTextViewTemperature.text = temperatureUnits[position]
                currentPositionsSpinner[4] = position
                passToLogic()

                oldPositionsSpinner[4] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Temperature Spinner
        */
        bottomSpinnerTemperature.adapter = SpinnerAdapter(this, temperatureTypes)

        bottomSpinnerTemperature.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                temperatureTypes[oldPositionsSpinner[5]].color = "#FFFFFF"
                temperatureTypes[oldPositionsSpinner[5]].visibility = GONE

                temperatureTypes[position].color = "#00C604"
                temperatureTypes[position].visibility = VISIBLE

                bottomTextViewTemperature.text = temperatureUnits[position]
                currentPositionsSpinner[5] = position
                passToLogic()

                oldPositionsSpinner[5] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Volume Spinner
        */
        topSpinnerVolume.adapter = SpinnerAdapter(this, volumeTypes)

        topSpinnerVolume.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                volumeTypes[oldPositionsSpinner[6]].color = "#FFFFFF"
                volumeTypes[oldPositionsSpinner[6]].visibility = GONE

                volumeTypes[position].color = "#00C604"
                volumeTypes[position].visibility = VISIBLE

                topTextViewVolume.text = volumeUnits[position]
                currentPositionsSpinner[6] = position
                passToLogic()

                oldPositionsSpinner[6] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Volume Spinner
        */
        bottomSpinnerVolume.adapter = SpinnerAdapter(this, volumeTypes)

        bottomSpinnerVolume.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                volumeTypes[oldPositionsSpinner[7]].color = "#FFFFFF"
                volumeTypes[oldPositionsSpinner[7]].visibility = GONE

                volumeTypes[position].color = "#00C604"
                volumeTypes[position].visibility = VISIBLE

                bottomTextViewVolume.text = volumeUnits[position]
                currentPositionsSpinner[7] = position
                passToLogic()

                oldPositionsSpinner[7] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Mass Spinner
        */
        topSpinnerMass.adapter = SpinnerAdapter(this, massTypes)

        topSpinnerMass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                massTypes[oldPositionsSpinner[8]].color = "#FFFFFF"
                massTypes[oldPositionsSpinner[8]].visibility = GONE

                massTypes[position].color = "#00C604"
                massTypes[position].visibility = VISIBLE

                topTextViewMass.text = massUnits[position]
                currentPositionsSpinner[8] = position
                passToLogic()

                oldPositionsSpinner[8] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Mass Spinner
        */
        bottomSpinnerMass.adapter = SpinnerAdapter(this, massTypes)

        bottomSpinnerMass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                massTypes[oldPositionsSpinner[9]].color = "#FFFFFF"
                massTypes[oldPositionsSpinner[9]].visibility = GONE

                massTypes[position].color = "#00C604"
                massTypes[position].visibility = VISIBLE

                bottomTextViewMass.text = massUnits[position]
                currentPositionsSpinner[9] = position
                passToLogic()

                oldPositionsSpinner[9] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Data Spinner
        */
        topSpinnerData.adapter = SpinnerAdapter(this, dataTypes)

        topSpinnerData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                dataTypes[oldPositionsSpinner[10]].color = "#FFFFFF"
                dataTypes[oldPositionsSpinner[10]].visibility = GONE

                dataTypes[position].color = "#00C604"
                dataTypes[position].visibility = VISIBLE

                topTextViewData.text = dataUnits[position]
                currentPositionsSpinner[10] = position
                passToLogic()

                oldPositionsSpinner[10] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Data Spinner
        */
        bottomSpinnerData.adapter = SpinnerAdapter(this, dataTypes)

        bottomSpinnerData.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                dataTypes[oldPositionsSpinner[11]].color = "#FFFFFF"
                dataTypes[oldPositionsSpinner[11]].visibility = GONE

                dataTypes[position].color = "#00C604"
                dataTypes[position].visibility = VISIBLE

                bottomTextViewData.text = dataUnits[position]
                currentPositionsSpinner[11] = position
                passToLogic()

                oldPositionsSpinner[11] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Top Speed Spinner
        */
        topSpinnerSpeed.adapter = SpinnerAdapter(this, speedTypes)

        topSpinnerSpeed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                speedTypes[oldPositionsSpinner[12]].color = "#FFFFFF"
                speedTypes[oldPositionsSpinner[12]].visibility = GONE

                speedTypes[position].color = "#00C604"
                speedTypes[position].visibility = VISIBLE

                topTextViewSpeed.text = speedUnits[position]
                currentPositionsSpinner[12] = position
                passToLogic()

                oldPositionsSpinner[12] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Speed Spinner
        */
        bottomSpinnerSpeed.adapter = SpinnerAdapter(this, speedTypes)

        bottomSpinnerSpeed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                speedTypes[oldPositionsSpinner[13]].color = "#FFFFFF"
                speedTypes[oldPositionsSpinner[13]].visibility = GONE

                speedTypes[position].color = "#00C604"
                speedTypes[position].visibility = VISIBLE

                bottomTextViewSpeed.text = speedUnits[position]
                currentPositionsSpinner[13] = position
                passToLogic()

                oldPositionsSpinner[13] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        /*
        Top Time Spinner
        */
        topSpinnerTime.adapter = SpinnerAdapter(this, timeTypes)

        topSpinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                timeTypes[oldPositionsSpinner[14]].color = "#FFFFFF"
                timeTypes[oldPositionsSpinner[14]].visibility = GONE

                timeTypes[position].color = "#00C604"
                timeTypes[position].visibility = VISIBLE

                topTextViewTime.text = timeUnits[position]
                currentPositionsSpinner[14] = position
                passToLogic()

                oldPositionsSpinner[14] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Bottom Time Spinner
        */
        bottomSpinnerTime.adapter = SpinnerAdapter(this, timeTypes)

        bottomSpinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                view?.setBackgroundResource(R.drawable.spinner_unpressed)

                timeTypes[oldPositionsSpinner[15]].color = "#FFFFFF"
                timeTypes[oldPositionsSpinner[15]].visibility = GONE

                timeTypes[position].color = "#00C604"
                timeTypes[position].visibility = VISIBLE

                bottomTextViewTime.text = timeUnits[position]
                currentPositionsSpinner[15] = position
                passToLogic()

                oldPositionsSpinner[15] = position
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        /*
        Disabling the keyboard when selecting the textEditors - No Longer Used
        //Area
        topTextEditorArea!!.setRawInputType(InputType.TYPE_NULL)
        topTextEditorArea!!.isFocusable = true
        topTextEditorArea!!.setTextIsSelectable(true)
        */

        backButton.setOnClickListener {
            Log.d("Admin", "ConversionActivity: backButton was clicked")
            startActivity(Intent(this, MainActivity::class.java))
        }

        areaButton.setOnClickListener {
            loadUnitTable("areaButton")
        }

        lengthButton.setOnClickListener {
            loadUnitTable("lengthButton")
        }

        temperatureButton.setOnClickListener {
            loadUnitTable("temperatureButton")
        }

        volumeButton.setOnClickListener {
            loadUnitTable("volumeButton")
        }

        massButton.setOnClickListener {
            loadUnitTable("massButton")
        }

        dataButton.setOnClickListener {
            loadUnitTable("dataButton")
        }

        speedButton.setOnClickListener {
            loadUnitTable("speedButton")
        }

        timeButton.setOnClickListener {
            loadUnitTable("timeButton")
        }

        /*
        The string forming buttons all use the clickedNumberButton() function
        */
        nineButton.setOnClickListener {
            clickedNumberButton("nineButton", "9")
        }

        eightButton.setOnClickListener {
            clickedNumberButton("eightButton", "8")
        }

        sevenButton.setOnClickListener {
            clickedNumberButton("sevenButton", "7")
        }

        sixButton.setOnClickListener {
            clickedNumberButton("sixButton", "6")
        }

        fiveButton.setOnClickListener {
            clickedNumberButton("fiveButton", "5")
        }

        fourButton.setOnClickListener {
            clickedNumberButton("fourButton", "4")
        }

        threeButton.setOnClickListener {
            clickedNumberButton("threeButton", "3")
        }

        twoButton.setOnClickListener {
            clickedNumberButton("twoButton", "2")
        }

        oneButton.setOnClickListener {
            clickedNumberButton("oneButton", "1")
        }

        zeroButton.setOnClickListener {
            clickedNumberButton("zeroButton", "0")
        }

        decimalButton.setOnClickListener {
            clickedNumberButton("decimalButton", ".")
        }

        deleteButton.setOnClickListener {
            clickedNumberButton("deleteButton", "d")
        }

        clearButton.setOnClickListener {
            clickedNumberButton("clearButton", "c")
        }

        /*
        The negButton will place or remove '-' from the input
        */
        plusMinusButton.setOnClickListener {
            Log.d("Admin", "ConversionActivity: plusMinusButton was clicked")
            if(isTop){
                currentValueTop = conversionLogicUnit.negation(currentValueTop)
                topTextEditorTemperature.setText(currentValueTop)}
            else{
                currentValueBottom = conversionLogicUnit.negation(currentValueBottom)
                bottomTextEditorTemperature.setText(currentValueBottom)}
            passToLogic()
        }
    }
}
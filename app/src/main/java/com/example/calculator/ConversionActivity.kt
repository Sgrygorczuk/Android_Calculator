package com.example.calculator

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_conversion.*
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.view.isVisible


class ConversionActivity: AppCompatActivity() {

    /*
    Tables used to fill out the Spinners and the Textboxes
        Type Tables are for spinners
        Unit tables are for the textboxes
    */
    private val areaTypes = arrayOf("Acre (ac)","Are (a)","Hectare (ha)","Square Centimeter (cm2)", "Square Foot (ft2)", "Square Inch (in2)", "Square Meter (m2)")
    private val areaUnits = arrayOf("ac","a","ha","cm2","ft2","in2","m2")
    private val lengthTypes = arrayOf("Millimeter (mm)", "Centimeter (cm)", "Meter (m)", "Kilometer (km)", "Inch (in)", "Foot (ft)", "Yard (yard)", "Mile (mi)", "Nautical Mile (NM)", "Mil (mil)")
    private val lengthUnits = arrayOf("mm", "cm", "m", "km", "in", "ft", "yard", "mi", "NM", "mil")
    private val temperatureTypes = arrayOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)")
    private val temperatureUnits = arrayOf("°C","°F","K")
    private val volumeTypes = arrayOf("UK Gallon (gal)", "US Gallon (gal)", "Liter (L)", "Milliliter (mL)", "Cubic Centimeter (cm3)", "Cubic Meter (m3)", "Cubic Inch (in3)", "Cubic Foot (ft3)")
    private val volumeUnits = arrayOf("gal", "gal", "L", "mL", "cm3", "m3", "in3", "ft3")
    private val massTypes = arrayOf("Ton (t)", "UK Ton (t)", "US Ton (t)", "Pound (lb)", "Ounce (oz)", "Kilogram (kg)", "Gram (g)")
    private val massUnits = arrayOf("t","t","t","lb","oz","kg","g")
    private val dataTypes = arrayOf("Bit (bit)", "Byte (B)", "Kilobyte (KB)", "Megabyte (MB)", "Gigabyte (GM)", "Terabyte (TB)")
    private val dataUnits = arrayOf("bit", "B", "KB", "MB", "GB", "TB")
    private val speedTypes = arrayOf("Meters per Second (m/s)", "Meters per Hour (m/h)", "Kilometers per Second (km/s)", "Kilometers per Hour (km/h)",
        "Inches per Second (in/s)", "Inches per Hour (in/h)", "Feet per Second (ft/s)", "Feet per Hour (ft/h)", "Miles per Second (mi/s)", "Miles per Hour (mi/h)",
        "Knots (k)")
    private val speedUnits = arrayOf("m/s", "m/h", "km/s", "km/h", "in/s", "in/h", "ft/s", "ft/h", "mi/s", "mi/h", "kn")
    private val timeTypes = arrayOf("Milliseconds (ms)", "Seconds (s)", "Minutes (m)", "Hours (h)", "Days (d)", "Weeks (wk)")
    private val timeUnits = arrayOf("ms", "s", "min", "h", "d", "wk")

    /*
    Initlization of all the of variables update the UI
    */
    private var conversionLogicUnit = ConversionLogic() //Initlizes the Logic Class
    private var isTop: Boolean = true                   //Keeps track of if we are focused on the top or bottom editTextBox
    private var currentValueTop : String = "1"          //Keeps track of the value of the top editTextBox
    private var currentValueBottom : String = "1"       //Keeps track of the value of the bottom editTextBox
    private var currentButton : String = "areaButton"   //Keeps track of what is the string of current button (Same as currentLayoutPosition?)
    private var currentLayoutPosition : Int = 0         //Keeps track of which layout we are looking at
    private var currentPositionTop : Int = 0            //Keeps track of which top spinner is setlecte on current layout
    private var currentPositionBottom : Int = 0         //Keeps track of which bottom spinner is setlecte on current layout
    private var currentPositionsSpinner = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0) //Keeps track of each spinner selection for each layout
    
    /*
    Input: Void
    Output: Void
    Purpose: Deselects all of the layout buttons and set all of the layouts to GONE
             Highlights the clicked on layout button and brings up that layout
    */
    private fun updateLayout(){
        Log.d("Admin", "ConversionActivity: $currentButton layout was chosen")

        var newButton : View = areaButton
        var newPosition : Int = 0
        var distance : Int = 0

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

    fun animateLayoutTransition(position : Int, scrollDistance : Int){
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
        showToast()
        updateChar(choice)
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
                currentValueTop = topTextEditorSpeed.toString()
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
        val areaTop = ArrayAdapter(this, android.R.layout.simple_spinner_item, areaTypes)
        areaTop.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        topSpinnerArea!!.adapter = areaTop

        topSpinnerArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                topTextViewArea.text = areaUnits[position]
                currentPositionsSpinner[0] = position
                passToLogic()
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
                currentPositionsSpinner[1] = position
                passToLogic()
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
                currentPositionsSpinner[2] = position
                passToLogic()
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
                currentPositionsSpinner[3] = position
                passToLogic()
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
                currentPositionsSpinner[4] = position
                passToLogic()
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
                currentPositionsSpinner[5] = position
                passToLogic()
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
                currentPositionsSpinner[6] = position
                passToLogic()
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
                currentPositionsSpinner[7] = position
                passToLogic()
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
                currentPositionsSpinner[8] = position
                passToLogic()
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
                currentPositionsSpinner[9] = position
                passToLogic()
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
                currentPositionsSpinner[10] = position
                passToLogic()
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
                currentPositionsSpinner[11] = position
                passToLogic()
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
                currentPositionsSpinner[12] = position
                passToLogic()
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
                currentPositionsSpinner[13] = position
                passToLogic()
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
                currentPositionsSpinner[14] = position
                passToLogic()
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
                currentPositionsSpinner[15] = position
                passToLogic()
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
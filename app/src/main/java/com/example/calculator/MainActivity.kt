/*
MainLogic.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This class takes care of all of the button inputs
They're broken down into 5 types

1) String forming which deal with 0,1,2,3,4,5,6,7,8,9,.   -> Need to be implemented π,e
2) Operations inputs such as +,-,/,*, ->   Needs to be implemented ^
3) Mod inputs such as  % and  √
4) UI updating buttons such as equal, neg, clear
5) Activity switching buttons that move us to other acitivites or modes
 */

package com.example.calculator

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log.d
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

class MainActivity : AppCompatActivity() {

    /*
    Tables are used to identify what the input button was pressed
        char table keeps track of all of the possible character that a user might want to input
        operation table keeps track of all the possible two input operations a user might want to execute
        mod table keeps track of all of the one input operation a user might want to execute
    */
    private val charTable = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "π")
    private val operationTable = arrayOf("+", "-", "/", "*")
    private val modTable = arrayOf("%", "√", "sin", "cos", "tan", "ln", "log", "1/x", "e^x", "x^2", "x^y",
        "|x|", "e", "cbrt", "asin", "acos", "atan", "sinh", "cosh", "tanh", "asinh", "acosh", "atanh",
        "2^x", "x^3", "x!")

    //Keeps track of if we are in landscape or portrait mode
    private var orientation : String = ""

    //The object that performs all of the arithmetic that this screen requires
    private val logicUnit = MainLogic()

    /*
    Input: Void
    Output: Void
    Purpose: Updates the orientation variable to be Land or Port when a new activity is created
    */
    private fun isOrientation(){
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){orientation = "Land"}
        else if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){orientation = "Port"}
    }

    /*
    Buttons which take us from one activity to another or update the current mode of the current activity
    */

    /*
    Input: view (Button)
    Output: Void
    Purpose: Switches the screen's orientation from landscape to portrait or vice versa
    */
    fun orientationButton(view : View){
        d("Admin", "MainActivity ${view.tag} button was clicked")
        if(view.tag == "Port") {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        else if(view.tag == "Land"){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    /*
    Input: view (Button)
    Output: Void
    Purpose: Takes us to the conversion activity
    */
    fun conversionButton(view : View) {
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        startActivity(Intent(this, ConversionActivity::class.java))
    }

    /*
    Input: view (Button)
    Output: Void
    Purpose: Switches between the two sets of extra buttons in the scientific (landscape) main view
    */
    fun switchLayoutButton(view : View) {
        d("Admin", "MainActivity ($orientation): layout ${view.tag} button was clicked")
        if(view.tag.toString() == "One") {
            this.buttonLayoutExtraOne.visibility = GONE
            this.buttonLayoutExtraTwo.visibility = VISIBLE
        }
        else if(view.tag.toString() == "Two"){
            this.buttonLayoutExtraOne.visibility = VISIBLE
            this.buttonLayoutExtraTwo.visibility = GONE
        }
    }

    /*
    Toasts that tell the user they did something wrong
    */

    /*
    Input: Void
    Output: Void
    Purpose: Tell the user that they reach max length input using Toast
    */
    private fun showToast(){
        if (logicUnit.isMaxLength()) {
            val toast = Toast.makeText(this@MainActivity, "You reached the max input of 15", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }

    /*
    Input: resultString to check if the result of the current operation is either "NaN" or "Infinity"
    Output: Void
    Purpose: Tell the user that the action that they are attempting to perform is illegal with different
            text for different illegal action.
    */
    private fun errorToast(resultString : String){
        var outputString = ""
        if(resultString == "NaN") {outputString = "Can't show undefined result"}
        else if(resultString == "Infinity") {outputString = "Can't divide by zero"}
        val toast = Toast.makeText(this@MainActivity, outputString, Toast.LENGTH_SHORT)
        toast.show()
    }

    /*
    Buttons which control the actions of current activity
    */

    /*
    Input: view (Button) uses the tag to figure out which button is pressed then according to tag
        performs different function
    Output: Void
    Purpose: The main driving force behind most of the interaction with the current activity
             most of action buttons are connected to it.
             The function looks at current press compares it ot the tables and based on where
             the input fits it performs the action of adding a new char, operation or mod to current
             input.
             When that is done it updates the rest of the UI element based on whatever input was provided
    */
    fun clickedButton(view:View){
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        when {
            //Adds a new character to current string
            charTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor(logicUnit.operationWasPerformed()))
                calculatorView.text = logicUnit.addChar(view.tag.toString())
            }
            //Save current string, connect it to new string using chosen operation
            operationTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.operationSetUp(view.tag.toString())
            }
            //Put a one input operation around current string
            modTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.modSetUp(view.tag.toString())
            }
        }
        //Updates rest of UI
        updateUI()
        //Make sure we don't display or let user interact with "NaN" and "Infinity"
        val viewString : String = logicUnit.performOperation()
        if(viewString == "NaN" || viewString == "Infinity"){resultsView.text = ""}
        else{resultsView.text = viewString}
    }

    /*
    Input: view (Button)
    Output: Void
    Purpose: The negButton will place or remove '-' from the input
    */
    fun negButton (view:View){
        d("Admin", "MainActivity ($orientation):${view.tag} button was clicked")
        calculatorView.text = logicUnit.negation()
        updateUI()
        resultsView.text = logicUnit.performOperation()
    }

    /*
    Input: view (Button)
    Output: Void
    Purpose: The clearButton clears the data and screen
    */
    fun clearButton (view:View){
        d("Admin", "MainActivity ($orientation): ${view.tag} was clicked")
        logicUnit.clear()
        calculatorView.text = ""
        textAdjustment()
        delButtonUpdate()
        resultsView.text = logicUnit.performOperation()
    }

    /*
    Input: view (Button)
    Output: Void
    Purpose: The equalButton first checks if all the necessary data has been input
             and if it has performs the equal putting the output from result view into
             input in the calculator view
    */
    fun equalButton(view:View){
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        //Preps viewString to see if current result is valid
        val viewString : String = logicUnit.performOperation()
        //Checks if we have an operation that can be executed
        if (logicUnit.operationReady() || logicUnit.modReady()){
            //If we have an operation but the result is bad send a error toast
            if(viewString == "NaN" || viewString == "Infinity"){errorToast(viewString)}
            //If everything works we perform the operation
            else{
                calculatorView.text = logicUnit.performEqual()
                resultsView.text = ""
                //Performs the animationaniamtion
                equalAnimation()
                //After animation finishes we update the color of the text
                Handler().postDelayed({
                    calculatorView.setTextColor(Color.parseColor("#36C23B"))
                }, 310)
            }
        }
    }

    /*
    UI Updates and Animations
    */

    /*
    Input: Void
    Output: Void
    Purpose: Executes all of the functions that update different parts of the UI
    */
    private fun updateUI(){
        showToast()
        isOrientation()
        textAdjustment()
        delButtonUpdate()
    }

    /*
    Input: Void
    Output: Void
    Purpose: Updates the delete button to be on or off based on if there is input provided
             have to do separate updates for landscape and portrait activates
    */
    private fun delButtonUpdate(){
        d("Admin", "MainActivity ($orientation): button was clicked")
        when(orientation){
            "Port" -> {
                if (logicUnit.isEmpty()) {
                    deleteButtonOne.isEnabled = false
                    deleteButtonOne.setImageResource(R.drawable.ic_del_off)
                }
                else {
                    deleteButtonOne.isEnabled = true
                    deleteButtonOne.setImageResource(R.drawable.ic_del_on)
                }
            }
            "Land" -> {
                if (logicUnit.isEmpty()) {
                    deleteButtonTwo.isEnabled = false
                    deleteButtonTwo.setImageResource(R.drawable.ic_del_off)
                }
                else{
                    deleteButtonTwo.isEnabled = true
                    deleteButtonTwo.setImageResource(R.drawable.ic_del_on)
                }
            }
        }
    }

    /*
    Input: Void
    Output: Void
    Purpose: Changes the text to be smaller if enough chars are put in, only
             works in portrait view
    */
    private fun textAdjustment(){
        if(orientation == "Port"){
            if(logicUnit.isAdjustLength()){
                calculatorView.animate()
                    .scaleY(.9f)
                    .scaleX(.9f)
                    .translationY(-50f)
                    .translationX(70f)
                    .duration = 200
                calculatorView.textSize = 40F
                }
            else{
                calculatorView.animate()
                    .translationY(0f)
                    .translationX(0f)
                    .scaleY(1f)
                    .scaleX(1f)
                    .duration = 0
                calculatorView.textSize = 60F
            }
        }
    }

    /*
    Input: Void
    Output: Void
    Purpose: Performs an animation where the calculator view moves from result view back to
             its original position to give it a illusion that it moves from the output to input
    */
    private fun equalAnimation() {
        calculatorView.setTextColor(Color.parseColor("#6C6C6C"))
        calculatorView.bringToFront()
        calculatorView.translationY = 200f
        if(orientation == "Port"){ calculatorView.translationX =  270f} else if (orientation == "Land") {calculatorView.translationX = 440f}
        calculatorView.scaleY = 0.5f
        calculatorView.scaleX = 0.5f

        calculatorView.animate()
            .translationY(0f)
            .translationX(0f)
            .scaleY(1f)
            .scaleX(1f)
            .duration = 300
    }

    /*
    Input: Void
    Output: Void
    Purpose: Creates the activity
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        delButtonUpdate()
    }
}

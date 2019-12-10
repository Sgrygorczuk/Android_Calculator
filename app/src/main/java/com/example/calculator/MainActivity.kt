/*
MainLogic.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This class takes care of all of the button inputs
They're broken down into 4 types

1) String forming which deal with 0,1,2,3,4,5,6,7,8,9,.
2) Operations inputs such as +,-,/,*
3) Mod inputs such as  % and  √
4) Unique, such as clear and equal
 */

package com.example.calculator

import android.content.Intent
import android.content.pm.ActivityInfo
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

    private val charTable = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "π")
    private val operationTable = arrayOf("+", "-", "/", "*")
    private val modTable = arrayOf("%", "√", "sin", "cos", "tan", "ln", "log", "1/x", "e^x", "x^2", "x^y",
        "|x|", "e", "cbrt", "asin", "acos", "atan", "sinh", "cosh", "tanh", "asinh", "acosh", "atanh",
        "2^x", "x^3", "x!")

    private var orientation : String = "Port"
    //The
    private val logicUnit = MainLogic()

    fun orientationButton(view : View){
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        if(view.tag.toString() == "Port") {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            orientation = "Port"
        }
        else if(view.tag.toString() == "Land"){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            orientation = "Land"
        }
    }

    fun conversionButton(view : View) {
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        startActivity(Intent(this, ConversionActivity::class.java))
    }

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
    Input: Void
    Output: Void
    Purpose: Tell the user that they reach max length input using Toast
    */
    private fun errorToast(resultString : String){
        var outputString = ""
        if(resultString == "NaN") {outputString = "Can't show undefined result"}
        else if(resultString == "Infinity") {outputString = "Can't divide by zero"}
        val toast = Toast.makeText(this@MainActivity, outputString, Toast.LENGTH_SHORT)
        toast.show()
    }

    /*
    Input: errorMsg for the debugger to see which button was clicked
       choice is passed into the logicUnit to perform addChar() function
    Output: Void
    Purpose: Input any new numbers or decimals to the input
    */
    fun clickedButton(view:View){
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        showToast()
        when {
            charTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor(logicUnit.operationWasPerformed()))
                calculatorView.text = logicUnit.addChar(view.tag.toString())
            }
            operationTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.operationSetUp(view.tag.toString())
            }
            modTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.modSetUp(view.tag.toString())
            }
        }
        textAdjustment()
        resultsView.text = logicUnit.performOperation()
    }

    /*
    The negButton will place or remove '-' from the input
    */
    fun negButton (view:View){
        d("Admin", "MainActivity ($orientation):${view.tag} button was clicked")
        calculatorView.text = logicUnit.negation()
        resultsView.text = logicUnit.performOperation()
    }

    /*
    The clearButton clears the data and screen
    */
    fun clearButton (view:View){
        d("Admin", "MainActivity ($orientation): ${view.tag} was clicked")
        logicUnit.clear()
        calculatorView.text = ""
        resultsView.text = logicUnit.performOperation()
    }

    /*
    The equalButton first checks if all the necesasry data has been input
    and if it has performs the performEqual()
    */
    fun equalButton(view:View){
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        val viewString : String = logicUnit.performOperation()
        if (logicUnit.operationReady() || logicUnit.modReady()){
            if(viewString == "NaN" || viewString == "Infinity"){errorToast(viewString)}
            else{
                calculatorView.text = logicUnit.performEqual()
                resultsView.text = ""
                equalAnimation()
                Handler().postDelayed({
                    calculatorView.setTextColor(Color.parseColor("#36C23B"))
                }, 310)
            }
        }
    }

    private fun textAdjustment(){
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

    private fun equalAnimation() {
        calculatorView.setTextColor(Color.parseColor("#6C6C6C"))
        calculatorView.bringToFront()
        calculatorView.translationY = 200f
        calculatorView.translationX = 270f
        calculatorView.scaleY = 0.5f
        calculatorView.scaleX = 0.5f

        calculatorView.animate()
            .translationY(0f)
            .translationX(0f)
            .scaleY(1f)
            .scaleX(1f)
            .duration = 300
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

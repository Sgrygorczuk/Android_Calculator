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

import android.app.assist.AssistStructure
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
import android.view.Window

class MainActivity : AppCompatActivity() {

    fun longButton(view : View){
        d("Admin", "MainActivity: longButton was clicked")
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    fun shortButton(view : View){
        d("Admin", "MainActivity: longButton was clicked")
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    fun SwitchButtonOne(view : View) {
        d("Admin", "MainActivity: longButton was clicked")
        buttonLayoutExtraOne.visibility = GONE
        buttonLayoutExtraTwo.visibility = VISIBLE
    }

    fun SwitchButtonTwo(view : View) {
        d("Admin", "MainActivity: longButton was clicked")
        buttonLayoutExtraOne.visibility = VISIBLE
        buttonLayoutExtraTwo.visibility = GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //The
        val logicUnit = MainLogic()

        /*
        Input: Void
        Output: Void
        Purpose: Tell the user that they reach max length input using Toast
        */
        fun showToast(){
            if (logicUnit.isMaxLength()) {
                val toast = Toast.makeText(this@MainActivity, "You reached the max input of 9", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }
        }

        /*
        Input: Void
        Output: Void
        Purpose: Tell the user that they reach max length input using Toast
        */
        fun errorToast(resultString : String){
            var outputString : String = ""
            if(resultString == "NaN") {outputString = "Can't show undefined result"}
            else if(resultString == "Infinity") {outputString = "Can't divide by zero"}
            val toast = Toast.makeText(this@MainActivity, "$outputString", Toast.LENGTH_SHORT)
            toast.show()
        }

        /*
        Input: errorMsg for the debugger to see which button was clicked
               choice is passed into the logicUnit to perform addChar() function
        Output: Void
        Purpose: Input any new numbers or decimals to the input
        */
        fun clickedNumberButton(errorMsg : String, choice : String){
            d("Admin", "MainActivity: $errorMsg was clicked")
            showToast()
            calculatorView.setTextColor(Color.parseColor(logicUnit.operationWasPerformed()))
            calculatorView.text = logicUnit.addChar(choice)
            resultsView.text = logicUnit.performOperation()
        }

        /*
        Input: errorMsg for the debugger to see which button was clicked
            choice is passed into the logicUnit to perform operationSetUp function
        Output: Void
        Purpose: Input any new operations to the input
        */
        fun clickedOperationButton(errorMsg : String, choice : String){
            d("Admin", "MainActivity: $errorMsg was clicked")
            showToast()
            calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
            calculatorView.text = logicUnit.operationSetUp(choice)
            resultsView.text = logicUnit.performOperation()
        }

        /*
        Input: errorMsg for the debugger to see which button was clicked
            choice is passed into the logicUnit to perform modSetUp function
        Output: Void
        Purpose: Input any new operations to the input
        */
        fun clickedModButton(errorMsg : String, choice : String){
            d("Admin", "MainActivity: $errorMsg was clicked")
            showToast()
            calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
            calculatorView.text = logicUnit.modSetUp(choice)
            resultsView.text = logicUnit.performOperation()
        }

        fun equalAnimation() {
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

        /*
        Operation Buttons all use the clickedOperationButton() function
        */
        divButton.setOnClickListener {
            clickedOperationButton("divButton", "/")
        }

        timesButton.setOnClickListener {
            clickedOperationButton("timesButton", "*")
        }

        minusButton.setOnClickListener {
            clickedOperationButton("minusButton", "-")
        }

        plusButton.setOnClickListener {
            clickedOperationButton("plusButton", "+")
        }

        /*
        Mod Buttons all use the clickedModButton() function
         */
        radButton.setOnClickListener {
            clickedModButton("radButton","√")
        }

        perButton.setOnClickListener {
            clickedModButton("perButton","%")
        }

        /*
        Unique Buttons
        */

        /*
        The equalButton first checks if all the necesasry data has been input
        and if it has performs the performEqual()
        */
        equalButton.setOnClickListener {
            d("Admin", "MainActivity: equalButton was clicked")
            var viewString : String = logicUnit.performOperation()
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

        /*
        The clearButton clears the data and screen
         */
        clearButton.setOnClickListener {
            d("Admin", "MainActivity: clearButton was clicked")
            logicUnit.clear()
            calculatorView.text = ""
            resultsView.text = logicUnit.performOperation()
        }

        /*
        The negButton will place or remove '-' from the input
         */
        negButton.setOnClickListener {
            d("Admin", "MainActivity: negButton was clicked")
            calculatorView.text = logicUnit.negation()
            resultsView.text = logicUnit.performOperation()
        }

        conversionButton.setOnClickListener {
            d("Admin", "MainActivity: conversionButton was clicked")
            startActivity(Intent(this, ConversionActivity::class.java))
        }

    }
}

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
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.view.Gravity

class MainActivity : AppCompatActivity() {

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
        fun showToast() : Unit {
            if (logicUnit.isMaxLength()) {
                val toast = Toast.makeText(this@MainActivity, "You reached the max input of 9", Toast.LENGTH_SHORT)
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
        fun clickedNumberButton(errorMsg : String, choice : String) : Unit {
            d("Admin", "MainActivity: $errorMsg was clicked")
            showToast()
            calculatorView.setTextColor(Color.parseColor(logicUnit.operationWasPerformed()))
            calculatorView.text = logicUnit.addChar("$choice")
            resultsView.text = logicUnit.performOperation()
        }

        /*
        Input: errorMsg for the debugger to see which button was clicked
            choice is passed into the logicUnit to perform operationSetUp function
        Output: Void
        Purpose: Input any new operations to the input
        */
        fun clickedOperationButton(errorMsg : String, choice : String) : Unit{
            d("Admin", "MainActivity: $errorMsg was clicked")
            showToast()
            calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
            calculatorView.text = logicUnit.operationSetUp("$choice")
            resultsView.text = logicUnit.performOperation()
        }

        /*
        Input: errorMsg for the debugger to see which button was clicked
            choice is passed into the logicUnit to perform modSetUp function
        Output: Void
        Purpose: Input any new operations to the input
        */
        fun clickedModButton(errorMsg : String, choice : String) : Unit{
            d("Admin", "MainActivity: $errorMsg was clicked")
            showToast()
            calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
            calculatorView.text = logicUnit.modSetUp("$choice")
            resultsView.text = logicUnit.performOperation()
        }

        fun equalAnimation() {
            resultsView.animate()
                .translationY(-200F)
                .scaleY(2F)
                .scaleX(2F)
                .duration = 200
        }

        fun equalBackAnimation(){
            resultsView.animate()
                .translationY(0F)
                .scaleY(1F)
                .scaleX(1F)
                .duration = 0
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
            if (logicUnit.operationReady() || logicUnit.modReady()){
                calculatorView.text =  logicUnit.performEqual()
                resultsView.text = ""
                calculatorView.setTextColor(Color.parseColor("#36C23B"))
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

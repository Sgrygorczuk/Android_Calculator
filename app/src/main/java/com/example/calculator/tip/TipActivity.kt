package com.example.calculator.tip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.main.MainActivity
import com.example.roomtestthree.R
import com.example.roomtestthree.databinding.ActivityTipBinding

class TipActivity : AppCompatActivity()  {

    private val logicUnit = TipLogic()
    private var binding: ActivityTipBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTipBinding.inflate(layoutInflater).apply {
            setContentView(root)
            tenPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))

            backButton.setOnClickListener { backButton(it) }

            listOf(tenPercent, fifteenPercent, twentyPercent).forEach {
                button -> button.setOnClickListener { percentButton(it) }
            }

            listOf(
                oneButton, twoButton, threeButton,
                fourButton, fiveButton, sixButton,
                sevenButton, eightButton, nineButton,
                deleteButton, zeroButton, decimalButton,
                clearButton
            ).forEach { button -> button.setOnClickListener { clickedNumberButton(it) } }
        }
    }

    fun backButton (view : View) {
        Log.d("Admin", "TipActivity: ${view.tag} was clicked")
        startActivity(Intent(this, MainActivity::class.java))
    }

    /*
    Input: errorMsg for the debugger to see which button was clicked
   choice is passed into the logicUnit to perform addChar() function
    Output: Void
    Purpose: Input any new numbers or decimals to the input
    */
    fun ActivityTipBinding.clickedNumberButton(view : View){
        Log.d("Admin", "TipActivity: ${view.tag} was clicked")
        inputText.text = logicUnit.addChar(view.tag.toString())
        tipText.text = logicUnit.getTip()
        totalText.text = logicUnit.getTotal()
    }

    fun ActivityTipBinding.percentButton(view : View){
        Log.d("Admin", "TipActivity: ${view.tag} was clicked")
        var newButton : View = tenPercent

        tenPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        fifteenPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))
        twentyPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.black_selector))

        when (view.tag)
        {
            "10%" -> { newButton = tenPercent }
            "15%" -> { newButton = fifteenPercent }
            "20%" -> { newButton = twentyPercent }
        }

        newButton.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))
        logicUnit.setPercent(view.tag.toString())
        tipText.text = logicUnit.getTip()
        totalText.text = logicUnit.getTotal()
    }
}
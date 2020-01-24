package com.example.calculator.tip

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.R
import com.example.calculator.main.MainActivity
import kotlinx.android.synthetic.main.activity_tip.*

class TipActivity : AppCompatActivity()  {

    private val logicUnit = TipLogic()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tip)

        tenPercent.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_black_selector))

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
    fun clickedNumberButton(view : View){
        Log.d("Admin", "TipActivity: ${view.tag} was clicked")
        inputText.text = logicUnit.addChar(view.tag.toString())
        tipText.text = logicUnit.getTip()
        totalText.text = logicUnit.getTotal()
    }

    fun percentButton(view : View){
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
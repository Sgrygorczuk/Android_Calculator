/*
MainActivity.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This class takes care of all of the button inputs
They're broken down into 5 types

1) String forming which deal with 0,1,2,3,4,5,6,7,8,9,.   -> Need to be implemented π,e
2) Operations inputs such as +,-,/,*, ->   Needs to be implemented ^
3) Mod inputs such as  % and  √
4) UI updating buttons such as equal, neg, clear
5) Activity switching buttons that move us to other activities or modes
 */

package com.example.calculator.main

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Log.d
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import com.example.calculator.conversion.ConversionActivity
import com.example.calculator.history.HistoryEntry
import com.example.calculator.history.HistoryRecyclerAdapter
import com.example.calculator.history.HistoryViewModel
import com.example.calculator.tip.TipActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var historyViewModel: HistoryViewModel

    /*
    Tables are used to identify what the input button was pressed
        char table keeps track of all of the possible character that a user might want to input
        operation table keeps track of all the possible two input operations a user might want to execute
        mod table keeps track of all of the one input operation a user might want to execute
    */
    private val charTable = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "π", "e")
    private val operationTable = arrayOf("+", "-", "/", "*", "^")
    private val modTable = arrayOf("%", "√", "sin", "cos", "tan", "ln", "log", "1/x", "e^x", "x^2", "x^-1",
        "|x|", "e", "cbrt", "asin", "acos", "atan", "sinh", "cosh", "tanh", "asinh", "acosh", "atanh",
        "2^x", "x^3", "x!")

    //Keeps track of if we are in landscape or portrait mode
    private var orientation : String = ""
    private var degRad : Boolean = false

    private var historyOn = false
    private var blink = false

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
        historyViewModel.initializeOrientation(orientation)
    }

    /*
    Input: view (Button)
    Output: Void
    Purpose: Switches the activity to Tip Activity
    */
    fun tipButton(view : View){
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        startActivity(Intent(this, TipActivity::class.java))
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

    fun historyButton(view : View) {
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        if(orientation == "Port") {
            if (historyOn) {
                historyButton.setImageResource(R.drawable.ic_history)
                historyNumbers.visibility = VISIBLE
                history.visibility = GONE
                historyOn = false
            } else {
                historyButton.setImageResource(R.drawable.ic_keypad)
                historyNumbers.visibility = GONE
                history.visibility = VISIBLE
                historyOn = true
            }
        }
        if(orientation == "Land") {
            if (historyOn) {
                historyButton.setImageResource(R.drawable.ic_history)
                historyOrExtra.visibility = VISIBLE
                history.visibility = GONE
                historyOn = false
            } else {
                historyButton.setImageResource(R.drawable.ic_keypad)
                historyOrExtra.visibility = GONE
                history.visibility = VISIBLE
                historyOn = true
            }
        }

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
    private fun showToast(toastType : Int){
        var text = ""
        when {
            logicUnit.isMaxLength() -> {text = "You reached the max input of 15"}
            toastType == 0 -> {text = "Input Necessary"}
            toastType == 1 -> {text = "Can only perform one operation at a time"}
        }
        if(logicUnit.isMaxLength() || toastType == 0 || toastType == 1){
            val toast = Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT)
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
        var toastType = -1
        d("Admin", "MainActivity ($orientation): ${view.tag} button was clicked")
        when {
            //Adds a new character to current string
            charTable.contains(view.tag) -> {
                calculatorView.setTextColor(Color.parseColor(logicUnit.operationWasPerformed()))
                calculatorView.text = logicUnit.addChar(view.tag.toString())
            }
            //Save current string, connect it to new string using chosen operation
            operationTable.contains(view.tag) -> {
                //Checks for current operation set up
                //If user can click something that won't respond it chooses right toast to show
                if(logicUnit.isEmpty()){toastType = 0}
                else if(logicUnit.isOperation()){toastType = 1}
                showToast(toastType)
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.operationSetUp(view.tag.toString())
            }
            //Put a one input operation around current string
            modTable.contains(view.tag) -> {
                if(logicUnit.isOperation()){toastType = 1}
                showToast(toastType)
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.modSetUp(view.tag.toString())
            }
            view.tag == "delete" -> {
                calculatorView.setTextColor(Color.parseColor("#FFFFFF"))
                calculatorView.text = logicUnit.delete()
            }
        }
        //Updates rest of UI
        updateUI()
        //Make sure we don't display or let user interact with "NaN" and "Infinity"
        val viewString : String = logicUnit.performOperation(degRad)
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
        resultsView.text = logicUnit.performOperation(degRad)
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
        resultsView.text = logicUnit.performOperation(degRad)
    }

    fun historyClear (view:View){
        if(orientation == "Port") {
            historyButton.setImageResource(R.drawable.ic_history_off)
            historyNumbers.visibility = VISIBLE
            history.visibility = GONE
            historyOn = false
            historyButton.isEnabled = false
        }
        if(orientation == "Land") {
            historyButton.setImageResource(R.drawable.ic_history_off)
            historyOrExtra.visibility = VISIBLE
            history.visibility = GONE
            historyOn = false
            historyButton.isEnabled = false
        }
        historyViewModel.clear()
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
        val viewString : String = logicUnit.performOperation(degRad)
        //Checks if we have an operation that can be executed
        if (logicUnit.operationReady() || logicUnit.modReady()){
            //If we have an operation but the result is bad send a error toast
            if(viewString == "NaN" || viewString == "Infinity"){errorToast(viewString)}
            //If everything works we perform the operation
            else{
                var input = logicUnit.returnInput()
                var type = logicUnit.getOrientation()
                calculatorView.text = logicUnit.performEqual()
                historyViewModel.insert(HistoryEntry(input, "= ${calculatorView.text}", type))
                resultsView.text = ""
                historyButton.isEnabled = true
                historyButton.setImageResource(R.drawable.ic_history)
                //Performs the animation
                equalAnimation()
                //After animation finishes we update the color of the text
                Handler().postDelayed({
                    calculatorView.setTextColor(Color.parseColor("#36C23B"))
                }, 310)
            }
        }
    }

    /*
    Input: Anything that is passed from the Recycler View Buttons
    Output: Void
    Purpose: the doClick function
    */
    private fun doClick(item:Any) {
        calculatorView.setTextColor(Color.parseColor(logicUnit.operationWasPerformed()))
        if(item.toString().contains('=')){
            d("Admin", "Main Activity ($orientation): Result: ${item}, Recycler Entry Clicked button was clicked")
            calculatorView.text = logicUnit.addResult(item.toString().substring(2))
        }
        else{
            d("Admin", "Main Activity ($orientation): Input: ${item}, Recycler Entry Clicked button was clicked")
            calculatorView.text = logicUnit.addInput(item.toString())
        }
        //Updates rest of UI
        updateUI()
        //Make sure we don't display or let user interact with "NaN" and "Infinity"
        val viewString : String = logicUnit.performOperation(degRad)
        if(viewString == "NaN" || viewString == "Infinity"){resultsView.text = ""}
        else{resultsView.text = viewString}
    }

    /*
    UI Updates and Animations
    */

    /*
    Input: view (Button)
    Output: Void
    Purpose: Switches between the two sets of extra buttons in the scientific (landscape) main view
    */
    fun switchRadDeg(view : View) {
        d("Admin", "MainActivity ($orientation): layout $degRad was clicked")
        if(degRad) {
            DegRadTextView.text = "Deg"
            radiansButton.text = "Rad"
            radiansButtonTwo.text = "Rad"
            degRad = false
        }
        else{
            DegRadTextView.text = "Rad"
            radiansButton.text = "Deg"
            radiansButtonTwo.text = "Deg"
            degRad = true
        }
        updateUI()
        resultsView.text = logicUnit.performOperation(degRad)
    }

    /*
    Input: Void
    Output: Void
    Purpose: Executes all of the functions that update different parts of the UI
    */
    private fun updateUI(){
        showToast(-1)
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
    Purpose: Changes the text to be smaller if enough chars are put in, only done in portrait view
    */
    private fun textAdjustment(){
        if(orientation == "Port"){
            if(logicUnit.isAdjustLength()){
                calculatorView.animate()
                    .scaleY(.85f)
                    .scaleX(.85f)
                    .translationY(-50f)
                    .translationX(40f)
                    .duration = 200
                calculatorView.textSize = 40F

                curosorTextView.animate()
                    .scaleY(.85f)
                    .scaleX(.85f)
                    .translationY(-50f)
                    .duration = 200
                curosorTextView.textSize = 40F
                }
            else{
                calculatorView.animate()
                    .translationY(0f)
                    .translationX(0f)
                    .scaleY(1f)
                    .scaleX(1f)
                    .duration = 0
                calculatorView.textSize = 60F

                curosorTextView.animate()
                    .translationY(0f)
                    .translationX(0f)
                    .scaleY(1f)
                    .scaleX(1f)
                    .duration = 0
                curosorTextView.textSize = 60F
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
        if(orientation == "Port"){ calculatorView.translationX =  120f} else if (orientation == "Land") {calculatorView.translationX = 60f}
        calculatorView.scaleY = 0.5f
        calculatorView.scaleX = 0.5f

        calculatorView.animate()
            .translationY(0f)
            .translationX(0f)
            .scaleY(1f)
            .scaleX(1f)
            .duration = 300

        curosorTextView.bringToFront()
        curosorTextView.translationY = 200f
        if(orientation == "Port"){ curosorTextView.translationX =  120f} else if (orientation == "Land") {curosorTextView.translationX = 60f}
        curosorTextView.scaleY = 0.5f
        curosorTextView.scaleX = 0.5f

        curosorTextView.animate()
            .translationY(0f)
            .translationX(0f)
            .scaleY(1f)
            .scaleX(1f)
            .duration = 300
    }

    private fun blinking() {
        if(blink) {
            curosorTextView.text = ""
            blink = false
        }
        else{
            curosorTextView.text = "|"
            blink = true
        }
    }

    /*
    Input: Void
    Output: Void
    Purpose: Creates the activity
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Calls Activity Initialization Functions
        isOrientation()
        delButtonUpdate()

        //Calls Recycler View Functions
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = HistoryRecyclerAdapter{ item -> doClick(item) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Get a new or existing ViewModel from the ViewModelProvider.
        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        historyViewModel.initializeOrientation(orientation)

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        historyViewModel.allEntries.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let { adapter.setHistory(it) }
        })

        //Sets Up the Blinking Effect
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {override fun run() {
                blinking()
                mainHandler.postDelayed(this, 400)}})
    }
}

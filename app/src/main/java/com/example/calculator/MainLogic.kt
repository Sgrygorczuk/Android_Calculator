/*
MainLogic.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

The purpose of this class is to house all of the logic that's responsible for
making the calculator run. The calculator will perform up to two input operations.
One Input: 10% -> 10*0.01 = 0.1
Two Input: 4+5 -> 4+5 = 9

The information is broadly broken down into three types

1) Numbers/Decimals which will be saved into inputOne, inputTwo
2) Operators such as -,+,/ and * which will perform two input operations
3) Mods such as % and  √ which will perform one input operations
4) Expectation of the negation sign which kind of exits on its own

All of the data from this class is sent to the MainActivity.kt to be used in
calcualtorView and resultsView
 */

package com.example.calculator

import kotlin.math.sqrt

class MainLogic {
    //Used to keep track of the first input
    var inputOne : String = ""
    //Used to save the second input
    var inputTwo : String = ""
    //Used to tell which operation we are performing
    var operation : String = ""
    //Used to tell which modification we are performing
    var mod : String = ""
    //Used to display the computation in the calcualtorView
    var inputString : String = ""
    //Used to display the result of the clcuation on the resultsView
    var resultString : String = ""
    //Used to keep track of if we clicked perfomrEqual()
    var operationPerformed : Boolean = false

    /*
    Input: Void
    Output: Boolean
    The isMaxLength function returns True if the inputString is larger than the maximum value that
    can be displayed on the calcualtorView.
    Purpose: To output Toast that tells the user they input the max number of input
    */
    fun isMaxLength() : Boolean { return inputString.length >= 9 }

    /*
    Input: Void
    Output: Boolean
    The operationReady function checks if we can operate, there is an operator value placed,
    both inputOne and inputTwo are values we can operate with and checks if an operation wasn't performed yet
    Purpose: Making sure we only operate on valid inputs
    */
    fun operationReady() : Boolean { return  operation.isNotEmpty() && inputOne.toDoubleOrNull() is Double && inputTwo.toDoubleOrNull() is Double && !operationPerformed}

    /*
    Input: Void
    Output: Boolean
    The modReady function checks if we can perform a modification, by checking if there is a mod operator,
    and if inputOne is something that can be operated on
    Purpose: Making sure we only operate on valid inputs
    */
    fun modReady() : Boolean { return mod.isNotEmpty() && inputOne.toDoubleOrNull() is Double && inputOne.isNotEmpty()}

    /*
    Input: Void
    Output: String; Will be displayed in resultsView
    The performOperation function takes in all of the information gathered from the user and
    if possible operates to display them to the resultsView
    Purpose: Perform mathematical operations
    */
    fun performOperation() : String {
        //Checks if either the mod or operation is viable
        if(operationReady() || modReady()){
            //If operation is viable then we perform a +,-,/, or * on the two inputs
            if (operationReady()) {
                when (operation) {
                    "/" -> resultString = (inputTwo.toDouble() / inputOne.toDouble()).toString()
                    "*" -> resultString = (inputTwo.toDouble() * inputOne.toDouble()).toString()
                    "-" -> resultString = (inputTwo.toDouble() - inputOne.toDouble()).toString()
                    "+" -> resultString = (inputTwo.toDouble() + inputOne.toDouble()).toString()
                }
            }
            //If mod is viable we perform √ or % on the first input
            else if (modReady()) {
                 when (mod) {
                     "√" -> resultString = (sqrt(inputOne.toDouble())).toString()
                     "%" -> resultString = (inputOne.toDouble() * .01).toString()
                 }
            }
            //To get best results we perform the math in Double but if there is no info after the decimal we get rid of it
            if (resultString.toDouble() % 1.0 == 0.0) { resultString = resultString.replaceFirst(".0", "") }
            return resultString
        }
        else { return "" }
    }

    /*
    Input: String; tells us which input(0,1,2,3,4,5,6,7,8,9,.) the user wants to use
    Output: String; Will be displayed in calculatorView
    The addChar function Will take the information provided by the user, make it look appropriate and store it
    for use later
    Purpose: Ability for user to input new numbers
    */
    fun addChar(choice: String) : String {
        //Checks if we are not above max length before allowing user to input
        if(!isMaxLength()){
            when (choice) {
                "0" -> inputOne += "0"
                "1" -> inputOne += "1"
                "2" -> inputOne += "2"
                "3" -> inputOne += "3"
                "4" -> inputOne += "4"
                "5" -> inputOne += "5"
                "6" -> inputOne += "6"
                "7" -> inputOne += "7"
                "8" -> inputOne += "8"
                "9" -> inputOne += "9"
                "." -> if(!inputOne.contains('.')){if(inputOne == ""){ inputOne += "0."} else {inputOne += "."}}
            }
            //If the user input 0 followed by any number other than '.' it deletes the 0
            if(inputOne.length >= 2 && inputOne[0] == '0'  && inputOne[1] != '.') { inputOne = inputOne.replaceFirst("0", "")}
            //Depending on if a mod or operation was input it will display different format
            if(mod.isNotEmpty()) {writeMod(mod)} else {inputString = inputTwo + operation + inputOne}
        }
        return inputString
    }

    /*
    Input: String; tells us what which operator (+,-,/,*) the user wants to use
    Output: String; Will be displayed in calculatorView
    The operationSetUp function will first check if an operation or mod has been input
    It will save whatever is in inputOne to inputTwo and make inputOne empty for the new input
    If inputOne filled the inputString in a previous function then it will
    save the operation and format the inputString accordingly
    Purpose: Ability to input operations
    */
    fun operationSetUp(choice : String) : String{
        if(mod.isEmpty() && operation.isEmpty()) {
            inputTwo = inputOne
            inputOne = ""
            if (inputString.isNotEmpty()) {
                when (choice) {
                    "/" -> operation = "/"
                    "*" -> operation = "*"
                    "-" -> operation = "-"
                    "+" -> operation = "+"
                }
            }
            inputString = inputTwo + operation + inputOne
        }
        return inputString
    }

    /*
    Input: String; tells us what which mod (√,%) the user wants to use
    Output: String; Will be displayed in calculatorView
    The modSetUp function will first check if an operation or mod has been input
    if it hasn't it will then save the input mod and format the output accordingly
    Purpose: Ability to input mod
    */
    fun modSetUp(choice : String) : String {
        if (mod.isEmpty() && operation.isEmpty()) {
            when (choice) {
                "√" -> mod = "√"
                "%" -> mod = "%"
            }
            writeMod(choice)
        }
        return inputString
    }

    /*
    Input: String; tells us which format we want the inputString to be in (√,%)
    Output: String; Will be displayed in calculatorView
    The writeMod takes in the current mod value and formats the inputString according
    Purpose: Format different types of mod inputs
    */
    fun writeMod(choice: String) : Unit {
        when (choice) {
            "√" -> inputString = "√$inputOne"
            "%" -> inputString = "$inputOne%"
        }
    }

    /*
    Input: Void
    Output: String; Will be displayed in calculatorView
    The negation function adds or removes the negation symbol from the current inputOne
    Purpose: Place or remove negation symbol
    */
    fun negation() : String {
        //Checks for the '-' is inputOne, if is removes it, if it isn't adds it
        if(inputOne.contains('-')) inputOne = inputOne.replace("-", "") else inputOne = "-$inputOne"
        //Formats the inputString based on if we're in the mod mode or operation mode
        if(mod.isNotEmpty()) {writeMod(mod)} else {inputString = inputTwo + operation + inputOne}
        return inputString
    }

    /*
    Input: Void
    Output: Void
    The clear function clears all of the variables
    Purpose: Clear all the preset data
    */
    fun clear() : Unit {
        inputOne = ""
        inputTwo = ""
        operation = ""
        mod = ""
        inputString = ""
        resultString = ""
    }

    /*
    Input: Void
    Output: String; Will be displayed in calculatorView
    The performEqual puts the calculated data from the function performOperation from resultView to
    calculatorView.
    Purpose: Clear all the preset data, and sets the operationPerformed to true
    */
    fun performEqual() : String {
        inputTwo = ""
        inputOne = resultString
        operation = ""
        mod = ""
        operationPerformed = true
        return inputOne
    }

    /*
    Input: Void
    Output: String; will change the color of the text in calculatorView
    The operationWasPerformed function checks if we clicked performEqual and if we did it resets
    the remaining data
    Purpose: To determine if we are using the output of previous operation or we are starting a
    new computation
    */
    fun operationWasPerformed() : String {
        if(operationPerformed) {
            inputOne = ""
            operationPerformed = false
        }
        return "#FFFFFF"
    }
}
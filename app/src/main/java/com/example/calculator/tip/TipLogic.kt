package com.example.calculator.tip

import java.math.BigDecimal
import java.math.RoundingMode

class TipLogic {

    private var percentage : String = "10%"
    var input : String = ""
    private var tip : String = ""

    /*
    Input: Void
    Output: Boolean
    The isMaxLength function returns True if the inputString is larger than the maximum value that
    can be displayed on the calcualtorView.
    Purpose: To output Toast that tells the user they input the max number of input
    */
    private fun isMaxLength(input : String) : Boolean { return input.length >= 9 }

    fun setPercent(input : String) {percentage = input}

    fun getTip() : String {
        return if(input == "") {
            tip = ""
            "$"
        } else {
            tip = (input.toDouble() * percentage.substring(0, 2).toDouble() / 100).toString()
            tip = BigDecimal(tip.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            "$${addCommas(tip)}"
        }
    }

    fun getTotal() : String {
        return if(input == "") { "$"
        } else {
            var total: String = (tip.toDouble() + input.toDouble()).toString()
            total = BigDecimal(total.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
            "$${addCommas(total)}"
        }
    }

    fun addChar(choice: String) : String {
        //Checks if we are not above max length before allowing user to input
        if(choice == "c") {
            input = ""
            return "$"}
        else if(!isMaxLength(input)){
            when (choice) {
                "0" -> input += "0"
                "1" -> input += "1"
                "2" -> input += "2"
                "3" -> input += "3"
                "4" -> input += "4"
                "5" -> input += "5"
                "6" -> input += "6"
                "7" -> input += "7"
                "8" -> input += "8"
                "9" -> input += "9"
                "." -> if(!input.contains('.')){ input += if(input == ""){ "0." } else { "." }
                }
            }
            //If the user input 0 followed by any number other than '.' it deletes the 0
            if(input.length >= 2 && input[0] == '0'  && input[1] != '.') { input = input.replaceFirst("0", "")}
        }
        if(input != "" && choice == "d"){input = input.substring(0, input.length - 1)}
        return "$${addCommas(input)}"
    }

    /*
    Input:
    Output:
    Purpose:
    */
    private fun addCommas(input : String) : String {
        var output = ""
        var adjustedInput : String = if(input.contains('.')) { input.substring(0,input.indexOf('.')) } else{ input }
        var i : Int = adjustedInput.length
        if(adjustedInput.length > 3) {
            while(i > 3){
                output = output + "," + adjustedInput.substring(i-3,i)
                i -= 3
            }
        }
        output = adjustedInput.substring(0, i) + output
        if(input.contains('.')) { output += input.substring(input.indexOf('.'))}
        return output
    }

}
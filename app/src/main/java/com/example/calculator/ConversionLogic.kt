package com.example.calculator

class ConversionLogic {

    var inputOne : String = ""
    var inputTwo : String = ""

    /*
    Input: Void
    Output: Boolean
    The isMaxLength function returns True if the inputString is larger than the maximum value that
    can be displayed on the calcualtorView.
    Purpose: To output Toast that tells the user they input the max number of input
    */
    fun isMaxLength() : Boolean { return inputOne.length >= 9 }

    fun addChar(choice: String) : Unit {
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
                "." -> if (inputOne == "") inputOne += "0." else inputOne += "."
            }
            //If the user input 0 followed by any number other than '.' it deletes the 0
            if(inputOne.length >= 2 && inputOne[0] == '0'  && inputOne[1] != '.') { inputOne = inputOne.replaceFirst("0", "")}
        }
    }
}
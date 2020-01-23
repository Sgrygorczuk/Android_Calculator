package com.example.calculator.conversion

import java.math.BigDecimal
import java.math.RoundingMode

class ConversionLogic {

    /*
    Acre vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    Are vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    Hectare vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    Square Centimeter vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    Square Foot vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    Square Inch vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    Square Meter vs: Acre, Are, Hectare, Square Centimeter, Square Foot, Square Inch, Square Meter
    */
    val areaTable = arrayOf(arrayOf(1, 40.4686, 0.404686, 4.047e+7, 43560, 6.273e+6, 4046.86),
                            arrayOf(0.0247105, 1, 0.01, 1e+6, 1076.39, 155000, 100),
                            arrayOf(2.47105, 100, 1, 1e+8, 107639, 1.55e+7, 10000),
                            arrayOf(2.4711e-8, 1e-6, 1e-8, 1, 0.00107639, 0.155, 1e-4),
                            arrayOf(2.2957e-5, 0.00092903, 9.2903e-6, 929.03, 1, 144, 0.092903),
                            arrayOf(1.5942e-7, 6.4516e-6, 6.4516e-8, 6.4516, 0.00694444, 1, 0.00064516),
                            arrayOf(0.000247105, 0.01, 1e-4, 10000, 10.7639, 1550, 1))

    /*
    Millimeter vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Centimeter vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Meter vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Kilometer vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Inch vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Foot vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Yard vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Mile vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Nautical Mile vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    Mil vs: Millimeter, Centimeter, Meter, Kilometer, Inch, Foot, Yard, Mile, Nautical Mile, Mil
    */
    val lengthTable = arrayOf(arrayOf(1, 0.1, 0.001, 1e-6, 0.0393701, 0.00328084, 0.00109361, 6.2137e-7, 5.3996e-7, 39.3701),
                              arrayOf(10, 1, 0.01, 1e-5, 0.393701, 0.0328084, 0.0109361, 6.2137e-6, 5.3996e-6, 393.701),
                              arrayOf(1000, 100, 1, 0.001, 39.3701, 3.28084, 1.09361 ,0.000621371, 0.000539957, 39370.1),
                              arrayOf(1e+6, 100000, 1000, 1, 39370.1, 3280.84, 1093.61, 0.621371, 0.539957, 3.937e+7),
                              arrayOf(25.4, 2.54, 0.0254, 2.54e-5, 1, 0.0833333, 0.0277778, 1.5783e-5, 1.3715e-5, 1000),
                              arrayOf(304.8, 30.48, 0.3048, 0.0003048, 12, 1, 0.333333, 0.000189394, 0.000164579, 12000),
                              arrayOf(914.4, 91.44, 0.9144, 0.0009144, 36, 3, 1, 0.000568182, 0.000493737, 36000),
                              arrayOf(1.609e+6, 160934, 1609.34, 1.60934, 63360, 5280, 1760, 1, 0.868976, 6.336e+7),
                              arrayOf(1.852e+6, 185200, 1852, 1.852, 72913.4, 6076.12, 2025.37, 1.15078, 1, 7.291e+7),
                              arrayOf(0.0254, 0.00254, 2.54e-5, 2.54e-8, 0.001, 8.3333e-5, 2.7778e-5, 1.5783e-8, 1.3715e-8, 1))

    /*
    There is no easy table you just have to do formulas which are done in temperatureFormulas
   */

    /*
    UK Gallon vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    US Gallon vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    Liter vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    Milliliter vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    Cubic Centimeter vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    Cubic Meter vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    Cubic Inch vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    Cubic Foot vs: UK Gallon, US Gallon, Liter, Milliliter, Cubic Centimeter, Cubic Meter, Cubic Inch, Cubic Foot
    */
    val volumeTable = arrayOf(arrayOf(1, 1.20095, 4.54609, 4546.09, 4546.09, 0.00454609, 277.419, 0.160544),
                              arrayOf(0.832674, 1, 3.78541, 3785.41, 3785.41, 0.00378541, 231, 0.133681),
                              arrayOf(0.219969, 0.264172, 1, 1000, 1000, 0.001, 61.0237, 0.0353147),
                              arrayOf(0.000219969, 0.000264172, 0.001, 1, 1, 1e-6, 0.0610237, 0.168936),
                              arrayOf(0.000219969, 0.000264172, 0.001, 1, 1, 1e-6, 0.0610237, 0.168936),
                              arrayOf(219.969, 264.172, 1000, 1e+6, 1e+6, 1, 61023.7, 35.3147),
                              arrayOf(0.00360465, 0.004329, 0.0163871, 16.3871, 16.3871, 1.6387e-5, 1, 0.000578704),
                              arrayOf(6.22884, 7.48052, 28.3168, 28316.8, 28316.8, 0.0283168,  1728, 1))

    /*
    Ton vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    UK Ton vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    US Ton vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    Pound vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    Ounce vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    Kilogram vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    Gram vs: Ton, UK Ton, US Ton, Pound, Ounce, Kilogram, Gram
    */
    val massTable = arrayOf(arrayOf(1, 0.984207, 1.10231, 2204.62, 35274, 1000, 1e+6),
                            arrayOf(1.01605, 1, 1.12, 2240, 35840, 1016.05, 1.016e+6),
                            arrayOf(0.907185, 0.892857, 1, 2000, 32000, 907.185, 907185),
                            arrayOf(0.000453592, 0.000446429, 0.0005, 1, 16, 0.453592, 453.592),
                            arrayOf(2.835e-5, 2.7902e-5, 3.125e-5, 0.0625, 1, 0.0283495, 28.3495),
                            arrayOf(0.001, 0.000984207, 0.00110231, 2.20462, 35.274, 1, 1000),
                            arrayOf(1e-6, 9.8421e-7, 1.1023e-6, 0.00220462, 0.035274, 0.001, 1))

    /*
    Bit vs: Bit, Byte, Kilobyte, Megabyte, Gigabyte, Terabyte
    Byte vs: Bit, Byte, Kilobyte, Megabyte, Gigabyte, Terabyte
    Kilobyte vs: Bit, Byte, Kilobyte, Megabyte, Gigabyte, Terabyte
    Megabyte vs: Bit, Byte, Kilobyte, Megabyte, Gigabyte, Terabyte
    Gigabyte vs: Bit, Byte, Kilobyte, Megabyte, Gigabyte, Terabyte
    Terabyte vs: Bit, Byte, Kilobyte, Megabyte, Gigabyte, Terabyte
    */
    val dataTable = arrayOf(arrayOf(1, 0.125, 0.000125, 1.25e-7, 1.25e-10, 1.25e-13),
                            arrayOf(8, 1, 0.001, 1e-6, 1e-9, 1e-12),
                            arrayOf(8000, 1000, 1, 0.001, 1e-6, 1e-9),
                            arrayOf(8e+6, 1e+6, 1000, 1, 0.001, 1e-6),
                            arrayOf(8e+9, 1e+9, 1e+6, 1000, 1, 0.001),
                            arrayOf(8e+12, 1e+12, 1e+9, 1e+6, 1000, 1))


    /*
    m/s vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    m/h vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    km/s vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    km/h vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    in/s vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    in/h vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    ft/s vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    ft/h vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    mi/s vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    mi/h vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    kn vs: m/s, m/h, km/s, km/h, in/s, in/h, ft/s, ft/h, mi/s, mi/h, kn
    */

    val speedTable = arrayOf(arrayOf(1, 3600, 0.001, 3.6, 39.23, 141732.283, 3.28, 11811.023, 0.000621, 2.2369, 1.9438),
                             arrayOf(0.0002778, 1, 2.7778e-7, 0.001, 0.01093, 39.37007, 0.000911, 3.2808, 0.000000172, 0.000613, 0.0005399),
                             arrayOf(1000, 3600000, 1, 3600, 39370.07874, 141732283.46, 3280.839, 11811023.622, 0.6213, 2236.936, 1943.844),
                             arrayOf(0.2778, 1000, 0.0002778, 1, 10.936, 39370.078, 0.9113, 3280.8398, 0.000172, 0.62137, 0.5399),
                             arrayOf(0.0254, 91.44, 0.0000254, 0.09144, 1, 3600, 0.08333, 300, 0.00000157, 0.05681, 0.04937),
                             arrayOf(0.00000705, 0.0254, 0.0000000071, 0.0000254, 0.0002778, 1, 0.0000231, 0.08333, 0.0000000044, 0.00001578, 0.0000137),
                             arrayOf(0.3048, 1097.28, 0.0003048, 1.09728, 12, 43200, 1, 3600, 0.000189, 0.681, 0.592483),
                             arrayOf(0.00008467, 0.3048, 0.0000000847, 0.0003048, 0.00333, 12, 0.000277778, 1, 0.0000000526, 0.000189, 0.0001645),
                             arrayOf(1609.344, 5793638.4, 1.609, 5793.63, 63360, 228096000, 5280, 1900800, 1, 3600, 3128.3144),
                             arrayOf(0.44704, 1609.344, 0.00044, 1.609344, 17.6, 63360, 1.4667, 5280, 0.0002778, 1, 0.86897),
                             arrayOf(0.51444, 1852, 0.0005144, 1.852, 20.2537, 72913.385, 1.6878, 6076.1154, 0.000319, 1.1507, 1))


    /*
    Milliseconds vs: Milliseconds, Seconds, Minutes, Hours, Days, Weeks
    Seconds vs: Milliseconds, Seconds, Minutes, Hours, Days, Weeks
    Minutes vs: Milliseconds, Seconds, Minutes, Hours, Days, Weeks
    Hours vs: Milliseconds, Seconds, Minutes, Hours, Days, Weeks
    Days vs: Milliseconds, Seconds, Minutes, Hours, Days, Weeks
    Weeks vs: Milliseconds, Seconds, Minutes, Hours, Days, Weeks
    */
    val timeTable = arrayOf(arrayOf(1, 0.001, 1.6667e-5, 2.7778e-7, 1.1574e-8, 1.6534e-9),
                            arrayOf(1000, 1, 0.0166667, 0.000277778, 1.1574e-5, 1.6534e-6),
                            arrayOf(60000, 60, 1, 0.0166667, 0.000694444, 9.9206e-5),
                            arrayOf(3.6e+6, 3600, 60, 1, 0.0416667, 0.00595238),
                            arrayOf(8.64e+7, 86400, 1440, 24, 1, 0.142857),
                            arrayOf(6.048e+8, 604800, 10080, 168, 7, 1))

    /*
    Input: Void
    Output: Boolean
    The isMaxLength function returns True if the inputString is larger than the maximum value that
    can be displayed on the calcualtorView.
    Purpose: To output Toast that tells the user they input the max number of input
    */
    fun isMaxLength(input : String) : Boolean { return input.length >= 9 }

    fun addChar(inputIn : String, choice: String) : String {
        var input : String = inputIn
        //Checks if we are not above max length before allowing user to input
        if(choice == "c") {return ""}
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
                "." -> if(!input.contains('.')){if(input == ""){ input += "0."} else {input += "."}}
            }
            //If the user input 0 followed by any number other than '.' it deletes the 0
            if(input.length >= 2 && input[0] == '0'  && input[1] != '.') { input = input.replaceFirst("0", "")}
        }
        if(input != "" && choice == "d"){input = input.substring(0, input.length - 1)}
        return input
    }

    /*
    Input: Void
    Output: String; Will be displayed in calculatorView
    The negation function adds or removes the negation symbol from the current inputOne
    Purpose: Place or remove negation symbol
    */
    fun negation(input : String) : String {
        var output = input
        //Checks for the '-' is inputOne, if is removes it, if it isn't adds it
        if(output.contains('-')){output = output.replace("-", "")} else {output = "-$input"}
        return output
    }

    fun temperatureFormulas(choice : Int, value : String) : String {
        var outputString : String = ""
        when(choice)
        {
            0 -> outputString = value
            1 -> outputString = (value.toDouble() * 9/5 + 32).toString()
            2 -> outputString = (value.toDouble() + 273.15).toString()
            3 -> outputString = ((value.toDouble() - 32) * 5/9).toString()
            4 -> outputString = value
            5 -> outputString = ((value.toDouble() - 32) * 5/9 + 273.15).toString()
            6 -> outputString = (value.toDouble() - 273.15).toString()
            7 -> outputString = ((value.toDouble() - 273.15) * 9/5 + 32).toString()
            8 -> outputString = value
        }
        return outputString
    }

    fun conversion(choice : Int, currentTop : Int, currentBottom : Int, value : String, divMul : Boolean) : String{
        var currentTable = arrayOf<Array<Any>>()
        var outputString: String
        if(value.isEmpty() ||(!value[value.length-1].isDigit() && value.indexOf('E')>= 0)) {return ""}
        else if(value.isNotEmpty()) {
            when (choice) {
                0 -> currentTable = areaTable
                1 -> currentTable = lengthTable
                3 -> currentTable = volumeTable
                4 -> currentTable = massTable
                5 -> currentTable = dataTable
                6 -> currentTable = speedTable
                7 -> currentTable = timeTable
            }

            if(choice == 2){outputString = temperatureFormulas(currentTop *  3 + currentBottom, value) }
            else
            {
                if (divMul){outputString = (value.toFloat() * currentTable[currentTop][currentBottom].toString().toFloat()).toString() }
                else {outputString = (value.toFloat() / currentTable[currentTop][currentBottom].toString().toFloat()).toString() }
            }

            if (outputString.toDouble() % 1.0 == 0.0) { outputString = outputString.replaceFirst(".0", "") }
            else if(outputString.toDouble() % 1.0 != 0.0){
                outputString = BigDecimal(outputString.toDouble()).setScale(7, RoundingMode.HALF_EVEN).toString()
                outputString = outputString.trimEnd('0')}

            return outputString
        }
        else{return ""}
    }
}


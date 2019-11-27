package com.example.calculator

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
    Celsius vs: Celsius, Fahrenheit, Kelvin
    Fahrenheit vs: Celsius, Fahrenheit, Kelvin
    Kelvin vs: Celsius, Fahrenheit, Kelvin
     */
    val temperatureTable = arrayOf(arrayOf(1, 33.8, 274.15),
                                   arrayOf(-17.22222222222, 1, 255.9277777778),
                                   arrayOf(-272.15, -457.87, 1))

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


    val speedTable = arrayOf(arrayOf(1, 0.001, 1.6667e-5, 2.7778e-7, 1.1574e-8, 1.6534e-9),
        arrayOf(1000, 1, 0.0166667, 0.000277778, 1.1574e-5, 1.6534e-6),
        arrayOf(60000, 60, 1, 0.0166667, 0.000694444, 9.9206e-5),
        arrayOf(3.6e+6, 3600, 60, 1, 0.0416667, 0.00595238),
        arrayOf(8.64e+7, 86400, 1440, 24, 1, 0.142857),
        arrayOf(6.048e+8, 604800, 10080, 168, 7, 1))

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

    var conversionValue : Double = 0.0
    var currentTable = arrayOf<Array<Any>>()
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

    fun conversion(choice : Int, currentTop : Int, currentBottom : Int){
        when(choice){
            0 -> currentTable = areaTable
            1 -> currentTable = lengthTable
            2 -> currentTable = temperatureTable
            3 -> currentTable = volumeTable
            4 -> currentTable = massTable
            5 -> currentTable = dataTable
            6 -> currentTable = speedTable
            7 -> currentTable = timeTable
        }
        conversionValue = currentTable[currentTop][currentBottom].toString().toDouble()
    }
}


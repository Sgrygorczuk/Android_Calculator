package com.example.calculator.History

/*
Data class that stores the information that is fed to the history recycler
it stores the input string so something like "3+3" and the result string
which would display as "= 6"
 */
data class HistoryEntry(
    var input: String,
    var result: String
) {
}
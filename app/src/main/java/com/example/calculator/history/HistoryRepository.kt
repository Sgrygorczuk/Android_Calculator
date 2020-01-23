/*
HistoryRepository.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This class is used to facilitate the communication between HistoryViewModel and the database.
It performs four tasks using the functions defined in HistoryDao, loadAll, loadPort, insert and clear.
This is done on a separate thread from the UI functionality.

 */

package com.example.calculator.history

import androidx.lifecycle.LiveData

class HistoryRepository(private val historyDao: HistoryDao) {

    /*
    Input: orientation, computed by the MainLogic class tells us which view type this information cna be load
    into.
    Output: Returns the a table with the appropriate rows, either all rows for Land or those limited to
    '+', '-', '*', '/', ' "%' and '√' for Port.
    Purpose: Return the table we wish to display to the user
    */
    fun initializeDatabase(orientation : String) : LiveData<List<HistoryEntry>> {
        return if(orientation == "Port"){ historyDao.loadPort("Port")} else{ historyDao.loadAll() }
    }

    /*
    Input: historyEntry (input, return, orientation) all the info passed in by the user
    Output: Null
    Purpose: Add a new entry to the table
    */
    suspend fun insert(historyEntry: HistoryEntry) {
        historyDao.insert(historyEntry)
    }

    /*
    Input: Null
    Output: Null
    Purpose: Deletes all entries from the table
    */
    suspend fun clear(){
        historyDao.deleteAll()
    }
}
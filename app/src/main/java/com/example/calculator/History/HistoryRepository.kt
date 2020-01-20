package com.example.calculator.History

import androidx.lifecycle.LiveData

class HistoryRepository(private val historyDao: HistoryDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    fun initlizeDatabase(orientation : String) : LiveData<List<HistoryEntry>> {
        var allEntries: LiveData<List<HistoryEntry>>
        if(orientation == "Port"){
            allEntries  = historyDao.loadPort("Port")
        }
        else{
            allEntries  = historyDao.loadAll()

        }
        return allEntries
    }

    suspend fun insert(historyEntry: HistoryEntry) {
        historyDao.insert(historyEntry)
    }

    suspend fun clear(){
        historyDao.deleteAll()
    }
}
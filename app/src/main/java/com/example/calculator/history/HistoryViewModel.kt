/*
HistoryRepository.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This class is used to communicate between the Main Activity Screen and the Repository and starts
the subroutines so that the actions can take place on a separate thread.
 */

package com.example.calculator.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: HistoryRepository
    // LiveData gives us updated table which it changes
    var allEntries: LiveData<List<HistoryEntry>>

    /*
    Input: Null
    Output: Null
    Purpose: Initializes the table we are going to be viewing
    */
    init {
        val historyDao = HistoryRoomDataBase.getDatabase(application, viewModelScope).historyDao()
        repository = HistoryRepository(historyDao)
        allEntries = repository.initializeDatabase("Port")
    }

    /*
    Input: Orientation, MainActivity tells us what it's state is and based on that we choose our table
    Output: Null
    Purpose: Retrieves the correct table for us using a subroutine so that main activity is not stalled
    */
    fun initializeOrientation(orientation : String) = viewModelScope.launch{
        allEntries = repository.initializeDatabase(orientation)
    }

    /*
    Input: historyEntry (input, result, orientation) passed in by the user
    Output: Null
    Purpose: Inserts a new entry to our table using a subroutine so that main activity is not stalled
    */
    fun insert(historyEntry: HistoryEntry) = viewModelScope.launch { repository.insert(historyEntry) }

    /*
    Input: Null
    Output: Null
    Purpose: Deletes everything from our table using a subroutine so that main activity is not stalled
    */
    fun clear() = viewModelScope.launch { repository.clear() }

}
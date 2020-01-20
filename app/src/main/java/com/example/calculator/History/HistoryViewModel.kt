package com.example.calculator.History

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: HistoryRepository
    private var Orientation : String = "Port"
    // LiveData gives us updated words when they change.
    val allEntries: LiveData<List<HistoryEntry>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val historyDao = HistoryRoomDataBase.getDatabase(application, viewModelScope).historyDao()
        repository = HistoryRepository(historyDao)
        allEntries = repository.initlizeDatabase(Orientation)
    }

    fun initilizeOrientation(orientation : String){
        Orientation = orientation
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on the mainthread, blocking
     * the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called viewModelScope which we
     * can use here.
     */
    fun insert(historyEntry: HistoryEntry) = viewModelScope.launch {
        repository.insert(historyEntry)
    }

    fun clear() = viewModelScope.launch {
        repository.clear()
    }
}
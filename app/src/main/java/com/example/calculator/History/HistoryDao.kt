package com.example.calculator.History

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryDao {
    //Loads only the history that can be displayed on the Port Activity
    @Query("SELECT * FROM history_table WHERE orientation LIKE :search")
    fun loadPort(search: String?): LiveData<List<HistoryEntry>>

    //Loads all of the history as Land Activity can use all of it
    @Query("SELECT * FROM history_table")
    fun loadAll(): LiveData<List<HistoryEntry>>

    //Inputs, shouldn't have issue with matching inputs as all will have
    //Different ids
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: HistoryEntry)

    //Deletes all rows from the table
    @Query("DELETE FROM history_table")
    suspend fun deleteAll()

}
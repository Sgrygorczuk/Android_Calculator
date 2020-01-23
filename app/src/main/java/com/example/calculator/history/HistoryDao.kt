/*
HistoryDao.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This interface defines the interaction with out database, it performs only
four actions.
    Insert - Take in any valid input even if it is a copy of one already stored
    loadAll - Loads all of the rows in the database
    loadPort - Loads only the rows that could be created in Main Port Activity
    clear - Deletes all of the rows in the table
*/

package com.example.calculator.history

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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: HistoryEntry)

    //Deletes all rows from the table
    @Query("DELETE FROM history_table")
    suspend fun deleteAll()
}
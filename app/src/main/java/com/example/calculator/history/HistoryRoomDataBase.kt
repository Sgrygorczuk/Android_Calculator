/*
HistoryRepository.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This class is used to communicate between the Main Activity Screen and the Repository and starts
the subroutines so that the actions can take place on a separate thread.
 */

package com.example.calculator.history

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [HistoryEntry::class], version = 1, exportSchema = false)
abstract class HistoryRoomDataBase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryRoomDataBase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): HistoryRoomDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, HistoryRoomDataBase::class.java, "history_database")
                    .addCallback(HistoryDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class HistoryDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database -> scope.launch(Dispatchers.IO) {} }
            }
        }
    }
}
package com.example.calculator.History

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class HistoryEntry(
    @ColumnInfo(name = "input") var input: String,
    @ColumnInfo(name = "result") var result: String,
    @ColumnInfo(name = "orientation") var orientation: String){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
package com.example.calculator.History

//Current Data Set Up Class that holds all of the info premade for the history
//recycler
class DataSource{

    companion object{

        fun createDataSet(): ArrayList<HistoryEntry>{
            val list = ArrayList<HistoryEntry>()
            list.add(
                HistoryEntry(
                    "1-1",
                    "= 0"
                )
            )
            list.add(
                HistoryEntry(
                    "90-49",
                    "= 51"
                )
            )

            list.add(
                HistoryEntry(
                    "3*2",
                    "= 6"
                )
            )
            list.add(
                HistoryEntry(
                    "20-30",
                    "= -10"
                )
            )
            list.add(
                HistoryEntry(
                    "41+42",
                    "= 83"
                )
            )
            list.add(
                HistoryEntry(
                    "130-30",
                    "= 100"
                )
            )
            list.add(
                HistoryEntry(
                    "7*7",
                    "= 49"
                )
            )
            list.add(
                HistoryEntry(
                    "1+0",
                    "= 1"
                )
            )
            list.add(
                HistoryEntry(
                    "3*3",
                    "= 9"
                )
            )
            return list
        }
    }
}
package com.example.calculator.History

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.calculator.R
import kotlinx.android.synthetic.main.history_layout.view.*


class HistoryRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //Holds a list of all of the HistoryEntry Objects
    private var items: List<HistoryEntry> = ArrayList()

    //Brings up the set up View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.history_layout, parent, false))
    }

    //Picks which view holder to display
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            //When we want the History view, look at the view from items at current position
            is HistoryViewHolder ->{ holder.bind(items[position]) }
        }
    }

    //Returns the size of the array
    override fun getItemCount(): Int {
        return items.size
    }

    //Function used externally to update the list 
    fun submitList(historyList : List<HistoryEntry>){
        items = historyList
    }
}

//This is a class that describe the layout
class HistoryViewHolder constructor(itemView : View): RecyclerView.ViewHolder(itemView) {
    //Connects the variables to the components on the layout
    private var inputText: Button = itemView.inputButton
    private var resultText: Button = itemView.resultButton

    //Connects those layout components to the data source
    fun bind(historyEntry: HistoryEntry) {
        inputText.text = historyEntry.input
        resultText.text = historyEntry.result
    }
}

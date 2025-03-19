/*
HistoryRecyclerAdapter.kt by Sebastian Grygorczuk
September 2019
sgrygorczuk@gmail.com

This file has two classes included in it.

The first class is HistoryViewHolder which maps the buttons in the
history_layout to whatever information we want to pass to them.

The second class is HistoryRecyclerAdapter class takes the information
from the database and pass is it to the HistoryViewHolder to create all
the entries in the RecyclerView.

 */

package com.example.calculator.history


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestthree.databinding.HistoryLayoutBinding

class HistoryRecyclerAdapter(val adapterOnClick : (Any) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //This is a class that describe the layout of one entry in the RecyclerView
    inner class HistoryViewHolder(binding : HistoryLayoutBinding): RecyclerView.ViewHolder(binding.root){
        //Connects the variables to the components on the layout
        private var inputText: Button = binding.inputButton
        private var resultText: Button = binding.resultButton

        /*
        Input: historyEntry
        Output: Void
        Purpose: Create an entry to display in RecyclerView
        */
        fun bind(historyEntry : HistoryEntry){
            inputText.text = historyEntry.input.toLowerCase()
            resultText.text = historyEntry.result
            inputText.setOnClickListener{adapterOnClick(historyEntry.input)}
            resultText.setOnClickListener{adapterOnClick(historyEntry.result)}
        }
    }

    //Holds a list of all of the HistoryEntry Objects
    private var items = emptyList<HistoryEntry>()

    /*
    Input: List<HistoryEntry> all of the current rows that are saved in memory and are apporate
        to the current activity are passed in
    Output: Null
    Purpose: Save the current table
    */
    internal fun setHistory(items : List<HistoryEntry>){
        this.items = items
        notifyDataSetChanged()
    }

    /*
    Input: ViewGroup (?), Int (?) Necessary for RecyclerView to work not sure where its coming from
    Output: HistoryViewHolder, entry of the Recycler
    Purpose: Brings up all of the HistoryViewHolder entries into the RecyclerView
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = HistoryLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    /*
    Input: RecyclerView.ViewHolder an entry of the Recycler view, position in the list
    Output: Null
    Purpose: Connects all of the rows in the list to create ViewHolders
    */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //Possible to have different ViewHolders in one RecyclingView
        when(holder){
            //When we want the History view, look at the view from items at current position
            is HistoryViewHolder ->{ holder.bind(items[position])}
        }
    }

    /*
    Input: Null
    Output: Int, size of the list
    Purpose: Tells us how big the list is
    */
    override fun getItemCount() = items.size
}
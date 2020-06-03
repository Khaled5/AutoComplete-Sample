package com.easyinc.autocomplete.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easyinc.autocomplete.R
import com.easyinc.autocomplete.utils.extentions.inflate
import kotlinx.android.synthetic.main.text_layout.view.*

class AutoCompleteAdapter: RecyclerView.Adapter<AutoCompleteAdapter.AutoCompleteViewHolder>() {

    private var autoCompleteList = listOf<String>()
    private var finalList = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AutoCompleteViewHolder(parent.inflate(R.layout.text_layout))

    override fun getItemCount() = finalList.size

    override fun onBindViewHolder(holder: AutoCompleteViewHolder, position: Int) = holder.bind(finalList[position])

    fun submitList(list: List<String>){
        autoCompleteList = list
        finalList = autoCompleteList
        notifyDataSetChanged()
    }

    fun filterAutoComplete(keyword: String?){
        if (keyword!!.isEmpty())
            finalList = autoCompleteList
        else
            finalList = autoCompleteList.filter { it.toLowerCase().startsWith(keyword) }

        notifyDataSetChanged()
    }

    inner class AutoCompleteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(text: String){
            itemView.tv_text.text = text
        }

    }

}
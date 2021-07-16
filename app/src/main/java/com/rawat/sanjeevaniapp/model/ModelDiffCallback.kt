package com.rawat.sanjeevaniapp.model

import androidx.recyclerview.widget.DiffUtil

class ModelDiffCallback(
    private val oldList:List<Session>,
    private val newList:List<Session>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].session_id == newList[newItemPosition].session_id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].session_id == newList[newItemPosition].session_id
    }
}
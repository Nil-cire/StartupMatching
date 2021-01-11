package com.eric.startupmatching

import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.project.treeview.model.TreeViewModel

interface OnExpandAndHideListener {
    fun onExpand(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
                 adapterItems: ArrayList<TreeViewModel>,
                 clickedItem: TreeViewModel,
                 clickedItemPosition: Int)

    fun onHide(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
               adapterItems: ArrayList<TreeViewModel>,
               clickedItem: TreeViewModel,
               clickedItemPosition: Int)
}
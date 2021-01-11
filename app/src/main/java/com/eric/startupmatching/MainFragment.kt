package com.eric.startupmatching

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment


open class MainFragment : ListFragment() {
    interface OnListItemClickListener {
        fun onListItemClick(position: Int)
    }

    private var mItemClickListener: OnListItemClickListener? = null
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mItemClickListener = activity as OnListItemClickListener?
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val items: Array<String?> = resources.getStringArray(R.array.main_items)
        val adapter: ArrayAdapter<Any?>? = activity?.let {
            ArrayAdapter<Any?>(
                it,
                R.layout.item_child, items
            )
        }
        listAdapter = adapter
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        mItemClickListener?.onListItemClick(position)
    }
}
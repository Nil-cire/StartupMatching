package com.eric.startupmatching.project.treeview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.R
import com.eric.startupmatching.project.treeview.model.ChildModel
import kotlinx.android.synthetic.main.item_child.view.*

/**
 * Created by zzw on 2018/6/22.
 */
class ChildViewBinder : ItemViewBinder<ChildModel, ChildViewBinder.ViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_child, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, child: ChildModel) {
        holder.itemView.tvContent.text = child.content
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

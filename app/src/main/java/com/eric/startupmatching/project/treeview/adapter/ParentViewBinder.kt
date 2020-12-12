package com.eric.startupmatching.project.treeview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.R
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.project.treeview.model.ParentModel
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import kotlinx.android.synthetic.main.item_parent.view.*

/**
 * Created by zzw on 2018/6/22.
 */
class ParentViewBinder : ItemViewBinder<ParentModel, ParentViewBinder.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_parent, parent, false)
        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: ParentModel) {
        holder.itemView.tvContent.text = model.content
        holder.itemView.setOnClickListener {
            when (model.expand) {
                true -> {
                    TreeViewUtil.rotationExpandIcon(holder.itemView.imgExpand, -180f, 0f)
                    mExpandAndHideListener.onHide(adapter, adapter.items as ArrayList<TreeViewModel>, model, getPosition(holder))
                }
                else -> {
                    TreeViewUtil.rotationExpandIcon(holder.itemView.imgExpand, 0f, -180f)
                    mExpandAndHideListener.onExpand(adapter, adapter.items as ArrayList<TreeViewModel>, model, getPosition(holder))
                }
            }
            model.expand = !model.expand
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

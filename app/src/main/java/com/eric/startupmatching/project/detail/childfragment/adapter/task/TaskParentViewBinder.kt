package com.eric.startupmatching.project.detail.childfragment.adapter.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.R
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import kotlinx.android.synthetic.main.item_parent.view.*

class TaskParentViewBinder : ItemViewBinder<TaskParentModel, TaskParentViewBinder.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_parent, parent, false)
        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
        return ViewHolder(
            root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TaskParentModel) {
        holder.itemView.tvContent.text = model.content.name
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

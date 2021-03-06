package com.eric.startupmatching.project.running.child.viewbinder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.ItemProjectRunParentBinding
import com.eric.startupmatching.project.edit.ProjectEditTaskParentAdapter
import com.eric.startupmatching.project.running.child.ProjectRunningTaskViewModel
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import kotlinx.android.synthetic.main.item_parent.view.*


class RunTaskParentViewBinder(val viewModel: ProjectRunningTaskViewModel, val onClickListener: OnClickListener) : ItemViewBinder<TaskParentModel, RunTaskParentViewBinder.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectRunParentBinding.inflate(layoutInflater)
        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
        return ViewHolder(binding, viewModel, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TaskParentModel) {
        holder.bind(model)
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

    class ViewHolder(var binding: ItemProjectRunParentBinding,
                     val viewModel: ProjectRunningTaskViewModel,
                     var onClickListener: OnClickListener) : RecyclerView.ViewHolder(binding.root) {


        fun bind(model: TaskParentModel) {
            binding.model = model
            binding.executePendingBindings()

            binding.chatroom.setOnClickListener {
                model.content.chatRoom?.let { it1 -> onClickListener.onClick(it1) }
            }
            Log.d("binding", model.toString())

        }
    }

    class OnClickListener(val clickListener: (chatRoomId: String) -> Unit) {
        fun onClick(chatRoomId: String) = clickListener(chatRoomId)
    }
}

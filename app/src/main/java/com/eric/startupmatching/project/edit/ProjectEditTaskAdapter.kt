package com.eric.startupmatching.project.edit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.databinding.ItemProjectEditTaskRecyclerViewBinding
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import kotlinx.android.synthetic.main.item_parent.view.*

class ProjectEditTaskAdapter(var viewModel: ProjectEditTaskViewModel, val onClickListener: OnClickListener) : ItemViewBinder<TaskParentModel, ProjectEditTaskAdapter.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    class ViewHolder(var binding: ItemProjectEditTaskRecyclerViewBinding,
                     var viewModel: ProjectEditTaskViewModel,
                     var onClickListener: OnClickListener) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TaskParentModel) {
            binding.addTodo.setOnClickListener { onClickListener.onClick(model) }
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectEditTaskRecyclerViewBinding.inflate(layoutInflater, parent, false)

//        val root = inflater.inflate(R.layout.item_parent, parent, false)
        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
//        return ViewHolder(root)
        return ViewHolder(binding, viewModel, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TaskParentModel) {
        holder.itemView.tvContent.text = model.content.name
        holder.itemView.setOnClickListener {
            when (model.expand) {
                true -> {
                    TreeViewUtil.rotationExpandIcon(holder.itemView.imgExpand, -180f, 0f)
                    mExpandAndHideListener.onHide(adapter, adapter.items as ArrayList<TreeViewModel>, model, getPosition(holder))
                    holder.bind(model)
                }
                else -> {
                    TreeViewUtil.rotationExpandIcon(holder.itemView.imgExpand, 0f, -180f)
                    mExpandAndHideListener.onExpand(adapter, adapter.items as ArrayList<TreeViewModel>, model, getPosition(holder))
                    holder.bind(model)
                }
            }
            model.expand = !model.expand
        }
    }

    class OnClickListener(val clickListener: (model: TaskParentModel) -> Unit) {
        fun onClick(model: TaskParentModel) = clickListener(model)
    }
}
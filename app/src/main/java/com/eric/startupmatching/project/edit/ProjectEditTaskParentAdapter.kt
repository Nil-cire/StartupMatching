package com.eric.startupmatching.project.edit

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.databinding.ItemProjectEditTaskRecyclerViewBinding
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import kotlinx.android.synthetic.main.item_parent.view.*
import kotlinx.android.synthetic.main.item_parent.view.imgExpand
import kotlinx.android.synthetic.main.item_parent.view.tvContent
import kotlinx.android.synthetic.main.item_project_edit_task_recycler_view.view.*

class ProjectEditTaskParentAdapter(var viewModel: ProjectEditTaskViewModel, val onClickListener: OnClickListener) : ItemViewBinder<TaskParentModel, ProjectEditTaskParentAdapter.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    class ViewHolder(var binding: ItemProjectEditTaskRecyclerViewBinding,
                     var viewModel: ProjectEditTaskViewModel,
                     var onClickListener: OnClickListener,
                     var holderType: Int) : RecyclerView.ViewHolder(binding.root) {

        val description = MutableLiveData<String>()
        fun bind(model: TaskParentModel) {

            holderType = 0
            binding.model = model
            binding.addTodo.setOnClickListener { onClickListener.onClick(model) }
            binding.confirmButton.setOnClickListener {
                var description = binding.detailText.text.toString()
                viewModel.editTaskDescription(model.content, description)
                binding.detailText.text= null}

        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectEditTaskRecyclerViewBinding.inflate(layoutInflater, parent, false)

//        val root = inflater.inflate(R.layout.item_parent, parent, false)
        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
//        return ViewHolder(root)
        return ViewHolder(binding, viewModel, onClickListener, 0)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TaskParentModel) {
        holder.itemView.itemCount.text = (model.content.serial?.plus(1)).toString()
        holder.itemView.tvContent.text = model.content.name
        holder.bind(model)
        holder.itemView.detail_text.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                holder.itemView.confirm_button.visibility = View.GONE
            } else {
                holder.itemView.confirm_button.visibility = View.VISIBLE
            }
        }
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

    class OnClickListener(val clickListener: (model: TaskParentModel) -> Unit) {
        fun onClick(model: TaskParentModel) = clickListener(model)
    }
}
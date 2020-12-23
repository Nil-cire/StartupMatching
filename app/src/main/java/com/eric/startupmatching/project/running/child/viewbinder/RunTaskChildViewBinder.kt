package com.eric.startupmatching.project.running.child.viewbinder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.R
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.TodoStatus
import com.eric.startupmatching.databinding.ItemProjectRunChildBinding
import com.eric.startupmatching.project.running.child.ProjectRunningTaskViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import kotlinx.android.synthetic.main.item_child.view.*

class RunTaskChildViewBinder(val viewModel: ProjectRunningTaskViewModel) : ItemViewBinder<TaskChildModel, RunTaskChildViewBinder.ViewHolder>() {

    class ViewHolder(var binding: ItemProjectRunChildBinding, var viewModel: ProjectRunningTaskViewModel) : RecyclerView.ViewHolder(binding.root) {
        class OnClickChatListener(val clickListener: (project: Project) -> Unit) {
            fun onClick(product: Project) = clickListener(product)
        }

        fun bind(model: TaskChildModel) {
            binding.model = model
            when (model.content.status) {
                "running" -> binding.imgExpand.setBackgroundResource(R.drawable.baseline_panorama_fish_eye_black_18dp)
                "done" -> binding.imgExpand.setBackgroundResource(R.drawable.baseline_task_alt_black_18dp)
            }
            Log.d("model", model.content.status!!)
            binding.executePendingBindings()
            // check to-do list
            binding.imgExpand.setOnClickListener {
                if (model.content.status == TodoStatus.Running.status) {
                    viewModel.updateTodoStatusToDone(model.content)
                    model.content.status = TodoStatus.Done.status
                    it.setBackgroundResource(R.drawable.baseline_task_alt_black_18dp)
                } else {
                    viewModel.updateTodoStatusToRunning(model.content)
                    model.content.status = TodoStatus.Running.status
                    it.setBackgroundResource(R.drawable.baseline_panorama_fish_eye_black_18dp)
                }
            }
        }
    }



    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectRunChildBinding.inflate(layoutInflater)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TaskChildModel) {
//        holder.itemView.tvContent.text = child.content.name
        holder.bind(model)
    }

    class OnClickListener(val clickListener: (teamMember: Project) -> Unit) {
        fun onClick(project: Project) = clickListener(project)
    }
}
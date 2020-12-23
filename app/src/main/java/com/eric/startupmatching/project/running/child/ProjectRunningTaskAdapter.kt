package com.eric.startupmatching.project.running.child

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.ItemProjectMainRecyclerViewBinding

class ProjectRunningTaskAdapter(val onClickListener: OnClickListener) : ListAdapter<Project, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemProjectMainRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        class OnClickChatListener(val clickListener: (project: Project) -> Unit) {
            fun onClick(product: Project) = clickListener(product)
        }


        fun bind(project: Project) {
            binding.project = project
            binding.executePendingBindings()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectMainRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        val item = getItem(position)
//        holder.bind(item)

        if (holder is ViewHolder) {
            val project = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(project)
            }
            holder.bind(project)
        } else {
            Log.d("Boooo", "cant bind data")
        }

    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (teamMember: Project) -> Unit) {
        fun onClick(project: Project) = clickListener(project)
    }
}
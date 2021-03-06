package com.eric.startupmatching.project.edit

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.databinding.ItemProjectEditUserRecyclerViewBinding
import com.eric.startupmatching.project.treeview.model.team.TeamChildModel
import kotlinx.android.synthetic.main.item_project_edit_task_recycler_view.view.*
import kotlinx.android.synthetic.main.item_project_edit_task_recycler_view.view.confirm_button
import kotlinx.android.synthetic.main.item_project_edit_task_recycler_view.view.detail_text
import kotlinx.android.synthetic.main.item_project_edit_team_recycler_view.view.*


class ProjectEditTeamChildAdapter(var viewModel: ProjectEditTeamViewModel) : ItemViewBinder<TeamChildModel, ProjectEditTeamChildAdapter.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    class ViewHolder(var binding: ItemProjectEditUserRecyclerViewBinding,
                     var viewModel: ProjectEditTeamViewModel,
                     var holderType: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: TeamChildModel) {
            holderType = 1
            binding.confirmButton.setOnClickListener {
                Log.d("BTN", "Clicked")
                binding.model = model
                var description = binding.detailText.text.toString()
                binding.detailText.text = null}

        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectEditUserRecyclerViewBinding.inflate(layoutInflater, parent, false)

        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
        return ViewHolder(binding, viewModel, 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TeamChildModel) {
        holder.itemView.tvContent.text = model.content.name
        holder.bind(model)
        holder.itemView.detail_text.addTextChangedListener {
            if (it.isNullOrEmpty()) {
                holder.itemView.confirm_button.visibility = View.GONE
            } else {
                holder.itemView.confirm_button.visibility = View.VISIBLE
            }
        }
    }
}
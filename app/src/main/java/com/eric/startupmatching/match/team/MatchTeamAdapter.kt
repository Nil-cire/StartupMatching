package com.eric.startupmatching.match.team

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.databinding.ItemMatchTeamRecyclerViewBinding

class MatchTeamAdapter(val onClickListener: OnClickListener) : ListAdapter<Team, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class ViewHolder(var binding: ItemMatchTeamRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            binding.team = team
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMatchTeamRecyclerViewBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

//        val item = getItem(position)
//        holder.bind(item)

        if (holder is ViewHolder) {
            val team = getItem(position)
            holder.itemView.setOnClickListener {
                onClickListener.onClick(team)
            }
            holder.bind(team)
        } else {
            Log.d("Boooo", "cant bind data")
        }

    }

    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (team: Team) -> Unit) {
        fun onClick(team: Team) = clickListener(team)
    }
}
package com.eric.startupmatching.project.detail.childfragment.adapter.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.R
import com.eric.startupmatching.project.treeview.model.team.TeamChildModel
import kotlinx.android.synthetic.main.item_child.view.*

/**
 * Created by zzw on 2018/6/22.
 */
class TeamChildViewBinder : ItemViewBinder<TeamChildModel, TeamChildViewBinder.ViewHolder>() {


    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_child, parent, false)
        return ViewHolder(
            root
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, teamChild: TeamChildModel) {
        holder.itemView.tvContent.text = teamChild.content.name
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

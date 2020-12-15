package com.eric.startupmatching.project.detail.childfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.drakeet.multitype.MultiTypeAdapter
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.FragmentProjectDetailTeamBinding
import com.eric.startupmatching.project.detail.childfragment.adapter.team.TeamChildViewBinder
import com.eric.startupmatching.project.detail.childfragment.adapter.team.TeamParentViewBinder
import com.eric.startupmatching.project.treeview.model.team.TeamChildModel
import com.eric.startupmatching.project.treeview.model.team.TeamParentModel
import com.eric.startupmatching.project.treeview.model.TreeViewModel

class ProjectDetailTeamFragment(val arg: Project): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arg = arg
        val binding = FragmentProjectDetailTeamBinding.inflate(inflater, container, false)
        val viewModelFactory = ProjectDetailTeamViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailTeamViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = MultiTypeAdapter()
        adapter.register(
            TeamParentModel::class.java,
            TeamParentViewBinder()
        )
        adapter.register(
            TeamChildModel::class.java,
            TeamChildViewBinder()
        )

        binding.recyclerView.adapter = adapter

        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            Log.d("projectMemberListObsv", it.toString())
            viewModel.getProjectTeamMember(it)
        })

        viewModel.projectTeamMemberList.observe(viewLifecycleOwner, Observer {
            val items = mutableListOf<Any>()
            for (i in it.indices) {
                var member = mutableListOf<TreeViewModel>()
                val team = viewModel.teamList2.value?.get(i)?.let { it1 ->
                    TeamParentModel(it1)
                }
                for (j in it[i]) {
                    member.add(TeamChildModel(j))
                }
                if (team != null) {
                    team.children = member as ArrayList<TreeViewModel>
                    items.add(team)
                }
            }
            adapter.items = items
            adapter.notifyDataSetChanged()
            Log.d("adapteritems", items.toString())
        })

        return binding.root
    }
    override fun onStart() {
        super.onStart()
        val arg = arg
        val viewModelFactory = ProjectDetailTeamViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailTeamViewModel::class.java)
        viewModel.getProjectTeams(arg)
    }
}
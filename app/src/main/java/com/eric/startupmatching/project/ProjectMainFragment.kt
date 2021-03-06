package com.eric.startupmatching.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.data.ProjectStage
import com.eric.startupmatching.databinding.FragmentProjectMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_project_main.view.*

class ProjectMainFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectMainBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ProjectMainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ProjectMainAdapter(ProjectMainAdapter.OnClickListener{
            when (it.startupStatus) {
                ProjectStage.Preparing.stage -> this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectDetailFragment(it))
                ProjectStage.Running.stage -> this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectRunningFragment(it))
                ProjectStage.Done.stage -> this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectDetailFragment(it))
            }
        })
        binding.recyclerView.adapter = adapter

        // init data
        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            viewModel.getAllProject()
        })

        viewModel.projectList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        val asProjectOwnerBtn = binding.projectOwner
        val asTeamLeaderBtn = binding.teamLeader
        val preparingBtn = binding.preparing
        val processBtn = binding.processing
        val historyBtn = binding.history

        // filter logic
        asProjectOwnerBtn.setOnClickListener {
            if (!viewModel.projectOwnerBtnChecked.value!!) {
                viewModel.projectOwnerBtnSelect()
                viewModel.teamLeaderBtnUnSelect()
            } else {
                viewModel.projectOwnerBtnUnSelect()
            }

        }

        asTeamLeaderBtn.setOnClickListener {
            if (!viewModel.teamLeaderBtnChecked.value!!) {
                viewModel.projectOwnerBtnUnSelect()
                viewModel.teamLeaderBtnSelect()
            } else {
                viewModel.teamLeaderBtnUnSelect()
            }
        }

        preparingBtn.setOnClickListener {
            if (!viewModel.preparingBtnChecked.value!!) {
                viewModel.preparingBtnSelect()
                viewModel.processBtnUnSelect()
                viewModel.historyBtnUnSelect()
            } else {
                viewModel.preparingBtnUnSelect()
            }
        }

        processBtn.setOnClickListener {
            if (!viewModel.processBtnChecked.value!!) {
                viewModel.preparingBtnUnSelect()
                viewModel.processBtnSelect()
                viewModel.historyBtnUnSelect()
            } else {
                viewModel.processBtnUnSelect()
            }
        }

        historyBtn.setOnClickListener {
            if (!viewModel.historyBtnChecked.value!!) {
                viewModel.preparingBtnUnSelect()
                viewModel.processBtnUnSelect()
                viewModel.historyBtnSelect()
            } else {
                viewModel.historyBtnUnSelect()
            }
        }

        // observe click status

        viewModel.projectOwnerBtnChecked.observe(viewLifecycleOwner, Observer {
            var list = viewModel.projectFilter()
            adapter.submitList(list)
        })

        viewModel.teamLeaderBtnChecked.observe(viewLifecycleOwner, Observer {
            var list = viewModel.projectFilter()
            adapter.submitList(list)
        })

        viewModel.preparingBtnChecked.observe(viewLifecycleOwner, Observer {
            var list = viewModel.projectFilter()
            adapter.submitList(list)
        })

        viewModel.processBtnChecked.observe(viewLifecycleOwner, Observer {
            var list = viewModel.projectFilter()
            adapter.submitList(list)
        })

        viewModel.historyBtnChecked.observe(viewLifecycleOwner, Observer {
            var list = viewModel.projectFilter()
            adapter.submitList(list)
        })

        requireActivity().project_main_add.setOnClickListener {
            Log.d("project_main_add", "pressed")
            this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectAddFragment())
        }


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val viewModel = ViewModelProvider(this).get(ProjectMainViewModel::class.java)
        viewModel.getUserTeam()

        requireActivity().project_main_add.visibility = View.VISIBLE

    }

    override fun onStop() {
        super.onStop()
        requireActivity().project_main_add.visibility = View.GONE
    }
}
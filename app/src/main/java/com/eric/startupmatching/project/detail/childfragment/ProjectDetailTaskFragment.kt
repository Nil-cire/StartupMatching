package com.eric.startupmatching.project.detail.childfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.FragmentProjectDetailTaskBinding

class ProjectDetailTaskFragment(val arg: Project): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arg = arg
        val binding = FragmentProjectDetailTaskBinding.inflate(inflater, container, false)
        val viewModelFactory = ProjectDetailTaskViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailTaskViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ProjectDetailTeamAdapter(ProjectDetailTeamAdapter.OnClickListener{})
        binding.recyclerView.adapter = adapter
        return binding.root
    }
}
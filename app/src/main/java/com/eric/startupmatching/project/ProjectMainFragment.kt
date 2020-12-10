package com.eric.startupmatching.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentProjectMainBinding

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
        val adapter = ProjectMainAdapter(ProjectMainAdapter.OnClickListener{})
        binding.recyclerView.adapter = adapter

        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            viewModel.getAllProject()
        })

        viewModel.projectList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val viewModel = ViewModelProvider(this).get(ProjectMainViewModel::class.java)
        viewModel.getUserTeam()
    }
}
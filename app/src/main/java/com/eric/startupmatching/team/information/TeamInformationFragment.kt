package com.eric.startupmatching.team.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentTeamInformationBinding
import com.eric.startupmatching.databinding.FragmentTeamTeamstatusBinding
import com.eric.startupmatching.team.teamstatus.TeamStatusAdapter
import com.eric.startupmatching.team.teamstatus.TeamStatusViewModel

class TeamInformationFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamInformationBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(TeamInformationViewModel::class.java)

        val adapter = TeamInformationAdapter(TeamInformationAdapter.OnClickListener{
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = adapter

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.getTeamMember()

        return binding.root
    }
}
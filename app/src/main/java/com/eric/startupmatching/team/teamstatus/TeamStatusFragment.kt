package com.eric.startupmatching.team.teamstatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.databinding.FragmentTeamTeamstatusBinding

class TeamStatusFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamTeamstatusBinding.inflate(inflater)
        val viewModel = ViewModelProvider(this).get(TeamStatusViewModel::class.java)

        val adapter = TeamStatusAdapter(TeamStatusAdapter.OnClickListener{
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = adapter

        viewModel.teamMemberList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.getTeamMember()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val viewModel = ViewModelProvider(this).get(TeamStatusViewModel::class.java)
        viewModel.getTeamMember()

    }
}
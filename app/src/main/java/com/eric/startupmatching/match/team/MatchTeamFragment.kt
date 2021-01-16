package com.eric.startupmatching.match.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentMatchTeamBinding

class MatchTeamFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchTeamBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(MatchTeamViewModel::class.java)
        val adapter = MatchTeamAdapter(MatchTeamAdapter.OnClickListener{ })
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.teamSearchingRecyclerView.adapter = adapter

        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }
}
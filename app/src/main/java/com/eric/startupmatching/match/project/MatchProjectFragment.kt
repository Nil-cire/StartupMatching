package com.eric.startupmatching.match.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentMatchProjectBinding

class MatchProjectFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchProjectBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(MatchProjectViewModel::class.java)
        val adapter = MatchProjectAdapter(MatchProjectAdapter.OnClickListener{ })
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.teamSearchingRecyclerView.adapter = adapter

        viewModel.projectList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        return binding.root
    }
}
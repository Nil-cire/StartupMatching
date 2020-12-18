package com.eric.startupmatching.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.databinding.FragmentPeopleSearchBinding
import kotlinx.android.synthetic.main.activity_main.*

class PersonMainFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPeopleSearchBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(PersonMainViewModel::class.java)
        val adapter = PersonMainAdapter(PersonMainAdapter.OnClickListener{
            this.findNavController().navigate(MainNavigationDirections.actionGlobalPersonDetailFragment(it))
        })
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = adapter

//        UserInfo.currentUser.id?.let { viewModel.observeNewMessage(it) }

        viewModel.userList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().header.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().header.visibility = View.VISIBLE
    }
}
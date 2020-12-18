package com.eric.startupmatching.person.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentPeopleSearchBinding
import com.eric.startupmatching.databinding.FragmentPersonDetailBinding
import com.eric.startupmatching.person.PersonMainAdapter
import com.eric.startupmatching.person.PersonMainViewModel

class PersonDetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userArgs = PersonDetailFragmentArgs.fromBundle(requireArguments()).userArgs
        val binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        val viewModelFactory = PersonDetailViewModelFactory(userArgs)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(PersonDetailViewModel::class.java)
        val adapter = PersonMainAdapter(PersonMainAdapter.OnClickListener{
//            this.findNavController().navigate(MainNavigationDirections.actionGlobalChatroomDetailFragment(it))
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
}
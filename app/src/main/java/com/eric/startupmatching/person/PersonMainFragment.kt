package com.eric.startupmatching.person

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.data.User
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

        //searching feature
        binding.searchBar.addTextChangedListener {
            val userList = mutableListOf<User>()
            var text = binding.searchBar.text.toString()
            if (!viewModel.userList.value.isNullOrEmpty()) {
                for (user in viewModel.userList.value!!) {
                    if (user != null) {
                        user.skills?.let {
                            for (skill in it) {
                                if (skill.toString().contains(text, ignoreCase = true)) {
                                    if (user !in userList) {
                                        userList.add(user)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            adapter.submitList(userList)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val viewModel = ViewModelProvider(this).get(PersonMainViewModel::class.java)
        viewModel.getAllUser()
        requireActivity().header.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        requireActivity().header.visibility = View.VISIBLE
    }
}
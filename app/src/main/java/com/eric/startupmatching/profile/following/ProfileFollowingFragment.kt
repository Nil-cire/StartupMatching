package com.eric.startupmatching.profile.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.databinding.FragmentProfileFollowingBinding

class ProfileFollowingFragment: Fragment() {

    lateinit var viewModel: ProfileFollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileFollowingBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ProfileFollowingViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = ProfileFollowingRecyclerViewAdapter(viewModel, ProfileFollowingRecyclerViewAdapter.OnClickListener{
//            this.findNavController().navigate(MainNavigationDirections.actionGlobalChatRoomDetailFragment())
        })
        binding.recyclerView.adapter = adapter

        viewModel.followingIdList.observe(viewLifecycleOwner, Observer {
            viewModel.getFollowingUsers(it as List<String>)
        })  // 2. Get Following user instances by ids

        viewModel.followingUsers.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.chatUser.observe(viewLifecycleOwner, Observer {
            viewModel.checkIfUserRoomExist(UserInfo.currentUser.value!!, it)
//            this.findNavController().navigate(MainNavigationDirections.actionGlobalChatRoomDetailFragment(it.id))
        })

        viewModel.checkChatRoom.observe(viewLifecycleOwner, Observer {
            if (it == null) {
                viewModel.createChatRoom()
            } else {
                this.findNavController().navigate(MainNavigationDirections.actionGlobalChatRoomDetailFragment(it.id!!))
            }
        })

        viewModel.chatRoomId.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalChatRoomDetailFragment(it))
        })



        return binding.root
    }

    override fun onResume() {
        super.onResume()
        UserInfo.currentUser.value?.let {
            viewModel.getFollowingList(UserInfo.currentUser.value!!)  // 1. get user followingList
        }
    }
}
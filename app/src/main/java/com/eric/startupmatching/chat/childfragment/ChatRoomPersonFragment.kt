package com.eric.startupmatching.chat.childfragment

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
import com.eric.startupmatching.databinding.FragmentChatRoomPersonBinding

class ChatRoomPersonFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomPersonBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ChatRoomPersonViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ChatRoomPersonAdapter(ChatRoomPersonAdapter.OnClickListener{
            this.findNavController().navigate(MainNavigationDirections.actionGlobalChatRoomDetailFragment(it.id!!))
        })
        binding.recyclerView.adapter = adapter

        viewModel.chatRoomList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val viewModel = ViewModelProvider(this).get(ChatRoomPersonViewModel::class.java)
        viewModel.getPrivateChatRoom(UserInfo.currentUser.value!!)
    }
}


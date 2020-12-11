package com.eric.startupmatching.chat.childfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentChatRoomTeamBinding
import com.eric.startupmatching.databinding.FragmentProjectDetailTeamBinding
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTeamAdapter
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTeamViewModel

class ChatRoomTeamFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomTeamBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ChatRoomTeamViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ChatRoomTeamAdapter(ChatRoomTeamAdapter.OnClickListener{})
        binding.recyclerView.adapter = adapter
        return binding.root
    }
}
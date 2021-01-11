package com.eric.startupmatching.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eric.startupmatching.chat.childfragment.ChatRoomPersonFragment
import com.eric.startupmatching.chat.childfragment.ChatRoomTaskFragment
import com.eric.startupmatching.chat.childfragment.ChatRoomTeamFragment
import com.eric.startupmatching.databinding.FragmentChatRoomMainBinding
import com.google.android.material.tabs.TabLayout

class ChatRoomMainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomMainBinding.inflate(inflater, container, false)

        val tabs = binding.tabsAssistant
        val viewPager = binding.viewpagerAssistant

        val viewpagerAdapter =  ChatRoomMainViewPagerAdapter(childFragmentManager)

        binding.lifecycleOwner = this

        viewpagerAdapter.addFragment(ChatRoomPersonFragment(),"個人")
        viewpagerAdapter.addFragment(ChatRoomTeamFragment(),"團隊")
        viewpagerAdapter.addFragment(ChatRoomTaskFragment(),"任務")

        viewPager.adapter = viewpagerAdapter

        val titleList = listOf("個人", "團隊", "任務")
        for (title in titleList) {
            tabs.addTab((tabs.newTab().setText(title)))
        }

        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

        return binding.root
    }

}
package com.eric.startupmatching.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.ProfileViewpagerCatagory
import com.eric.startupmatching.data.ToolBarText
import com.eric.startupmatching.databinding.FragmentProfileBinding
import com.eric.startupmatching.profile.following.ProfileFollowingFragment
import com.eric.startupmatching.team.information.TeamInformationFragment
import com.eric.startupmatching.team.teamstatus.TeamStatusFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val tabs = binding.tabsAssistant
        val viewPager = binding.viewpagerAssistant

        val viewpagerAdapter = ProfileViewpagerAdapter(childFragmentManager)

        binding.lifecycleOwner = this

        viewpagerAdapter.addFragment(TeamStatusFragment(),"成員")
        viewpagerAdapter.addFragment(ProfileFollowingFragment(), ProfileViewpagerCatagory.FollowingList.catagory)

        viewPager.adapter = viewpagerAdapter

        val titleList = listOf("成員",  ProfileViewpagerCatagory.FollowingList.catagory)
        for (title in titleList) {
            tabs.addTab((tabs.newTab().setText(title)))
        }

        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().notification_icon.visibility = View.VISIBLE
        requireActivity().toolBar_text.text = ToolBarText.ProfileMain.type
    }

    override fun onPause() {
        super.onPause()
        requireActivity().notification_icon.visibility = View.GONE
    }
}
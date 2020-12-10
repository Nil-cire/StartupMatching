package com.eric.startupmatching.team

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
import com.eric.startupmatching.databinding.FragmentTeamMainBinding
import com.eric.startupmatching.team.information.TeamInformationFragment
import com.eric.startupmatching.team.teamstatus.TeamStatusFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_team_main.*

class TeamMainFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTeamMainBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this).get(TeamMainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val tabs = binding.tabsAssistant
        val viewPager = binding.viewpagerAssistant

        val viewpagerAdapter = TeamMainAdapter(childFragmentManager)
        if(UserInfo.currentUser.value?.workingStatus == "離開") {
            binding.switch1.text = "離開"
            binding.switch1.isChecked = true
        } else {
            binding.switch1.text = "回來"
            binding.switch1.isChecked = false
        }

        binding.switch1.text = "離開"
        binding.switch1.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                buttonView.text = "離開"
            } else {
                buttonView.text = "回來"
            }
        }

        binding.lifecycleOwner = this

        viewpagerAdapter.addFragment(TeamStatusFragment(),"成員")
        viewpagerAdapter.addFragment(TeamInformationFragment(),"動態")

        viewPager.adapter = viewpagerAdapter

        val titleList = listOf("成員", "動態")
        for (title in titleList) {
            tabs.addTab((tabs.newTab().setText(title)))
        }

        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

//        binding.teamMemberRecyclerView.adapter = adapter

//        UserInfo.currentUser.id?.let { viewModel.observeMemberStatus() }

//        viewModel.teamList.observe(viewLifecycleOwner, Observer {
//            if (!it.isNullOrEmpty()) {
//                adapter.submitList(it)
//                binding.joinBtn.text = "團隊資訊"
//            }
//        })
//
//        binding.joinBtn.setOnClickListener {
//            if (binding.joinBtn.text != "團隊資訊") {
//                TODO() // navigation
//            }
//        }

        binding.textView.setOnClickListener {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalBackStageFragment())
        }

        return binding.root
    }
}
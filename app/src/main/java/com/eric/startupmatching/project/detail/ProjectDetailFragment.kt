package com.eric.startupmatching.project.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.databinding.FragmentProjectDetailBinding
import com.eric.startupmatching.databinding.FragmentTeamMainBinding
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTaskFragment
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTeamFragment
import com.eric.startupmatching.team.TeamMainAdapter
import com.eric.startupmatching.team.TeamMainViewModel
import com.eric.startupmatching.team.information.TeamInformationFragment
import com.eric.startupmatching.team.teamstatus.TeamStatusFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_team_main.*
import kotlinx.android.synthetic.main.item_project_edit_task.view.*

class ProjectDetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectDetailBinding.inflate(inflater, container, false)

        val arg = ProjectDetailFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectDetailViewModelFractory(arg)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val tabs = binding.tabsAssistant
        val viewPager = binding.viewpagerAssistant

        val viewpagerAdapter = TeamMainAdapter(childFragmentManager)

        binding.lifecycleOwner = this

        viewpagerAdapter.addFragment(ProjectDetailTaskFragment(arg),"任務")
        viewpagerAdapter.addFragment(ProjectDetailTeamFragment(arg),"團隊")

        viewPager.adapter = viewpagerAdapter

        val titleList = listOf("任務", "團隊")
        for (title in titleList) {
            tabs.addTab((tabs.newTab().setText(title)))
        }

        tabs.setupWithViewPager(viewPager)

        class MViewPagerAdapter(tabLayout: TabLayout) : TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    1 -> requireActivity().project_detail_edit.text = "編輯團隊"
                    else -> requireActivity().project_detail_edit.text = "編輯任務"
                }
            }
        }

        viewPager.addOnPageChangeListener(MViewPagerAdapter(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(viewPager))

        requireActivity().project_detail_edit.setOnClickListener {
            if(it is TextView && it.text == "編輯任務") {
                this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectEditTaskFragment(arg))
            } else {
                this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectEditTeamFragment(arg))
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val arg = ProjectDetailFragmentArgs.fromBundle(requireArguments()).projectArgs
        requireActivity().project_detail_edit.visibility = View.VISIBLE


    }

    override fun onPause() {
        super.onPause()
        requireActivity().project_detail_edit.visibility = View.GONE
    }
}
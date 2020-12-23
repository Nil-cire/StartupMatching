package com.eric.startupmatching.project.running

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.ViewPagerType
import com.eric.startupmatching.databinding.FragmentProjectRunningBinding
import com.eric.startupmatching.project.detail.ProjectDetailFragmentArgs
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTeamFragment
import com.eric.startupmatching.project.running.child.ProjectRunningTaskFragment
import com.eric.startupmatching.team.TeamMainAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class ProjectRunningFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectRunningBinding.inflate(inflater, container, false)

        val arg = ProjectDetailFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectRunningViewModelFactory(arg)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectRunningViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val tabs = binding.tabsAssistant
        val viewPager = binding.viewpagerAssistant

        val viewpagerAdapter = TeamMainAdapter(childFragmentManager)

        binding.lifecycleOwner = this

        viewpagerAdapter.addFragment(ProjectRunningTaskFragment(arg), ViewPagerType.Task.type)
        viewpagerAdapter.addFragment(ProjectDetailTeamFragment(arg),ViewPagerType.Team.type)

        viewPager.adapter = viewpagerAdapter

        val titleList = listOf(ViewPagerType.Task.type, ViewPagerType.Team.type)
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
        val arg = ProjectDetailFragmentArgs.fromBundle(requireArguments()).projectArgs
        requireActivity().project_detail_edit.visibility = View.VISIBLE


    }

    override fun onPause() {
        super.onPause()
        requireActivity().project_detail_edit.visibility = View.GONE
    }
}
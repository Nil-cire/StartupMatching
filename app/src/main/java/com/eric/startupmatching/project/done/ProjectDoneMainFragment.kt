package com.eric.startupmatching.project.done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.ViewPagerType
import com.eric.startupmatching.databinding.FragmentProjectDoneBinding
import com.eric.startupmatching.databinding.FragmentProjectRunningBinding
import com.eric.startupmatching.project.detail.ProjectDetailFragmentArgs
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTeamFragment
import com.eric.startupmatching.project.running.ProjectRunningViewModel
import com.eric.startupmatching.project.running.ProjectRunningViewModelFactory
import com.eric.startupmatching.project.running.child.ProjectRunningTaskFragment
import com.eric.startupmatching.team.TeamMainAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*

class ProjectDoneMainFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectDoneBinding.inflate(inflater, container, false)

        val projectArgs = ProjectDoneMainFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectDoneMainViewModelFactory(projectArgs)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDoneMainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
//        val arg = ProjectDetailFragmentArgs.fromBundle(requireArguments()).projectArgs
//        requireActivity().project_detail_edit.visibility = View.VISIBLE


    }

    override fun onPause() {
        super.onPause()
//        requireActivity().project_detail_edit.visibility = View.GONE
    }
}
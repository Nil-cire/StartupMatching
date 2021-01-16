package com.eric.startupmatching.project.done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.databinding.FragmentProjectDoneBinding
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

        binding.postIt.setOnClickListener {
            var content = binding.shareText.text.toString()
            var imageUrl = "" // await for image function
            viewModel.postProject(content, imageUrl)
        }

        // observe post added on firebase -> navigate to social media post "Main" page
        viewModel.addPostComplete.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(MainNavigationDirections.actionGlobalSocialMediaMainFragment())
            }
        })

        // create achievement when users in profile get and set userId in achievement
        viewModel.userIdList.observe(viewLifecycleOwner, Observer {
            viewModel.createAchievementForProject(projectArgs, it)
        })

        // add achievement to all users
        viewModel.achievementId.observe(viewLifecycleOwner, Observer {
            viewModel.addAchievementIdToUsers(viewModel.userIdList.value!!, it)
        })

        return binding.root
    }
}
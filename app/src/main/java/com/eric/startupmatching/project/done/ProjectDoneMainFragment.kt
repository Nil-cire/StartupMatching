package com.eric.startupmatching.project.done

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

        binding.sendPost.setOnClickListener {
            var content = binding.shareText.text.toString()
            var imageUrl = "" // await for image function
            viewModel.postProject(content, imageUrl)
        }

        // observe post added on firebase -> navigate to social media post detail page
        viewModel.addPostComplete.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                // TODO do navigation
            }
        })

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
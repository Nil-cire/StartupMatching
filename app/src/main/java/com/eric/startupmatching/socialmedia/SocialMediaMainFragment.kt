package com.eric.startupmatching.socialmedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.databinding.FragmentSocialMediaMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class SocialMediaMainFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this).get(SocialMediaMainViewModel::class.java)
        val binding = FragmentSocialMediaMainBinding.inflate(inflater, container, false)
        val postAdapter = SocialMediaMainPostAdapter(SocialMediaMainPostAdapter.OnClickListener{  })
        val user = UserInfo.currentUser.value
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.postRecyclerView.adapter = postAdapter

        viewModel.postList.observe(viewLifecycleOwner, Observer {
            Log.d("postListToSubmit", it.toString())
            postAdapter.submitList(it)

        })

        if (user != null) {
            viewModel.observeNewPosts(user.id!!)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().assistant.alpha = 1.0f
    }

    override fun onPause() {
        super.onPause()
        requireActivity().assistant.alpha = 0.3f
    }

}
package com.eric.startupmatching.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentProjectMainBinding
import com.eric.startupmatching.project.ProjectMainViewModel

class UserProfileFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectMainBinding.inflate(inflater, container, false)
//        val arg = UserProfileFragmentArgs.
//        val viewModelFactory = UserProfileViewModelFactory(arg)
//        val viewModel = UserProfileViewModel(this).get(UserProfileViewModel::class.java)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel

        return binding.root
    }
}
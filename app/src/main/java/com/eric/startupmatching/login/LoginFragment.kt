package com.eric.startupmatching.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.databinding.FragmentLoginMainBinding

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginMainBinding.inflate(inflater, container, false)
        binding.loginBtn.setOnClickListener {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalTeamMainFragment())
        }
        return binding.root
    }
}
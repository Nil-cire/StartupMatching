package com.eric.startupmatching.match

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.databinding.FragmentMatchMainBinding

class MatchMainFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMatchMainBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        binding.project.setOnClickListener {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalMatchProjectFragment())
        }

        binding.team.setOnClickListener {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalMatchTeamFragment())
        }

        return binding.root
    }
}
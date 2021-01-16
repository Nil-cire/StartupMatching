package com.eric.startupmatching.person.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.R
import com.eric.startupmatching.data.FollowStatus
import com.eric.startupmatching.databinding.FragmentPeopleSearchBinding
import com.eric.startupmatching.databinding.FragmentPersonDetailBinding
import com.eric.startupmatching.person.ItemPersonSkillRecyclerViewAdapter
import com.eric.startupmatching.person.PersonMainAdapter
import com.eric.startupmatching.person.PersonMainViewModel

class PersonDetailFragment: Fragment() {
    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val userArgs = PersonDetailFragmentArgs.fromBundle(requireArguments()).userArgs
        val binding = FragmentPersonDetailBinding.inflate(inflater, container, false)
        val viewModelFactory = PersonDetailViewModelFactory(userArgs)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(PersonDetailViewModel::class.java)
        val adapter = ItemPersonSkillRecyclerViewAdapter(ItemPersonSkillRecyclerViewAdapter.OnClickListener{})
        val adapter2 = PersonDetailAchievementAdapter(PersonDetailAchievementAdapter.OnClickListener{ })
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.adapter = adapter
        binding.recyclerView2.adapter = adapter2

        adapter.submitList(userArgs.skills)

        viewModel.followBtnTexChecker()

        binding.followBtnText.setOnClickListener {
            if (!viewModel.followed.value!!) {
                viewModel.follow(userArgs.id!!)
                Log.d("follow", "follow")
            } else {
                viewModel.unFollow(userArgs.id!!)
                Log.d("unFollow", "unFollow")
            }
        }

        viewModel.followed.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                binding.followBtnText.text = FollowStatus.Followed.type
                binding.followBtn.setBackgroundResource(R.drawable.round_corner)
                binding.followBtnText.setTextColor(R.color.black)
            } else {
                binding.followBtnText.text = FollowStatus.Follow.type
                binding.followBtn.setBackgroundResource(R.drawable.round_corner_light_blue)
                binding.followBtnText.setTextColor(R.color.white)
            }
        })

        viewModel.achievementListSubmit.observe(viewLifecycleOwner, Observer {
            adapter2.submitList(it)
        })

        return binding.root
    }

}
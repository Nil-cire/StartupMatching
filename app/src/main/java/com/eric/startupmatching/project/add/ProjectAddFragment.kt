package com.eric.startupmatching.project.add

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.FragmentProjectAddBinding

class ProjectAddFragment: Fragment() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectAddBinding.inflate(inflater, container, false)
        val viewModel = ViewModelProvider(this).get(ProjectAddViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        var startTime = Calendar.getInstance()
        var endTime = Calendar.getInstance()

        binding.editTextTextPersonName2.setOnClickListener {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(requireContext(),{_, year, month, day ->
            run {
                binding.editTextTextPersonName2.setText(setDateFormat(year, month, day))
                startTime.set(year, month, day)}
            },year,month,day).show()
        }


        binding.editTextTextPersonName3.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(),{_, year, month, day ->
                run {
                    binding.editTextTextPersonName3.setText(setDateFormat(year, month, day))
                    endTime.set(year, month, day)
                }
            },year,month,day).show()
        }

//        binding.editTextTextPersonName4.


        binding.constraintBot.setOnClickListener {
            viewModel.addTeam()
        }

        viewModel.addTeamId.observe(viewLifecycleOwner, Observer{

            val newProject = Project(
                id = null,
                projectName = binding.editTextTextPersonName.text.toString(),
                description = binding.editTextTextPersonName6.text.toString(),
                startupStatus = "planning",
                projectLeader = UserInfo.currentUser.value?.id,
                startTime = startTime.time,
                endTime = endTime.time
            )
            viewModel.addProject(newProject)
        })

        viewModel.addSuccess.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectDetailFragment(it))
        })

        return binding.root
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year-${month + 1}-$day"
    }
}
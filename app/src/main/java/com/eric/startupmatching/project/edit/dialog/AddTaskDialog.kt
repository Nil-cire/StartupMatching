package com.eric.startupmatching.project.edit.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.DialogAddTaskBinding
import com.eric.startupmatching.project.edit.ProjectEditTaskViewModel
import com.eric.startupmatching.project.edit.ProjectEditTaskViewModelFactory
import kotlinx.android.synthetic.main.fragment_match_main.*
import kotlinx.android.synthetic.main.item_project_edit_task.view.*
import java.util.*

class AddTaskDialog(val viewModel: ProjectEditTaskViewModel) : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val project = Project()
        val binding = DialogAddTaskBinding.inflate(inflater, container, false)
//        val viewModelFactory = ProjectEditTaskViewModelFactory(project)
//        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTaskViewModel::class.java)

        var startTime = Calendar.getInstance()
        var startTime2: Date? = null
        var endTime = Calendar.getInstance()
        var endTime2: Date? = null

        binding.editTextTextPersonName2.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(),{_, year, month, day ->
                run {
                    binding.editTextTextPersonName2.text = setDateFormat(year, month, day)
                    startTime.set(year, month, day)
                    startTime2 = startTime.time}
            },year,month,day).show()
        }

        binding.editTextTextPersonName3.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(requireContext(),{_, year, month, day ->
                run {
                    binding.editTextTextPersonName3.text = setDateFormat(year, month, day)
                    endTime.set(year, month, day)
                    endTime2 = endTime.time
                }
            },year,month,day).show()
        }

        binding.submit.setOnClickListener {
            if (startTime2 != null && endTime2 != null) {
//                viewModel.getNewTaskState()
                // set task value
                viewModel.taskInstance.serial = viewModel.taskList.value?.size ?: 0
                viewModel.taskInstance.name = binding.editTextTextPersonName.text.toString()
                viewModel.taskInstance.startTime = startTime2
                viewModel.taskInstance.endTime = endTime2
                viewModel.taskInstance.description = binding.editTextTextPersonName6.text.toString()
                Log.d("taskInstance", viewModel.taskInstance.toString())
                viewModel.addTask(viewModel.taskInstance)
                dismiss()
            }

            binding.cancel.setOnClickListener {
                dismiss()
            }
        }

        return binding.root
    }
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year-${month + 1}-$day"
    }
}
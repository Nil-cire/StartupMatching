package com.eric.startupmatching.project.edit.dialog

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.eric.startupmatching.R
import com.eric.startupmatching.databinding.DialogAddTeamBinding
import com.eric.startupmatching.project.edit.ProjectEditTeamViewModel
import java.util.*

class AddTeamDialog(val viewModel: ProjectEditTeamViewModel) : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.custom_dialog)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogAddTeamBinding.inflate(inflater, container, false)

        binding.submit.setOnClickListener {
            // set task value
//            viewModel.taskInstance.serial = viewModel.taskList.value?.size ?: 0
//            viewModel.taskInstance.name = binding.editTextTextPersonName.text.toString()
//            viewModel.taskInstance.description = binding.editTextTextPersonName6.text.toString()
//            Log.d("taskInstance", viewModel.taskInstance.toString())
//            viewModel.addTask(viewModel.taskInstance)
//            dismiss()
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
    private fun setDateFormat(year: Int, month: Int, day: Int): String {
        return "$year-${month + 1}-$day"
    }
}
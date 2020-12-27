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
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.TodoStatus
import com.eric.startupmatching.databinding.DialogAddTodoBinding
import com.eric.startupmatching.project.edit.ProjectEditTaskViewModel
import java.util.*

class AddTodoDialog(val viewModel: ProjectEditTaskViewModel) : DialogFragment() {

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
        val project = Project()
        val binding = DialogAddTodoBinding.inflate(inflater, container, false)

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
//            if (startTime2 != null && endTime2 != null) {
//                viewModel.getNewTaskState()
                // set task value
                viewModel.todoInstance.serial = viewModel.todoSize.value ?: 0
                viewModel.todoInstance.name = binding.editTextTextPersonName.text.toString()
                viewModel.todoInstance.startTime = startTime2
                viewModel.todoInstance.endTime = endTime2
                viewModel.todoInstance.task = viewModel.todoTask.value?.id.toString()
                viewModel.todoInstance.description = binding.editTextTextPersonName6.text.toString()
                viewModel.todoInstance.status = TodoStatus.Running.status
                Log.d("todoInstance", viewModel.todoInstance.toString())
                viewModel.addTodo(viewModel.todoInstance)
                viewModel.todoAdded()
                dismiss()
//            }

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
package com.eric.startupmatching.project.running.dialog

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.eric.startupmatching.databinding.DialogConfirmProjectDoneBinding
import com.eric.startupmatching.project.running.child.ProjectRunningTaskViewModel

class ProjectDoneConfirmDialog(val viewModel: ProjectRunningTaskViewModel) : DialogFragment() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogConfirmProjectDoneBinding.inflate(inflater, container, false)

        binding.cancel.setOnClickListener {
            dismiss()
        }

        binding.confirm.setOnClickListener {
            viewModel.confirmProjectDone()
            dismiss()
        }

        return binding.root
    }
}
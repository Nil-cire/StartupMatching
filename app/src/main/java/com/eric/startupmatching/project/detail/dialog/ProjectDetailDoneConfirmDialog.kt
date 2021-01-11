package com.eric.startupmatching.project.detail.dialog

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.eric.startupmatching.R
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.databinding.DialogAddTeamBinding
import com.eric.startupmatching.databinding.DialogConfirmProjectDoneBinding
import com.eric.startupmatching.project.detail.ProjectDetailViewModel

class ProjectDetailDoneConfirmDialog(val viewModel: ProjectDetailViewModel) : DialogFragment() {

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
        val binding = DialogConfirmProjectDoneBinding.inflate(inflater, container, false)

        binding.confirm.setOnClickListener {
            viewModel.confirmProjectReady()
            dismiss()
        }

        binding.cancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }
}
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
import com.eric.startupmatching.databinding.DialogAddTaskBinding
import com.eric.startupmatching.databinding.DialogSelectMemberBinding
import com.eric.startupmatching.project.edit.ProjectEditTeamViewModel
import java.util.*

class SelectMemberDialog(val viewModel: ProjectEditTeamViewModel) : DialogFragment() {

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
        val binding = DialogSelectMemberBinding.inflate(inflater, container, false)
        val adapter = SelectMemberDialogAdapter(SelectMemberDialogAdapter.OnClickListener{
            viewModel.selectedFollowList.add(it.id.toString())
            Log.d("addOneMember", viewModel.selectedFollowList.toString())
        })
        binding.recyclerView.adapter = adapter
        viewModel.resetSelectedFollowList()
        viewModel.getFriendList()

        viewModel.followList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {list ->
            list.sortedBy { it.id }
            adapter.submitList(list)
        })

        binding.cancel.setOnClickListener {
            viewModel.resetSelectedFollowList()
            dismiss()
        }


        binding.submit.setOnClickListener {
            viewModel.selectedTeam?.let { it1 -> viewModel.addSelectedFollowList(it1) }
            Log.d("addTeamMember", viewModel.selectedTeam.toString())
            dismiss()
        }

        return binding.root
    }
}
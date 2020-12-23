package com.eric.startupmatching.project.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.project.edit.ProjectEditTeamViewModel


class ProjectRunningViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectEditTeamViewModel::class.java)) {
            return ProjectRunningViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
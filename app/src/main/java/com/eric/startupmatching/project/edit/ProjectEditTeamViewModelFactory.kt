package com.eric.startupmatching.project.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project

class ProjectEditTeamViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectEditTeamViewModel::class.java)) {
            return ProjectEditTeamViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
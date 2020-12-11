package com.eric.startupmatching.project.detail.childfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project

class ProjectDetailTeamViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDetailTeamViewModel::class.java)) {
            return ProjectDetailTeamViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

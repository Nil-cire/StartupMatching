package com.eric.startupmatching.project.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project

class ProjectEditTaskViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectEditTaskViewModel::class.java)) {
            return ProjectEditTaskViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
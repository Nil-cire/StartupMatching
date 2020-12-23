package com.eric.startupmatching.project.running.child

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project


class ProjectRunningTaskViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectRunningTaskViewModel::class.java)) {
            return ProjectRunningTaskViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
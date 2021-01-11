package com.eric.startupmatching.project.running

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project


class ProjectRunningViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectRunningViewModel::class.java)) {
            return ProjectRunningViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
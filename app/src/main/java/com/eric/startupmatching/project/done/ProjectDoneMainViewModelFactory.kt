package com.eric.startupmatching.project.done

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.project.running.ProjectRunningViewModel

class ProjectDoneMainViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDoneMainViewModel::class.java)) {
            return ProjectDoneMainViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package com.eric.startupmatching.project.detail.childfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.project.detail.ProjectDetailViewModel

class ProjectDetailTaskViewModelFactory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDetailTaskViewModel::class.java)) {
            return ProjectDetailTaskViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
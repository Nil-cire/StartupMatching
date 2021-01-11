package com.eric.startupmatching.project.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.Project

class ProjectDetailViewModelFractory (
    private val arg: Project
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProjectDetailViewModel::class.java)) {
            return ProjectDetailViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

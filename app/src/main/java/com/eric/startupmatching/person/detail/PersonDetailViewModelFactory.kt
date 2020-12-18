package com.eric.startupmatching.person.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.User

class PersonDetailViewModelFactory (
    private val arg: User
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PersonDetailViewModel::class.java)) {
            return PersonDetailViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
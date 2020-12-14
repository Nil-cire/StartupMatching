package com.eric.startupmatching.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.User

class UserProfileViewModelFactory (
    private val arg: User
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
            return UserProfileViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
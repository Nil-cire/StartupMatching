package com.eric.startupmatching.chat.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.data.ChatRoom
import com.eric.startupmatching.project.detail.ProjectDetailViewModel

class ChatRoomDetailViewModelFactory (
    private val arg: String
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatRoomDetailViewModel::class.java)) {
            return ChatRoomDetailViewModel(arg) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
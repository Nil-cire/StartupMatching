package com.eric.startupmatching.chat.childfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatRoomPersonViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _chatRoomList = MutableLiveData<List<ChatRoom?>>()
    val chatRoomList: LiveData<List<ChatRoom?>>
        get() = _chatRoomList

    var user = UserInfo.currentUser

    fun getPrivateChatRoom(user: User) {
        coroutineScope.launch {
            _chatRoomList.value = FirebaseDataSource.getPrivateChatRoom(user)
        }
    }
}
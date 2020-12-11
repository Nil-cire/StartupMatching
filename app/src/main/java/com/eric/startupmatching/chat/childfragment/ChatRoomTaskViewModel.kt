package com.eric.startupmatching.chat.childfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.ChatRoom
import com.eric.startupmatching.data.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatRoomTaskViewModel: ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _chatRoomList = MutableLiveData<List<ChatRoom>>()
    val chatRoomList: LiveData<List<ChatRoom>>
        get() = _chatRoomList

    var user = UserInfo.currentUser

    fun getTaskChatRoom() {
        var list = mutableListOf<ChatRoom>()
        coroutineScope.launch {
            db.collection("ChatRoom")
                .whereEqualTo("type", "Task")
                .whereArrayContains("member", user.value!!.id.toString())
                .get()
                .addOnSuccessListener { data ->
                    list.addAll(data.toObjects(ChatRoom::class.java))
                    list.sortBy { it.updateTime }
                    _chatRoomList.value = list
                }
        }
    }
}
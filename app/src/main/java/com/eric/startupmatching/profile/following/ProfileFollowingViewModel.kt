package com.eric.startupmatching.profile.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.ChatRoom
import com.eric.startupmatching.data.FirebaseDataSource
import com.eric.startupmatching.data.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileFollowingViewModel: ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val user = UserInfo.currentUser.value


    // get following Ids -> get following Users

    // get Following Ids
    private val _followingIdList = MutableLiveData<List<String?>?>()
    val followingIdList: LiveData<List<String?>?>
        get() = _followingIdList

    private val _followingUsers = MutableLiveData<List<User?>>()
    val followingUsers: LiveData<List<User?>>
        get() = _followingUsers

    // Navigate to chat room (personal)
    private val _chatUser = MutableLiveData<User>()
    val chatUser: LiveData<User>
        get() = _chatUser

    fun setChatUser(user: User) {
        _chatUser.value = user
    }

    // Check if chat room exist
    private val _checkChatRoom = MutableLiveData<ChatRoom?>()
    val checkChatRoom: LiveData<ChatRoom?>
        get() = _checkChatRoom

    fun setCheckedChatRoom(chatRoom: ChatRoom?) {
        _checkChatRoom.value = chatRoom
    }

    fun checkIfUserRoomExist(user: User, otherUser: User) {
        coroutineScope.launch {
            val chatRoom = FirebaseDataSource.checkIfUserRoomExist(user, otherUser)
            setCheckedChatRoom(chatRoom)
        }
    }

    // get or fetch chat room after checking room existing
    private val _chatRoomId = MutableLiveData<String>()
    val chatRoomId: LiveData<String>
        get() = _chatRoomId

    fun setChatRoomId(chatRoomId: String) {
        _chatRoomId.value = chatRoomId
    }

    fun createChatRoom() {
        coroutineScope.launch {
            val chatRoomId = FirebaseDataSource.createUserChatRoom(UserInfo.currentUser.value!!, chatUser.value!!)
            setChatRoomId(chatRoomId)
        }
    }

    fun getFollowingList(user: User) {
        coroutineScope.launch {
            _followingIdList.value = FirebaseDataSource.getFollowingIdList(user)
        }
    }

    fun getFollowingUsers(ids: List<String>) {
        coroutineScope.launch {
            _followingUsers.value = FirebaseDataSource.getFollowingList(ids)
        }
    }
}
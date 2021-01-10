package com.eric.startupmatching.profile.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
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
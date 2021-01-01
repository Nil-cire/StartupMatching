package com.eric.startupmatching.profile

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

class ProfileViewModel(private val dataSource: FirebaseDataSource): ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val user = UserInfo.currentUser.value


    // get following Ids -> get following Users

    // get Following Ids
    private val _followingIdList = MutableLiveData<List<String?>>()
    val followingIdList: LiveData<List<String?>>
        get() = _followingIdList

    fun getFollowingIdList(user: User) {
        coroutineScope.launch {
            _followingIdList.value = dataSource.getFollowingIdList(user)
        }
    }

    // get Following Users
    private val _followingList = MutableLiveData<List<User?>>()
    val followingList: LiveData<List<User?>>
        get() = _followingList

    fun getFollowingUserList(list: List<String?>?) {
        coroutineScope.launch {
            _followingList.value = dataSource.getFollowingList(list)
        }
    }
}
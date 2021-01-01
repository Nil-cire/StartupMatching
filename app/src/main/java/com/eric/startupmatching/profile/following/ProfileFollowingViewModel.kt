package com.eric.startupmatching.profile.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProfileFollowingViewModel: ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val user = UserInfo.currentUser.value


    // get following Ids -> get following Users

    // get Following Ids
    private val _followingIdList = MutableLiveData<List<String?>>()
    val followingIdList: LiveData<List<String?>>
        get() = _followingIdList
}
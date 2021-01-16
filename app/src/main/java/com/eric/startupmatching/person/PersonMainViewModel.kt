package com.eric.startupmatching.person

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.FirebaseDataSource
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PersonMainViewModel: ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Project>()
    val team: LiveData<Project>
        get() = _team

    private val _userList = MutableLiveData<List<User?>?>()
    val userList: LiveData<List<User?>?>
        get() = _userList

    fun getAllUser() {
        coroutineScope.launch {
            try {
                _userList.value = FirebaseDataSource.getAllUser()
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }
}
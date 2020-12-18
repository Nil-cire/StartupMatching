package com.eric.startupmatching.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
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

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    fun getAllUser() {
        coroutineScope.launch {
            db.collection("User")
                .get().addOnSuccessListener {
                    var list = it.toObjects(User::class.java)
                    var list2 = list.toMutableList()
                    for ((index, user) in list.withIndex()) {
                        if (user.id == UserInfo.currentUser.value?.id)
                            list2.removeAt(index)
                    }
                    _userList.value = list2
                }
        }
    }

    init {
       getAllUser()
    }

}
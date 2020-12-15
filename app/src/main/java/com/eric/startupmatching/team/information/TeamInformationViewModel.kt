package com.eric.startupmatching.team.information

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Event
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamInformationViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<User>()
    val team: LiveData<User>
        get() = _team

    private val _eventList = MutableLiveData<List<Event>>()
    val eventList: LiveData<List<Event>>
        get() = _eventList

    var user = UserInfo.currentUser

    fun getTeamMember() {
        var list = mutableListOf<Event>()
        var count = 0
        coroutineScope.launch {
            db.collection("Event")
                .whereArrayContains("toUsers", user.value?.id.toString())
                .addSnapshotListener { value, error ->
                    for (event in value?.toObjects(Event::class.java) as MutableList<Event>) {
//                        if(event.fromUser != user.value?.id) {
                            Log.d("event", event.toString())
                            list.add(event)
                            count += 1
//                        }
                    }
                    if (count == value.toObjects(Event::class.java).size){
                        _eventList.value = list
                        Log.d("eventList", eventList.value.toString())
                    }
                }
        }
    }

}
package com.eric.startupmatching.team.teamstatus

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamStatusViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<User>()
    val team: LiveData<User>
        get() = _team

    private val _teamMemberList = MutableLiveData<List<User>>()
    val teamMemberList: LiveData<List<User>>
        get() = _teamMemberList

    var user = UserInfo.currentUser

    fun getTeamMember() {
        var teamMenberList = mutableListOf<User>()
        coroutineScope.launch {
            db.collection("Team")
                .whereArrayContains("members", user.value?.id.toString())
                .addSnapshotListener { value, error ->
                    for (teamMember in value?.toObjects(User::class.java) as MutableList<User>) {
                        if(teamMember.id != user.value?.id) {
                            teamMenberList.add(teamMember)
                        }
                    }
                    _teamMemberList.value = teamMenberList
                    Log.d("TeamMainMember", teamMemberList.toString())
                }
        }
    }

}
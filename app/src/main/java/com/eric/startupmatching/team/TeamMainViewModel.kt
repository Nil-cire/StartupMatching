package com.eric.startupmatching.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.data.TeamMember
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class TeamMainViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

//    private val _team = MutableLiveData<Team>()
//    val team: LiveData<Team>
//        get() = _team

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _teamList = MutableLiveData<List<TeamMember>>()
    val teamList: LiveData<List<TeamMember>>
        get() = _teamList

//    private val _currentUser = MutableLiveData<User>()
//    val currentUser: LiveData<User>
//        get() = _currentUser

    var user = UserInfo.currentUser

    fun getTeamMemberList(user: User) {
        coroutineScope.launch{
            try {
                db.collection("TeamMember")
                    .whereEqualTo("project", user.currentProject?.get(0))
                    .whereEqualTo("team", user.currentTeam?.get(0))
                    .get()
                    .addOnSuccessListener {
                        val list = it.toObjects(TeamMember::class.java)
                        _teamList.value = list
                        Log.d("teamList", user.currentProject.toString())
                        Log.d("teamList", teamList.value.toString())
                    }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }

//    fun observeMemberStatus() {
//        db.collection("TeamMember")
//            .whereEqualTo("project", user.currentProject?.get(0))
//            .whereEqualTo("team", user.currentTeam)
//            .addSnapshotListener { value, error ->
//                value?.let {
//                    getTeamMemberList(user)
//                }
//            }
//    }


    init {
        user = UserInfo.currentUser
//        getTeamMemberList(user)
    }

}
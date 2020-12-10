package com.eric.startupmatching.match.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MatchTeamViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Project>()
    val team: LiveData<Project>
        get() = _team

    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: LiveData<List<Team>>
        get() = _teamList

//    private val _getTeamList = MutableLiveData<Int>()
//    val getTeamList: LiveData<Int>
//        get() = _getTeamList

    var user = UserInfo.currentUser

//    fun getTeamList() {
//        var list = mutableListOf<Team>()
//        coroutineScope.launch{
//
//            try {
//                db.collection("Team")
//                    .whereNotIn("members", user.id)
//                    .get()
//                    .addOnSuccessListener {
//                        list.addAll(it.toObjects(Team::class.java))
//                        _teamList.value = list
//                        Log.d("ProjectListaa", teamList.value.toString())
//                    }
//            } catch (e: Exception) {
//                Log.d("error", e.message.toString())
//            }
//
////                _projectList.value = list.sortedBy { it.startTime }
//
//        }
//    }

//    fun observeProjectUpdate() {
//        db.collection("Project")
//            .whereEqualTo("startupStatus", "Running")
//            .addSnapshotListener { value, error ->
//                value?.let {
//                    getRunningProjectList(user)
//                }
//            }
//    }

//    fun liveUpdateMemeberStatus() {
//        db.collection("TeamMember").addSnapshotListener { value, error ->
//            value?.let {
//                it.forEach {
//                    Log.i("REALTIMETAG", "${it.data}")
//                }
//            }
//        }
//    }

    init {
        user = UserInfo.currentUser
//        getTeamList()
    }

}
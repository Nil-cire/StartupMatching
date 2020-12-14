package com.eric.startupmatching.project

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

class ProjectMainViewModel: ViewModel() {


    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _teamAsTeamleader = MutableLiveData<List<Team>>()
    val teamAsTeamleader: LiveData<List<Team>>
        get() = _teamAsTeamleader

    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: MutableLiveData<List<Team>>
        get() = _teamList

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>>
        get() = _projectList

    val user = UserInfo.currentUser

//run by default
    fun getUserTeam() {
        coroutineScope.launch {
            db.collection("Team")
                .whereArrayContains("members", user.value?.id.toString())
                .get()
                .addOnSuccessListener {
                    _teamList.value = it.toObjects(Team::class.java)
                    Log.d("teamList", teamList.toString())
                }
        }
    }

    fun getUserTeamAsTeamLeader() {
        var teamAsTeamleaderList = mutableListOf<Team>()
        coroutineScope.launch {
            db.collection("Team")
                .whereEqualTo("teamLeader", user.value?.id)
                .get()
                .addOnSuccessListener {
                    teamAsTeamleaderList.addAll(it.toObjects(Team::class.java))
                    _teamAsTeamleader.value = teamAsTeamleaderList
                    Log.d("teamAsTeamleader", teamAsTeamleader.toString())
                }
        }
    }


    fun getAllProject() {
        var cont = 0
        coroutineScope.launch {
            var list = mutableListOf<Project>()
            for (team in teamList.value!!) {
                db.collection("Project")
                    .whereArrayContains("teams", team.id.toString())
                    .get()
                    .addOnSuccessListener {
                        var a = it.toObjects(Project::class.java)
                        list.addAll(it.toObjects(Project::class.java))
                        cont += 1
                        if (cont == teamList.value!!.size) {
                            _projectList.value = list
                            Log.d("getAllProject", list.toString())
                        }
                    }
            }
        }
    }


//filter on Chip Selected
    fun getProjectAsOwner() {
        var projectAsOwnerList = mutableListOf<Project>()
        coroutineScope.launch {
            db.collection("Project")
                .whereEqualTo("projectLeader", user.value?.id)
                .get()
                .addOnSuccessListener {
                    projectAsOwnerList.addAll(it.toObjects(Project::class.java))
                    _projectList.value = projectAsOwnerList
                    Log.d("projectAsOwnerList", _projectList.toString())
                }
        }
}



    init {
        getUserTeam()
        getUserTeamAsTeamLeader()
    }

}
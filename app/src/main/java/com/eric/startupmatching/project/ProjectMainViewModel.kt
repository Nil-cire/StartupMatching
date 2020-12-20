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

    private val _projectListFilted = MutableLiveData<List<Project>>()
    val projectListFilted: LiveData<List<Project>>
        get() = _projectListFilted

    val user = UserInfo.currentUser

    //// project filter status

    private val _projectOwnerBtnChecked = MutableLiveData<Boolean>()
    val projectOwnerBtnChecked: LiveData<Boolean>
        get() = _projectOwnerBtnChecked

    fun projectOwnerBtnSelect() {
        _projectOwnerBtnChecked.value = true
    }

    fun projectOwnerBtnUnSelect() {
        _projectOwnerBtnChecked.value = false
    }

    private val _teamLeaderBtnChecked = MutableLiveData<Boolean>()
    val teamLeaderBtnChecked: LiveData<Boolean>
        get() = _teamLeaderBtnChecked

    fun teamLeaderBtnSelect() {
        _teamLeaderBtnChecked.value = true
    }

    fun teamLeaderBtnUnSelect() {
        _teamLeaderBtnChecked.value = false
    }

    private val _preparingBtnChecked = MutableLiveData<Boolean>()
    val preparingBtnChecked: LiveData<Boolean>
        get() = _preparingBtnChecked

    fun preparingBtnSelect() {
        _preparingBtnChecked.value = true
    }

    fun preparingBtnUnSelect() {
        _preparingBtnChecked.value = false
    }

    private val _processBtnChecked = MutableLiveData<Boolean>()
    val processBtnChecked: LiveData<Boolean>
        get() = _processBtnChecked

    fun processBtnSelect() {
        _processBtnChecked.value = true
    }

    fun processBtnUnSelect() {
        _processBtnChecked.value = false
    }

    private val _historyBtnChecked = MutableLiveData<Boolean>()
    val historyBtnChecked: LiveData<Boolean>
        get() = _historyBtnChecked

    fun historyBtnSelect() {
        _historyBtnChecked.value = true
    }

    fun historyBtnUnSelect() {
        _historyBtnChecked.value = false
    }

    fun projectFilter() : MutableList<Project> {
        var list1 = mutableListOf<Project>()
        var list2 = mutableListOf<Project>()
        if (projectOwnerBtnChecked.value == true && preparingBtnChecked.value == true) {
            projectList.value?.filterTo(list1, {it.projectLeader == UserInfo.currentUser.value?.id })
            if (!list1.isNullOrEmpty()) {
                list1.filterTo(list2, {it.startupStatus == "preparing"})
            }
        } else if (projectOwnerBtnChecked.value == true && processBtnChecked.value == true) {
            projectList.value?.filterTo(list1, {it.projectLeader == UserInfo.currentUser.value?.id})
            if (!list1.isNullOrEmpty()) {
                list1.filterTo(list2, {it.startupStatus == "running"})
            }
        } else if (projectOwnerBtnChecked.value == true && historyBtnChecked.value == true) {
            projectList.value?.filterTo(list1, {it.projectLeader == UserInfo.currentUser.value?.id})
            if (!list1.isNullOrEmpty()) {
                list1.filterTo(list2, {it.startupStatus == "done"})
            }
        } else if (teamLeaderBtnChecked.value == true && preparingBtnChecked.value == true) {
            projectList.value?.filterTo(list1, {it.teamLeaders!!.contains(UserInfo.currentUser.value?.id)})
            if (!list1.isNullOrEmpty()) {
                list1.filterTo(list2, {it.startupStatus == "preparing"})
            }
        } else if (teamLeaderBtnChecked.value == true && processBtnChecked.value == true) {
            projectList.value?.filterTo(list1, {it.teamLeaders!!.contains(UserInfo.currentUser.value?.id)})
            if (!list1.isNullOrEmpty()) {
                list1.filterTo(list2, {it.startupStatus == "running"})
            }
        } else if (teamLeaderBtnChecked.value == true && historyBtnChecked.value == true) {
            projectList.value?.filterTo(list1, {it.teamLeaders!!.contains(UserInfo.currentUser.value?.id)})
            if (!list1.isNullOrEmpty()) {
                list1.filterTo(list2, {it.startupStatus == "done"})
            }
        } else if (projectOwnerBtnChecked.value == true) {
            projectList.value?.filterTo(list2, {it.projectLeader == UserInfo.currentUser.value?.id})
        } else if (teamLeaderBtnChecked.value == true) {
            projectList.value?.filterTo(list2, {it.teamLeaders!!.contains(UserInfo.currentUser.value?.id)})
        } else if (preparingBtnChecked.value == true) {
            projectList.value?.filterTo(list2, {it.startupStatus == "preparing"})
        } else if (processBtnChecked.value == true) {
            projectList.value?.filterTo(list2, {it.startupStatus == "running"})
        } else if (historyBtnChecked.value == true) {
            projectList.value?.filterTo(list2, {it.startupStatus == "done"})
        } else {
            if (!projectList.value.isNullOrEmpty()) {
                list2 = projectList.value as MutableList<Project>
            }
        }

        return list2
    }

    init {
        getUserTeam()
        getUserTeamAsTeamLeader()
        _projectOwnerBtnChecked.value = false
        _teamLeaderBtnChecked.value = false
        _preparingBtnChecked.value = false
        _processBtnChecked.value = false
        _historyBtnChecked.value = false
    }

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
        var list = mutableListOf<Project>()
        var cont = 0
        coroutineScope.launch {
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







}
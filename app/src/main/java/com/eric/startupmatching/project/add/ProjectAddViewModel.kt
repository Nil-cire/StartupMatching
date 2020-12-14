package com.eric.startupmatching.project.add

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

class ProjectAddViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _addSuccess = MutableLiveData<Project>()
    val addSuccess: LiveData<Project>
        get() = _addSuccess

    private val _addTeamId = MutableLiveData<String>()
    val addTeamId: LiveData<String>
        get() = _addTeamId

//    private val _teamAsTeamleader = MutableLiveData<List<Team>>()
//    val teamAsTeamleader: LiveData<List<Team>>
//        get() = _teamAsTeamleader
//
//    private val _teamList = MutableLiveData<List<Team>>()
//    val teamList: MutableLiveData<List<Team>>
//        get() = _teamList
//
//    private val _projectList = MutableLiveData<List<Project>>()
//    val projectList: LiveData<List<Project>>
//        get() = _projectList

    val user = UserInfo.currentUser

    fun addTeam() {
        var team = Team(teamName = "Project Owner", members = mutableListOf(UserInfo.currentUser.value?.id))

        coroutineScope.launch {
            db.collection("Team")
                .add(team)
                .addOnSuccessListener {
                    it.update("id", it.id)
                    _addTeamId.value = it.id
                }
        }

    }

    fun addProject(project: Project) {
        coroutineScope.launch {
            db.collection("Project").add(project)
                .addOnSuccessListener {
                    it.update("id", it.id)
                    it.update("teams", mutableListOf(_addTeamId.value))
                    _addSuccess.value = project
                }
        }
    }
}
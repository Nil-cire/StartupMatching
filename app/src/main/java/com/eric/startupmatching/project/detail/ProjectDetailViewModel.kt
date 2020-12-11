package com.eric.startupmatching.project.detail

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

class ProjectDetailViewModel(arg: Project): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var arg = arg

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
}
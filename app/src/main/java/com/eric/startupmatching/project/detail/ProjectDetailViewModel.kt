package com.eric.startupmatching.project.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.ProjectStage
import com.eric.startupmatching.data.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProjectDetailViewModel(arg: Project): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var projectArgs = arg

    val db = FirebaseFirestore.getInstance()

    private val _projectUpdate = MutableLiveData<Boolean>()
    val projectUpdate: LiveData<Boolean>
        get() = _projectUpdate

    //// Project ready check dialog (double check)
    private val _confirmProjectDone = MutableLiveData<Boolean>()
    val confirmProjectDone: LiveData<Boolean>
        get() = _confirmProjectDone

    fun confirmProjectReady() {
        _confirmProjectDone.value = true
    }
    //// --

    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: MutableLiveData<List<Team>>
        get() = _teamList

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>>
        get() = _projectList

    val user = UserInfo.currentUser

    fun updateProjectStatus(project: Project) {
        coroutineScope.launch {
            db.collection("Project").document(project.id!!)
                .update("startupStatus", ProjectStage.Running.stage)
                .addOnSuccessListener { _projectUpdate.value = true }
        }
    }

}
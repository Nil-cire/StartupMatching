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

    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: MutableLiveData<List<Team>>
        get() = _teamList

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>>
        get() = _projectList

    val user = UserInfo.currentUser

    fun getUserTeam() {
        coroutineScope.launch {
            db.collection("Team")
                .whereArrayContains("members", user.value?.id.toString())
                .get()
                .addOnSuccessListener {
                    _teamList.value = it.toObjects(Team::class.java)
                }
        }
    }

    fun getAllProject() {
        coroutineScope.launch {
            var list = mutableListOf<Project>()
            for (team in teamList.value!!) {
                db.collection("Project")
                    .whereArrayContains("teams", team.id.toString())
                    .get()
                    .addOnSuccessListener {
                        for (project in it) {
                            list.add(project.toObject(Project::class.java))
                        }
                    }
            }
            _projectList.value = list
            Log.d("ProjectALL", projectList.value.toString())
        }
    }

}
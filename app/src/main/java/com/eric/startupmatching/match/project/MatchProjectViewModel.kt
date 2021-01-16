package com.eric.startupmatching.match.project

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class MatchProjectViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Project>()
    val team: LiveData<Project>
        get() = _team

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>>
        get() = _projectList

    var user = UserInfo.currentUser

    fun getProjectList() {
        var list = mutableListOf<Project>()
        coroutineScope.launch{
            try {
                db.collection("Project")
                    .get()
                    .addOnSuccessListener {
                        list.addAll(it.toObjects(Project::class.java))
                        _projectList.value = list.sortedBy { it.startTime }
                        Log.d("ProjectListaa", projectList.value.toString())
                    }
            } catch (e: Exception) {
                Log.d("error", e.message.toString())
            }
        }
    }

    init {
        user = UserInfo.currentUser
        getProjectList()
    }

}
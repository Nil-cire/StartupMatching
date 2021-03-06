package com.eric.startupmatching.project.done

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ProjectDoneMainViewModel(arg: Project): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var projectArgs = arg

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

    //// get all user in project
    private val _userIdList = MutableLiveData<List<String>>()
    val userIdList: LiveData<List<String>>
        get() = _userIdList

    fun getAllUserInProject(project: Project) {
        var teamIdList = mutableListOf<String>()
        var userIdList = mutableListOf<String>()
        var count = 0
        coroutineScope.launch {
            db.collection("Project").document(project.id!!)
                .get()
                .addOnSuccessListener {
                    teamIdList = it.toObject(Project::class.java)?.teams as MutableList<String>
                }.addOnSuccessListener {
                    for (teamId in teamIdList) {
                        db.collection("Team").document(teamId)
                            .get()
                            .addOnSuccessListener {
                                var team = it.toObject(Team::class.java)
                                if (team != null) {
                                    if (!team.members.isNullOrEmpty()) {
                                        val list = team.members as MutableList<String>
                                        userIdList.addAll(list)
                                    }
                                }
                                count += 1
                                if (count == teamIdList.size) {
                                    _userIdList.value = userIdList
                                }
                            }
                    }
                }
        }
    }

    //// add achievement to each user
    fun addAchievementIdToUsers(userIdList: List<String>, achievementId: String) {
        var userAcievementList = mutableListOf<String>()
        coroutineScope.launch {
            for (userId in userIdList) {
                db.collection("User").document(userId)
                    .get()
                    .addOnSuccessListener {
                        if (it.toObject(User::class.java)?.achievements.isNullOrEmpty()) {
                            userAcievementList = mutableListOf()
                        } else {
                            userAcievementList = it.toObject(User::class.java)?.achievements as MutableList<String>
                        }
                        userAcievementList.add(achievementId)
                        it.reference.update("achievements", userAcievementList)
                    }
            }
        }
    }


    //// create achievement of project, add users' id in it

    private val _achievementId = MutableLiveData<String>()
    val achievementId: LiveData<String>
        get() = _achievementId

    fun createAchievementForProject(project: Project, userIdList: List<String>) {
        val achievement = Achievement(name = project.projectName, members = userIdList, time = Calendar.getInstance().time, project = project.id)
        coroutineScope.launch {
            db.collection("Achievement")
                .add(achievement)
                .addOnSuccessListener {
                    it.update("id", it.id)
                    _achievementId.value = it.id
                }
        }
    }

    //// set project status to done

    fun setProjectStatusToDone(project: Project) {
        coroutineScope.launch {
            db.collection("Project").document(project.id!!)
                .update("startupStatus", ProjectStage.Done.stage)
        }
    }

    init {
        getAllUserInProject(projectArgs)
        setProjectStatusToDone(projectArgs)
    }

    //// creating a post with comment and photo -> then create achievement for each member with postId included
    private val _addPostComplete = MutableLiveData<Boolean>()
    val addPostComplete: LiveData<Boolean>
        get() = _addPostComplete

    fun postProject(content: String, imageUrl: String?) {
        val post = Post(content = content, image = imageUrl, poster = UserInfo.currentUser.value?.id!!, timeDate = Calendar.getInstance().time, achievementId = achievementId.value)
        coroutineScope.launch {
            db.collection("Post")
                .add(post)
                .addOnSuccessListener {
                    it.update("id", it.id)
                }.addOnSuccessListener {
                    _addPostComplete.value = true
                }
        }
    }
}
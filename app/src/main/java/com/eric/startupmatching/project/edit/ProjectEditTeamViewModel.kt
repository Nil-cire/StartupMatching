package com.eric.startupmatching.project.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.*
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import com.eric.startupmatching.project.treeview.model.team.TeamChildModel
import com.eric.startupmatching.project.treeview.model.team.TeamParentModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProjectEditTeamViewModel(project: Project): ViewModel() {

    val projectArgs = project

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _teamIdList = MutableLiveData<List<String>>()
    val teamIdList: LiveData<List<String>>
        get() = _teamIdList

    var teamListHolder = mutableListOf<Team>()
    var teamListHolderCount = 0


    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: LiveData<List<Team>>
        get() = _teamList

    private val _getUserCount = MutableLiveData<Int>()
    val getUserCount: LiveData<Int>
        get() = _getUserCount

    var listToSubmitHolder = mutableListOf<Any>()

    var teamInstance = Task()

    private val _listToSubmit = MutableLiveData<List<Any>>()
    val listToSubmit: LiveData<List<Any>>
        get() = _listToSubmit

    private val _teamListGet = MutableLiveData<Boolean>()
    val teamListGet: LiveData<Boolean>
        get() = _teamListGet

    //todo val
    private val _todoSize = MutableLiveData<Int>()
    val todoSize: LiveData<Int>
        get() = _todoSize

    private val _getTeamCount = MutableLiveData<Int>()
    val getTeamCount: LiveData<Int>
        get() = _getTeamCount

    var addTeamId = MutableLiveData<String>()

    val editTeam = MutableLiveData<Team>()

    private val _followList = MutableLiveData<List<User>>()
    val followList: LiveData<List<User>>
        get() = _followList

//    private val _selectedFollowList = MutableLiveData<List<User>>()
//    val selectedFollowList: LiveData<List<User>>
//        get() = _selectedFollowList

    var selectedFollowList = mutableListOf<String>()

    var selectedTeam: Team? = null

    fun resetSelectedFollowList() {
        selectedFollowList = mutableListOf()
    }

    fun addSelectedFollowList(team: Team) {
        var count = 0
        coroutineScope.launch {
            db.collection("Team").document(team.id.toString())
                .get()
                .addOnSuccessListener {
                    var members = it.toObject(Team::class.java)?.members as MutableList
                    if (!members.isNullOrEmpty()) {
                        for (member in selectedFollowList) {
                            if (!members.contains(member)) {
                                members.add(member)
                            }
                            count += 1
                        }
                    }
                    if (count == selectedFollowList.size) {
                        it.reference.update("members", members)
                    }
                }
        }
    }


    val todoTask = MutableLiveData<Task>()

    var todoInstance = User()

    val db = FirebaseFirestore.getInstance()

    fun getTeamsByProject(project: Project) {
        var list = mutableListOf<String>()
        coroutineScope.launch {
            db.collection("Project").document(project.id!!)
                .get()
                .addOnSuccessListener { qs ->
                    list.addAll(qs.toObject(Project::class.java)?.teams as Collection<String>)
//                    list.sortBy { it.serial }
                    Log.d("editTeamTeamList", list.toString())
                    _teamIdList.value = list
                    Log.d("getTeamIdByProject", teamIdList.value.toString())
                }
        }
    }

    fun getTeamsByIds(teamIds: String) {
        var list = mutableListOf<Team>()
        var count = 0
        coroutineScope.launch {
//            for (id in teamIds) {
                db.collection("Team")
//                    .whereEqualTo("id", id)
                    .document(teamIds)
                    .get()
                    .addOnSuccessListener {
                        Log.d("teamListSize", (it.toObject(Team::class.java).toString()))
                        var team = it.toObject(Team::class.java)
                        Log.d("aTeam", team.toString())
                        if (team != null) {
                            teamListHolder.add(team)
                            teamListHolderCount += 1
                        }
                        Log.d("teamListHolder", teamListHolder.toString())
//                        teamListHolderCount += 1
                        if (teamListHolderCount == teamIdList.value?.size ?: 0) {
                            _teamList.value = teamListHolder
                            Log.d("teamListXXXX", teamList.value.toString())
                            teamListHolder = mutableListOf()
                            teamListHolderCount = 0
                        }
                    }
//            }
        }
    }

    fun addTeam(team: Team) {
        coroutineScope.launch {
            db.collection("Team")
                .add(team)
                .addOnSuccessListener {
                    it.update("id", it.id)
                    addTeamId.value = it.id
                }.addOnSuccessListener {
                    db.collection("Project").document(projectArgs.id.toString())
                        .get()
                        .addOnSuccessListener {
                            val project = it.toObject(Project::class.java)
                            var list = mutableListOf<String>()
                            if (!project!!.teams.isNullOrEmpty()) {
                                for (teamId in project.teams!!) {
                                    if (teamId != null) {
                                        list.add(teamId)
                                    }
                                }
                            }
                            list.add(addTeamId.value.toString()!!)
                            Log.d("addTeamId", list.toString())
                            it.reference.update("teams", list)
                        }
                }
        }
    }

    fun observeTeamDataChanged() {
        db.collection("Project").document(projectArgs.id.toString())
            .addSnapshotListener { value, error ->
                getTeamsByProject(projectArgs)
            }
    }


    fun getUserByTeam(team: Team) {
        var list = mutableListOf<User>()
        var treeChildList = mutableListOf<TreeViewModel>()
        coroutineScope.launch {
            db.collection("User").whereArrayContains("currentTeam", team.id.toString())
                .get()
                .addOnSuccessListener {qs ->
                    for (user in qs.toObjects(User::class.java)) {
                        if (user.id == team.teamLeader) {
                            list.add(0, user)
                        } else list.add(user)
                    }
                    Log.d("editUserList", list.toString())
                    for (user in list) {
                        treeChildList.add(TeamChildModel(user))
                    }
                    val treeParent = TeamParentModel(team)
                    treeParent.children = treeChildList as ArrayList<TreeViewModel>
                    listToSubmitHolder.add(treeParent)
                    _getTeamCount.value = _getTeamCount.value?.plus(1)
                    if (getTeamCount.value == teamList.value?.size) {
                        var a = listToSubmitHolder as MutableList<TaskParentModel>
//                        a.sortBy { it.content.serial }
                        _listToSubmit.value = a
                        listToSubmitHolder = mutableListOf()
                        _getTeamCount.value = 0
//                        _taskListGet.value = true
                        Log.d("listToSubmit", listToSubmit.value.toString())
                    }
                }
        }
    }

    fun getFriendList() {
        var followList = mutableListOf<User>()
        var count = 0
        if (!UserInfo.currentUser.value?.following.isNullOrEmpty()) {
            for (userId in UserInfo.currentUser.value?.following!!) {
                db.collection("User").whereEqualTo("id", userId)
                    .get()
                    .addOnSuccessListener {
                        if (!it.toObjects(User::class.java).isNullOrEmpty()) {
                            var user = it.toObjects(User::class.java)[0]
                            followList.add(user)
                            count += 1
                        }
                        if (count == UserInfo.currentUser.value?.following!!.size){
                            _followList.value = followList
                        }
                    }
            }
        }
    }

    init {
        _getTeamCount.value = 0
    }
}
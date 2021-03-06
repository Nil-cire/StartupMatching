package com.eric.startupmatching.project.detail.childfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class ProjectDetailTeamViewModel(arg: Project): ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var arg = arg

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: MutableLiveData<List<Team>>
        get() = _teamList

    private val _teamList2 = MutableLiveData<List<Team>>()
    val teamList2: MutableLiveData<List<Team>>
        get() = _teamList2

    private val _projectTeamMemberList = MutableLiveData<List<List<User>>>()
    val projectTeamMemberList: LiveData<List<List<User>>>
        get() = _projectTeamMemberList


    // Firebase query functions

    fun getProjectTeams(project: Project) {
        var count3 = 0
        var teamList = mutableListOf<Team>()
        coroutineScope.launch {
            db.collection("Project").document(project.id!!)
                .addSnapshotListener { value, error ->
                    var teams = value?.toObject(Project::class.java)?.teams ?: mutableListOf()
                    if (!teams.isNullOrEmpty()) {
                        for (teamId in teams) {
                            db.collection("Team")
                                .whereEqualTo("id", teamId)
                                .get()
                                .addOnSuccessListener {qs ->
                                    teamList.addAll(qs.toObjects(Team::class.java))
                                    count3 += 1
                                    if (count3 == project.teams?.size) {
                                        teamList.sortBy { it.id }
                                        _teamList.value = teamList
                                        Log.d("ProjectTeams", _teamList.value.toString())
                                    }

                                }
                        }
                    }
                }
        }
    }


    fun getProjectTeamMember(teamList: List<Team>) {
        coroutineScope.launch {
            var lista = mutableListOf<Team>()
            var listb = mutableListOf<List<User>>()
            var listc = mutableListOf<User>()
            var cont1 = 0
            var cont2 = 0

            for (team in teamList) {
                Log.d("teammembers", team.members.toString())
                lista.add(team)

                for (userId in team.members!!) {
                    delay(200)
                    Log.d("userId", userId.toString())
                    db.collection("User")
                        .whereEqualTo("id", userId)
                        .get()
                        .addOnCompleteListener {
                            it.let { qs ->
                                var user = qs.result!!.toObjects(User::class.java)[0]
                                if (user == null) {
                                    cont2 += 1
                                } else {
                                    listc.add(qs.result!!.toObjects(User::class.java)[0])
                                    cont2 += 1
                                }
                                Log.d("cont2", cont2.toString())
                                Log.d("singleTeamUser", listc.toString())

                                if (cont2 == team.members.size) {
                                    listb.add(listc)
                                    listc = mutableListOf()
                                    Log.d("multiTeamUser1", listb.toString())
                                    cont1 += 1
                                    cont2 = 0
                                    Log.d("cont1", cont1.toString())

                                    if (cont1 == teamList.size) {
                                        _teamList2.value = lista
                                        _projectTeamMemberList.value = listb
                                        Log.d("multiTeamUser2", projectTeamMemberList.value.toString())
                                        Log.d("ProjectTeams2", _teamList2.value.toString())
                                    }
                                }
                            }
                        }
                }
            }
        }
    }


    fun observeTeamDataChanged() {
        db.collection("Project").document(arg.id.toString())
            .addSnapshotListener { value, error ->
                getProjectTeams(arg)
            }
    }
}
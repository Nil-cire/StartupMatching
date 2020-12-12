package com.eric.startupmatching.project.detail.childfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProjectDetailTeamViewModel(arg: Project): ViewModel() {

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

    private val _projectTeamMemberList = MutableLiveData<List<List<User>>>()
    val projectTeamMemberList: LiveData<List<List<User>>>
        get() = _projectTeamMemberList


    // Firebase query functions
    fun getProjectTeams(project: Project) {
        var cont3 = 0
        coroutineScope.launch {
            val teamList = mutableListOf<Team>()
            if (!project.teams.isNullOrEmpty()) {
                for (teamId in project.teams) {
                    db.collection("Team")
                        .whereEqualTo("id", teamId)
                        .get()
                        .addOnSuccessListener {
                            cont3 += 1
                            teamList.add(it.toObjects(Team::class.java)[0])
                            teamList.sortByDescending { it.id }
                            if (cont3 == project.teams.size) {
                                _teamList.value = teamList
                                Log.d("ProjectTeams", _teamList.value.toString())
                            }

                        }
                }
            }
        }
    }

    fun getUser(userId: String): User? {
        var user: User? = null
        db.collection("User")
            .whereEqualTo("id", userId)
            .get()
            .addOnSuccessListener {
                user = it.toObjects(User::class.java)[0]
            }

        return user
    }

    fun get(teamList: List<Team>) {
        var listb = mutableListOf<List<User>>()
        var listc: MutableList<User>
        var cont1 = 0
        var cont2 = 0

        for (team in teamList) {
            for (userId in team.members!!) {
                val list = mutableListOf<User>()
                var user = getUser(userId!!)
                if (user != null) {
                    list.add(user)
                }
            }
        }
    }

    fun getProjectTeamMember(teamList: List<Team>) {
        coroutineScope.launch {
            var lista = mutableListOf<Team>()
            var listb = mutableListOf<List<User>>()
            var listc: MutableList<User>
            var cont1 = 0
            var cont2 = 0

            for (team in teamList) {
                listc = mutableListOf()
                Log.d("teammembers", team.members.toString())

                for (userId in team.members!!) {
                    lista.add(team)
                    Log.d("userId", userId.toString())
                    db.collection("User")
                        .whereEqualTo("id", userId)
                        .get()
                        .addOnSuccessListener {
                            it?.let {qs ->
                                listc.add(qs.toObjects(User::class.java)[0])
                                cont2 += 1
                                Log.d("cont2", cont2.toString())
                                Log.d("singleTeamUser", listc.toString())

                                if (cont2 == team.members.size) {
                                    cont2 = 0
                                    listb.add(listc)
                                    listc = mutableListOf()
                                    Log.d("multiTeamUser1", listb.toString())
                                    cont1 += 1
                                    Log.d("cont1", cont1.toString())

                                    if (cont1 == teamList.size) {
                                        _projectTeamMemberList.value = listb
                                        _teamList.value = lista
                                        Log.d("multiTeamUser2", projectTeamMemberList.value.toString())
                                    }
                                }
                            }
                        }
                }
            }
        }
    }


//    fun getProjectTeamMember(teamList: List<Team>) {
//        coroutineScope.launch {
//            var listb = mutableListOf<List<User>>()
//            var listc: MutableList<User>
//            var cont1 = 0
//            var cont2 = 0
//            for (team in teamList) {
//                listc = mutableListOf()
//                cont2 = 0
//                Log.d("teammembers", team.members.toString())
//
//                for (userId in team.members!!) {
//                    Log.d("userId", userId.toString())
//                    db.collection("User")
//                        .whereEqualTo("id", userId)
//                        .get()
//                        .addOnSuccessListener {
//                            it?.let {qs ->
//                                cont2 += 1
//                                Log.d("cont2", cont2.toString())
//                                listc.add(qs.toObjects(User::class.java)[0])
//                                Log.d("singleTeamUser", listc.toString())
//                                if (cont2 == team.members.size) {
//                                    listb.add(listc)
//                                    Log.d("multiTeamUser1", listb.toString())
//                                    cont1 += 1
//                                    Log.d("cont1", cont1.toString())
//                            }
//                        }
//                    }.addOnSuccessListener {
//                            if (cont1 == teamList.size) {
//                                _projectTeamMemberList.value = listb
//                                Log.d("multiTeamUser2", projectTeamMemberList.value.toString())
//                            }
//                        }
//                }
//            }
//        }
//    }
}
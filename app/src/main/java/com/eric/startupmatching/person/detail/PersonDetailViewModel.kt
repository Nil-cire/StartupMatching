package com.eric.startupmatching.person.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.Achievement
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PersonDetailViewModel(user: User): ViewModel() {

    val userArgs = user
    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Project>()
    val team: LiveData<Project>
        get() = _team

    private val _achievementList = MutableLiveData<List<Achievement>>()
    val achievementList: LiveData<List<Achievement>>
        get() = _achievementList

    private val _achievementListSubmit = MutableLiveData<List<Achievement>>()
    val achievementListSubmit: LiveData<List<Achievement>>
        get() = _achievementListSubmit


    fun getAchievements(user: User) {
        var count = 0
        var list = mutableListOf<Achievement>()
        coroutineScope.launch {
            db.collection("User")
                .whereEqualTo("id", user.id)
                .get()
                .addOnSuccessListener {
                    val userAchievementsId = it.toObjects(User::class.java)[0].achievements
                    if (userAchievementsId != null) {
                        for (achievementId in userAchievementsId) {
                            db.collection("Achievement")
                                .document(achievementId!!)
                                .get()
                                .addOnSuccessListener {doc ->
                                    if (doc != null) {
                                        list.add(doc.toObject(Achievement::class.java)!!)
                                    }
                                    count += 1
                                    if(count == userAchievementsId.size) {
                                        _achievementListSubmit.value = list
                                    }
                                }
                        }
                    }
                }
        }
    }

    init {
        getAchievements(userArgs)
        _user.value = userArgs
    }


}
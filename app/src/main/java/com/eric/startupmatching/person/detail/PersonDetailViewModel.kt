package com.eric.startupmatching.person.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Achievement
import com.eric.startupmatching.data.FirebaseDataSource
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

    private val _followed = MutableLiveData<Boolean>()
    val followed: LiveData<Boolean>
        get() = _followed

    private val _achievementList = MutableLiveData<List<Achievement?>?>()
    val achievementList: LiveData<List<Achievement?>?>
        get() = _achievementList

    private val _achievementListSubmit = MutableLiveData<List<Achievement>>()
    val achievementListSubmit: LiveData<List<Achievement>>
        get() = _achievementListSubmit


    //// follow and unfollow new user
    fun followBtnTexChecker() {
        UserInfo.currentUser.value?.following?.let{
            _followed.value = UserInfo.currentUser.value?.following?.contains(user.value?.id) ?: false
        }
    }

    fun follow(userId: String) {
        db.collection("User").document(UserInfo.currentUser.value?.id!!)
            .get()
            .addOnSuccessListener {
                var followList = it.toObject(User::class.java)?.following as MutableList<String?>?
                followList?.add(userId)
                it.reference.update("following", followList)
                db.collection("User").document(userId)
                    .get()
                    .addOnSuccessListener { doc ->
                        var followerList = doc.toObject(User::class.java)?.follower as MutableList<String>
                        followerList.add(UserInfo.currentUser.value!!.id.toString())
                        doc.reference.update("follower", followerList)
                        var list = UserInfo.currentUser.value!!.following as MutableList  // up date following list in UserInfo
                        list.add(userId)
                        UserInfo.setFollowerList(followerList)
                    }
                    .addOnSuccessListener { _followed.value = true }
            }
    }

    fun unFollow(userId: String) {
        db.collection("User").document(UserInfo.currentUser.value?.id!!)
            .get()
            .addOnSuccessListener {doc ->
                var followList = doc.toObject(User::class.java)?.following as MutableList<String>?
                var followList2 = mutableListOf<String>()
                followList?.filterTo(followList2, {it != userId})
                doc.reference.update("following", followList2)
                db.collection("User").document(userId)
                    .get()
                    .addOnSuccessListener { doc ->
                        var followerList = doc.toObject(User::class.java)?.follower as MutableList<String>
                        var followerList2 = mutableListOf<String>()
                        followerList.filterTo(followerList2, { it != UserInfo.currentUser.value?.id!! })
                        doc.reference.update("follower", followerList2)
//                        var list = UserInfo.currentUser.value!!.following as MutableList  // up date following list in UserInfo
//                        list.filter { it != userId }
                        UserInfo.setFollowerList(followList2)
                    }
                    .addOnSuccessListener { _followed.value = false }
            }
    }

    fun getAchievements(user: User) {
//        coroutineScope.launch {
//            _achievementList.value = FirebaseDataSource.getAchievements(user)
//        }
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
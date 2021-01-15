package com.eric.startupmatching.data

import android.util.Log
import com.eric.startupmatching.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FirebaseDataSource: DataSourceFunction {

    val db = FirebaseFirestore.getInstance()

// [Personal Fragment - Following List] Functions for getting following list

    override suspend fun getFollowingIdList(user: User): List<String?>? = suspendCoroutine {continuation ->
        var result = mutableListOf<String>()
            db.collection("User").document(user.id.toString())
                .get()
                .addOnCompleteListener {
                    result = it.result?.toObject(User::class.java)?.following as MutableList<String>?
                        ?: mutableListOf()
                    continuation.resume(result)
                }
    }

    override suspend fun getFollowingList(userIds: List<String?>?): List<User?>? = suspendCoroutine {continuation ->
        var result = mutableListOf<User>()
            var count = 0
            userIds?.let {
                for (id in userIds) {
                    db.collection("User")
                        .whereEqualTo("id", id)
                        .addSnapshotListener { value, error ->
                            value?.toObjects(User::class.java)?.get(0)?.let { result.add(it) }
                            count += 1
                            if (count == userIds.size) {
                                continuation.resume(result)
                            }
                        }
                }
            }
    }


// [All user Index Fragment - Following List] Functions for getting following list

    override suspend fun getAllUser(): List<User?>? = suspendCoroutine {continuation ->
        db.collection("User")
            .get()
            .addOnCompleteListener {
                val result = it.result?.toObjects(User::class.java)
                val result2 = it.result?.toObjects(User::class.java)
                if (!result2.isNullOrEmpty()) {
                    for ((index, user) in result2.withIndex()) {
                        if (user.id == UserInfo.currentUser.value?.id) {
                            result!!.removeAt(index)
                        }

                    }
                }
                continuation.resume(result)
            }
            .addOnFailureListener {
                Log.d("error", it.message.toString())
            }
    }

    override suspend fun getAchievements(user: User): List<Achievement?>? = suspendCoroutine {continuation ->
        var result = mutableListOf<Achievement>()
        var count = 0
        db.collection("User")
            .whereEqualTo("id", user.id)
            .get()
            .addOnCompleteListener {
                val userAchievementsId = it.result?.toObjects(User::class.java)?.get(0)?.achievements
                if (!userAchievementsId.isNullOrEmpty()) {
                    for (achievementId in userAchievementsId) {
                        db.collection("Achievement")
                            .document(achievementId!!)
                            .get()
                            .addOnSuccessListener {doc ->
                                if (doc.toObject(Achievement::class.java) != null) {
                                    result.add(doc.toObject(Achievement::class.java)!!)
                                }
                                count += 1
                                if(count == userAchievementsId.size) {
                                    continuation.resume(result)
                                }
                            }
                    }
                }
                continuation.resume(result)
            }
            .addOnFailureListener {
                Log.d("error", it.message.toString())
            }
    }

    override suspend fun setUserName(user:User) {

    }

    override suspend fun setUserImage(user:User) {

    }

    override suspend fun setUserDescription(user:User) {

    }

    override suspend fun setUserEmail(user:User) {

    }

    override suspend fun checkIfUserRoomExist(user: User, otherUser: User): ChatRoom? = suspendCoroutine { continuation->
        val list = listOf<String>(user.id!!, otherUser.id!!)
        db.collection("ChatRoom")
            .whereEqualTo("type", ChatRoomType.User.type)
            .whereArrayContains("member", list)
            .get()
            .addOnCompleteListener {
                val result = if (!it.result?.toObjects(ChatRoom::class.java).isNullOrEmpty()) {
                    it.result?.toObjects(ChatRoom::class.java)?.get(0)
                } else {
                    null
                }
                continuation.resume(result)
            }
    }

// [All user Index Fragment - Following List] Functions for getting or creating User Chat Room

    override suspend fun createUserChatRoom(user: User, otherUser: User): String = suspendCoroutine { continuation->
        val chatRoom = ChatRoom(messages = mutableListOf(user.id!!, otherUser.id!!))
        db.collection("ChatRoom")
            .add(chatRoom)
            .addOnCompleteListener {
                it.result?.update("id", it.result?.id)
                continuation.resume(it.result!!.id)
            }
    }



}
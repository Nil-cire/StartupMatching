package com.eric.startupmatching.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FirebaseDataSource: DataSourceFunction {

    val db = FirebaseFirestore.getInstance()

//    override suspend fun getFollowingIdList(user: User): List<String?>? = suspendCoroutine {continuation ->
//        var result = mutableListOf<String>()
//        return try {
//            db.collection("User").document(user.id.toString())
//                .get()
//                .addOnCompleteListener {
//                    result = it.result?.toObject(User::class.java)?.following as MutableList<String>?
//                        ?: mutableListOf()
//                }
//            result
//        } catch (e: Exception) {
//            Log.d("getFollowingList", "Fail")
//            mutableListOf()
//        }
//    }

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

//    override suspend fun getFollowingList(userIds: List<String?>?): List<User?>? {
//        var result = mutableListOf<User>()
//        try {
//            var count = 0
//            userIds?.let {
//                for (id in userIds) {
//                    db.collection("User")
//                        .whereEqualTo("id", id)
//                        .addSnapshotListener { value, error ->
//                            value?.toObjects(User::class.java)?.get(0)?.let { result.add(it) }
//                            count += 1
//                            if (count == userIds.size) {
//                                return@addSnapshotListener
//                            }
//                        }
//                }
//            }
//            return result
//        } catch (e: Exception) {
//            Log.d("getFollowingList", "Fail")
//            return mutableListOf()
//        }
//    }
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
}
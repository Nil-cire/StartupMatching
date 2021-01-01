package com.eric.startupmatching.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseDataSource: DataSourceFunction {

    val db = FirebaseFirestore.getInstance()

    override suspend fun getFollowingIdList(user: User): List<String?>? {
        var result = mutableListOf<String>()
        try {
            db.collection("User").document(user.id.toString())
                .get()
                .addOnCompleteListener {
                    result = it.result?.toObject(User::class.java)?.following as MutableList<String>?
                        ?: mutableListOf()
                }
        } catch (e: Exception) {
            Log.d("getFollowingList", "Fail")
            return mutableListOf()
        }
        return result
    }

    override suspend fun getFollowingList(userIds: List<String?>?): List<User?>? {
        var result = mutableListOf<User>()
        try {
            var count = 0
            userIds?.let {
                for (id in userIds) {
                    db.collection("User")
                        .whereEqualTo("id", id)
                        .addSnapshotListener { value, error ->
                            value?.toObjects(User::class.java)?.get(0)?.let { result.add(it) }
                            count += 1
                            if (count == userIds.size) {
                                result
                            }
                        }
                }
            }
        } catch (e: Exception) {
            Log.d("getFollowingList", "Fail")
            return mutableListOf()
        }
        return result
    }

}
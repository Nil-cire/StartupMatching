package com.eric.startupmatching

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivityViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    val fragmentType = MutableLiveData<String>()

    val db = FirebaseFirestore.getInstance()

    fun getUser(uid: String) {
        coroutineScope.launch {
            try {
                db.collection("User").document(uid)
                    .get()
                    .addOnSuccessListener {
                        val result = it.toObject(User::class.java)
                        UserInfo.currentUser.value = result
                    }
            } catch (e: Exception) {
            }
        }
    }

}
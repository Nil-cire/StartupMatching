package com.eric.startupmatching

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status
    private var _getUserData = MutableLiveData<User>()
    val getUserData: LiveData<User>
        get() = _getUserData
    var firebaseUser = MutableLiveData<FirebaseUser>()

    val db = FirebaseFirestore.getInstance()

    init {

    }

    fun createUser() {
        coroutineScope.launch {
            firebaseUser.value?.let {
                val user = when (getUserData.value?.id == it.uid) {
                    true -> getUserData.value
                    false -> user(it)
                }
                user?.let { data->
                    db.collection("User").document(data.id!!)
//                        .update("id", data.id, "name", data.name, "image", data.image, "briefIntro", data.briefIntro)
                        .set(user)
                        .addOnCompleteListener {
                            UserInfo.currentUser.value = user
                            _status.value = true
                            Log.d("userLoginStatus", status.value.toString())
                        }
                        .addOnFailureListener { exception ->
                            Log.d("userCreateFailed", exception.message.toString())
                        }
                }
            }
        }
    }
    private fun user(it: FirebaseUser): User {
        return User(
            id = it.uid,
            name = it.displayName.toString(),
            image = it.photoUrl.toString(),
            email = it.email.toString()
        )
    }
    fun getUser(uid: String) {
        coroutineScope.launch {
            try {
                db.collection("User").document(uid)
                    .get()
                    .addOnSuccessListener {
                        val result = it.toObject(User::class.java)
                        UserInfo.currentUser.value = result
                        _getUserData.value = result
                    }
            } catch (e: Exception) {
            }
        }
    }
}
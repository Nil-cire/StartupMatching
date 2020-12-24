package com.eric.startupmatching

import com.google.firebase.auth.FirebaseUser

class LoginViewModel(private val gameRepository: GameRepository) : ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status
    private var _getUserData = MutableLiveData<User>()
    val getUserData: LiveData<User>
        get() = _getUserData
    var firebaseUser = MutableLiveData<FirebaseUser>()
    init {
    }
    fun createUser() {
        coroutineScope.launch {
            firebaseUser.value?.let {
                val user = when (getUserData.value?.id == it.uid) {
                    true -> getUserData.value
                    else -> user(it)
                }
                user?.let {data->
                    val result = gameRepository.createUser(data)
                    _status.value = when (result) {
                        is Result.Success -> {
                            result.data
                        }
                        else -> {
                            null
                        }
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
            favorite = mutableListOf(),
            browseRecently = mutableListOf()
        )
    }
    fun getUser(uid: String) {
        coroutineScope.launch {
            try {
                val result = gameRepository.getUser(uid)
                _getUserData.value = when (result) {
                    is Result.Success -> {
                        result.data
                    }
                    else -> {
                        null
                    }
                }
            } catch (e: Exception) {
            }
        }
    }
}
package com.eric.startupmatching

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eric.startupmatching.data.User
import com.eric.startupmatching.login.UserManager
import com.eric.startupmatching.login.UserManager._user
import com.eric.startupmatching.login.UserManager.user
import com.eric.startupmatching.login.UserManager.userToken
import java.util.*

object UserInfo {

    private const val USER_DATA = "user_data"
    private const val USER_TOKEN = "user_token"

    val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

//    var userA = User(
//        id = "004",
//        name = "D",
//        image = null,
//        skills = mutableListOf("002"),
//        currentTeam = mutableListOf("001"),
//        currentProject = mutableListOf("001"),
//        applyProject = null,
//        achievements = null,
//        following = mutableListOf("000", "001", "002", "005"),
//        follower = mutableListOf("000", "001", "002", "003", "005"),
//        blacklist = null,
//        briefIntro = "Will be on vacation on 12/10 ~ 1/2",
//        time = Calendar.getInstance().time
//    )

    var currentUser = MutableLiveData<User>()
    fun setFollowerList(followerList: MutableList<String>) {
        currentUser.value?.following = followerList
    }

    var userToken: String? = null
        get() = MyApplication.appContext
            ?.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            ?.getString(USER_TOKEN, null)
        set(value) {
            field = when (value) {
                null -> {
                    MyApplication.appContext
                        ?.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)?.edit()
                        ?.remove(USER_TOKEN)
                        ?.apply()
                    null
                }
                else -> {
                    MyApplication.appContext
                        ?.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)?.edit()
                        ?.putString(USER_TOKEN, value)
                        ?.apply()
                    value
                }
            }
        }

    /**
     * It can be use to check login status directly
     */
    val isLoggedIn: Boolean
        get() = UserManager.userToken != null
    /**
     * Clear the [userToken] and the [user]/[_user] data
     */
    fun clear() {
        UserManager.userToken = null
        _user.value = null
    }
}
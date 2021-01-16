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
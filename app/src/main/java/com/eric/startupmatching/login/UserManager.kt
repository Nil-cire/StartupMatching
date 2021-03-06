package com.eric.startupmatching.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eric.startupmatching.MyApplication
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.User

object UserManager {
    private const val USER_DATA = "user_data"
    private const val USER_TOKEN = "user_token"

    val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    val userUid = MutableLiveData<String>()


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
        get() = userToken != null
    /**
     * Clear the [userToken] and the [user]/[_user] data
     */
    fun clear() {
        userToken = null
        _user.value = null
    }
}
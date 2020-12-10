package com.eric.startupmatching

import androidx.lifecycle.MutableLiveData
import com.eric.startupmatching.data.User
import java.util.*

object UserInfo {

    private const val USER_DATA = "user_data"
    private const val USER_TOKEN = "user_token"

    private val userA = User(
        id = "004",
        name = "D",
        image = null,
        skills = mutableListOf("002"),
        currentTeam = mutableListOf("001"),
        currentProject = mutableListOf("001"),
        applyProject = null,
        achievements = null,
        following = null,
        follower = null,
        blacklist = null,
        briefIntro = "Will be on vacation on 12/10 ~ 1/2",
        time = Calendar.getInstance().time
    )

    var currentUser = MutableLiveData<User>(userA)
}
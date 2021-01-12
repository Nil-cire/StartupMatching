package com.eric.startupmatching.data

interface DataSourceFunction {

    suspend fun getFollowingIdList(user: User): List<String?>?

    suspend fun getFollowingList(userIds: List<String?>?): List<User?>?

    suspend fun getAllUser(): List<User?>?

}
package com.eric.startupmatching.data

interface DataSourceFunction {

    suspend fun getFollowingIdList(user: User): List<String?>?

    suspend fun getFollowingList(userIds: List<String?>?): List<User?>?

    suspend fun getAllUser(): List<User?>?

    suspend fun getAchievements(user: User): List<Achievement?>?

    suspend fun setUserName(user:User)

    suspend fun setUserImage(user:User)

    suspend fun setUserDescription(user:User)

    suspend fun setUserEmail(user:User)

    suspend fun checkIfUserRoomExist(user: User, otherUser: User): ChatRoom?

    suspend fun createUserChatRoom(user: User, otherUser: User): String

    suspend fun getPrivateChatRoom(user: User): List<ChatRoom?>

}
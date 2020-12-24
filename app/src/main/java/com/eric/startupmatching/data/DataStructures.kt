package com.eric.startupmatching.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    val id: String? = null,
    val name: String? = null,
    val image: String? = null,
    val skills: List<String?>? = null, //skill id
    val currentTeam: List<String?>? = null, // team id
    val currentProject: List<String?>? = null, // project id
    val applyProject: List<String?>? = null, //ProjectApply id
    val achievements: List<String?>? = null, // Achievement id
    val following: List<String?>? = null, // user id
    val follower: List<String?>? = null,
    val blacklist: List<String?>? = null, // user id
    val briefIntro: String? = null,
    val time: Date? = null,
    val workingStatus: String? = null // On, Off
): Parcelable

@Parcelize
data class ProjectApply(
    val project: String, // project id
    val applier: String, // user id
    val acceptor: String // user id
): Parcelable

@Parcelize
data class TeamApply(
    val team: String, // Team id
    val applier: String, // user id
    val acceptor: String // user id
): Parcelable

@Parcelize
data class Team(
    val id: String? = "",
    val teamName: String = "",
    val image: String? = "",
    val members: List<String?>? = mutableListOf(), //TeamMember id
    val hiringPosition: List<String?>? = mutableListOf(), //Position id
    val teamLeader: String = "", // User id
    val description: String? = null
): Parcelable

@Parcelize
data class Project(
    var id: String? = "",
    val projectName: String? = "",
    val description: String? = "",
    val tasks: List<String?>? = mutableListOf(), //Task id
    val teams: List<String?>? = mutableListOf(), // Team id
    val startupStatus: String? = "", // preparing, running, done
    val positions:List<String?>? = mutableListOf(), // position
    val hiringPosition: List<String?>? = mutableListOf(), // position
    val industry: String? = "", // industry id
    val projectLeader: String? = "", // User id
    val teamLeaders : List<String?>? = mutableListOf(), // User.id s
    val applicationList: List<String?>? = mutableListOf(), //User id
    val bulletinBoard: String? = "",
    val startTime: Date? = Calendar.getInstance().time,
    val endTime: Date? = Calendar.getInstance().time,
    val taskEditable: String? = null,
    val teamEditable: String? = null,
    val members: List<String?>? = mutableListOf()
): Parcelable

@Parcelize
data class Post(
    val id: String = "", // Post id
    val poster: String = "", // User id
    val content: String = "",
    val image: String? = null,
    val timeDate: Date = Calendar.getInstance().time,
    val comments: List<String?>? = mutableListOf(), // Comment ID
//    val achievement: List<String?>?, //Achievement id
    val likes: List<String?>? = mutableListOf(), // user id
    val achievementId: String? = ""
): Parcelable

@Parcelize
data class Achievement(
    val id: String? = null,
    val postId: String? = null,
    val project: String? = null,
    val team: String? = null,
    val members: List<String?>? = mutableListOf(),
    val position: String? = null,
    val time: Date? = Calendar.getInstance().time,
    val name: String? = ""
): Parcelable

@Parcelize
data class TeamMember(
    val position: String = "",
    val project: String = "", // project.id
    val name: String = "",
    val userId : String = "", //User id
    val team: String = "",
    val dutyStatus: String? = "", // On, Off, Back Soon, On Vacation ...
    val timeDate: Date? = null,
    val briefIntro: String? = null
): Parcelable

@Parcelize
data class Position(
    val id: String,
    val name: String,
    val description: String? = null
): Parcelable

@Parcelize
data class Industry(
    val id: String,
    val name: String,
    val positions: List<Position?>? = null
): Parcelable

@Parcelize
data class Skill(
    val id: String,
    val name: String,
    val description: String? = null
): Parcelable

@Parcelize
data class Task(
    val id: String? = null,
    var serial: Int? = null,
    val members: List<String?>? = mutableListOf(), //TeamMember id
    var name: String? = null,
    val chatRoom: String? = null, //chatRoom id
    val todo: List<String?>? = mutableListOf(), // Todo id
    val status: String? = null, //proposal, await, doing, done
    var startTime: Date? = null,
    var endTime: Date? = null,
    val preTask: String? = null, //Task id
    val deadline: Date? = null,
    var description: String? = null
): Parcelable

@Parcelize
data class Todo( //adjust while crafting project manage page
    val id: String? = null,
    var serial: Int? = null,
    val members: List<String?>? = mutableListOf(),
    var name: String? = null,
    var status: String? = null, //running, done
    var startTime: Date? = null,
    var endTime: Date? = null,
    val preTodo: String? = null,
    var task: String? = null,
    var description: String? = null
): Parcelable

@Parcelize
data class Message(
    val id: String = "",
    val content: String = "",
    val poster: String = "", //User.name
    val postTimestamp: Date = Calendar.getInstance().time,
    val checked: List<String?>? = mutableListOf()//User id
): Parcelable

@Parcelize
data class Comment(
    val id: String,
    val post: String, //Post id
    val content: String,
    val poster: String,
    val postTimestamp: Date = Calendar.getInstance().time,
    val checked: List<String?>? //User id
): Parcelable

@Parcelize
data class ChatRoom(
    val name: String? = null, // Task.id or Team.id or null for personal chat room
    val type: String? = null, // Private, Task, Team
    val id: String? = null ,
    val member: List<String?>? = null, // User id
    val updateTime: Date = Calendar.getInstance().time,
    val messages: MutableList<String?>? = null // Message id
): Parcelable

@Parcelize
data class Event(
    val fromUser: String? = null, // User.name
    val type: String? = null, // team, task, atodo, project
    val project: String? = null,
    val task: String? = null,
    val todo: String? = null,
    val team: String? = null,
    val toUsers: List<String?>? = null, // all user involved
    val id: String? = null ,
    val time: Date? = Calendar.getInstance().time, // done time
    val messages: String? = null // Message id
): Parcelable
package com.eric.startupmatching

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eric.startupmatching.data.*
import com.eric.startupmatching.databinding.FragmentBackstageBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class BackStageFragment: Fragment() {

    var positionA = Position("00001","Singer")
    var positionB = Position("00002","Guitarist")
    var positionC = Position("00003","Drummer")
    var positionD = Position("00004","Keyboard")
    var industryA = Industry("00001", "Music", mutableListOf(positionA, positionB, positionC, positionD))



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentBackstageBinding.inflate(inflater, container, false)

        val db = FirebaseFirestore.getInstance()

        //Buttons
        val addUserBtn = binding.addUser
        val addTeamBtn = binding.addTeam
        val addProjectBtn = binding.addProject
        val addSkills = binding.addSkill
        val addPosts = binding.addPost
        val addTeamMember = binding.addTeamMember
        val addMessage= binding.addMessage
        val addChatRoom = binding.addChatRoom
        val addTask = binding.addTask
        val addTodo = binding.addTodo
        val addEvent = binding.addEvent

        //instances

        val taskA = hashMapOf<String, Any?>(
            "id" to "001",
            "members" to mutableListOf("00001", "00002"),
            "name" to "Write a song",
            "messageList" to mutableListOf<String>(),
            "todo" to mutableListOf<String>(),
            "status" to "doing",
            "startTIme" to null,
            "endTime" to null,
            "preTask" to mutableListOf<String>(),
            "deadline" to null
        )

        val taskB = hashMapOf<String, Any?>(
            "id" to "002",
            "members" to mutableListOf("00001"),
            "name" to "Record it",
            "messageList" to mutableListOf<String>(),
            "todo" to mutableListOf<String>(),
            "status" to "await",
            "startTIme" to null,
            "endTime" to null,
            "preTask" to mutableListOf("0001"),
            "deadline" to null
        )




        val teamA = hashMapOf<String, Any?>(
            "id" to "00001",
            "teamName" to "Heartbreak Rocket",
            "members" to "00001, 00002",
            "achievements" to "00001, 00002",
            "hiringPosition" to mutableListOf("keyboard", "singer", "guitar"),
            "projectStatus" to "preparing",
            "follower" to "00003, 00004",
            "teamLeader" to "00001"
        )

        val projectA = hashMapOf<String, Any?>(
            "id" to "00001",
            "projectName" to "Heartbreak Rocket till 2020",
            "description" to "cool Band in Taipei",
            "tasks" to mutableListOf("00001", "00002"),
            "teams" to mutableListOf("00001"),
            "startupStatus" to "recruiting",
            "hiringPosition" to mutableListOf("00003", "00004"),
            "industry" to "00001",
            "taskProposal" to null,
            "projectLeader" to "00001",
            "applicationList" to mutableListOf("00003"),
            "bulletinBoard" to "",
            "startTime" to null,
            "endTime" to null
        )


        val userA = hashMapOf<String, Any?>(
            "id" to "00001",
            "name" to "eric",
            "image" to null,
            "gender" to "Male",
            "skills" to null,
            "currentTeam" to "00001",
            "currentProject" to mutableListOf("00001"),
            "applyProject" to mutableListOf("00001"),
            "achievements" to mutableListOf<String>(),
            "following" to mutableListOf("00003"),
            "follower" to mutableListOf("00002"),
            "blacklist" to mutableListOf("00004"),
            "briefIntro" to "I'm so fking lost!"
        )

        val allPosition = mutableListOf<Position>(
            Position("00001", "Singer"),
            Position("00002", "Guitarist"),
            Position("00003", "Drummer"),
            Position("00004", "Keyboard player"),
            Position("00005", "Bassist")
        )



        //Functions

        val userB = User(
            id = "000",
            name = "H",
            image = null,
            skills = null,
            currentTeam = mutableListOf("000"),
            currentProject = mutableListOf("001"),
            applyProject = null,
            achievements = null,
            following = null,
            follower = null,
            blacklist = null,
            briefIntro = " -- ",
            time = Calendar.getInstance().time

        )

        addUserBtn.setOnClickListener {
            try {
                db.collection("User")
                    .document(userB.id!!)
                    .set(userB)
                Log.d("add User", "Success")
            } catch (e:Exception) {
                Log.d("add User fail:", e.message ?: null.toString())
            }
        }

        //PJ
        val projectB = Project(
            id = "001",
            projectName = "Cost reduction of new product",
            description = "Current cost: 15 USD/Kg \nTarget cost: 12 USD/Kg",
            tasks = mutableListOf("00001", "00002"),
            teams = mutableListOf("000", "001", "002", "003"),
            startupStatus = "running",
            positions = mutableListOf("001", "002", "003", "004"),
            hiringPosition = mutableListOf("004"),
            industry = null,
            projectLeader = "000",
            teamLeaders = mutableListOf("001", "004", "006"),
            applicationList = mutableListOf(),
            bulletinBoard = "Get it done before 2020",
            startTime = null,
            endTime = null
        )

        addProjectBtn.setOnClickListener {
            try {
                db.collection("Project")
                    .document(projectB.id!!)
                    .set(projectB)
                Log.d("add Project:", "Success")
            } catch (e:Exception) {
                Log.d("add Profile fail:", e.message ?: null.toString())
            }
        }

        addSkills.setOnClickListener {
            try {
                for (position in allPosition) {
                    db.collection("Position")
                        .document(position.id)
                        .set(position)
                }
                Log.d("add position:", "Success")
            } catch (e:Exception) {
                Log.d("add position fail:", e.message ?: null.toString())
            }

        }

        val teams = mutableListOf<Team>(
            Team(id = "001", teamName = "Equipment", image = null, members = mutableListOf("001", "002", "003"), hiringPosition = mutableListOf(), teamLeader = "001"),
            Team(id = "002", teamName = "RD", image = null, members = mutableListOf("004", "005"), hiringPosition = mutableListOf(), teamLeader = "004"),
            Team(id = "003", teamName = "Process", image = null, members = mutableListOf("006"), hiringPosition = mutableListOf("003"), teamLeader = "006"),
            Team(id = "000", teamName = "Manager", image = null, members = mutableListOf("001", "002", "003"), hiringPosition = mutableListOf(), teamLeader = "001")
        )


        addTeamBtn.setOnClickListener {
            try {
                for (team in teams) {
                    db.collection("Team")
                        .add(team)
                }
                Log.d("add position:", "Success")
            } catch (e:Exception) {
                Log.d("add position fail:", e.message ?: null.toString())
            }
        }

        val teamMembers = mutableListOf(
            TeamMember("singer", "00001",
                "eric", "00001", "00001",
                "ON", Calendar.getInstance().time),
            TeamMember("manager", "00001",
                "A", "00005", "00001",
                "ON", Calendar.getInstance().time),
            TeamMember("friend", "00001",
                "B", "00006", "00001",
                "ON", Calendar.getInstance().time),
            TeamMember("fans", "00001",
                "C", "00007", "00001",
                "ON", Calendar.getInstance().time),
            TeamMember("dealer", "00001",
                "D", "00008", "00001",
                "ON", Calendar.getInstance().time)
        )

        addTeamMember.setOnClickListener {
            try {
                for (teamMember in teamMembers) {
                    db.collection("TeamMember")
                        .add(teamMember)
                }
                Log.d("add teamMember:", "Success")
            } catch (e:Exception) {
                Log.d("add teamMember fail:", e.message ?: null.toString())
            }
        }

        val messages = mutableListOf(
            Message("002", "Hi all", "004", Calendar.getInstance().time, mutableListOf("004", "005")),
            Message("004", "Hi", "005", Calendar.getInstance().time, mutableListOf("004", "005"))
        )

        addMessage.setOnClickListener {
            try {
                for (message in messages) {
                    db.collection("Message")
                        .add(message)
                }
                Log.d("add message:", "Success")
            } catch (e:Exception) {
                Log.d("add message fail:", e.message ?: null.toString())
            }
        }

        val chatRooms = mutableListOf(
            ChatRoom(null, "User","001", mutableListOf("000", "004"), Calendar.getInstance().time, mutableListOf("001", "003")),
            ChatRoom(null, "Team","002", mutableListOf("004", "005"), Calendar.getInstance().time, mutableListOf("002", "004"))

        )

        addChatRoom.setOnClickListener {
            try {
                for (chatRoom in chatRooms) {
                    db.collection("ChatRoom")
                        .add(chatRoom)
                }
                Log.d("add chatRoom:", "Success")
            } catch (e:Exception) {
                Log.d("add chatRoom fail:", e.message ?: null.toString())
            }
        }

        val tasks = mutableListOf<Task>(
            Task(id="001", serial = 0, members = mutableListOf("000", "001", "004"), name = "1st task",
                chatRoom = "003", todo = mutableListOf(), status = "proposal",
                startTime = null, endTime = null, preTask = null, deadline=null),
            Task(id="002", serial = 1, members = mutableListOf("001", "004"), name = "2nd task",
                chatRoom = "004", todo = mutableListOf(), status = "proposal",
                startTime = null, endTime = null, preTask = "001", deadline=null)
        )

        addTask.setOnClickListener {
            try {
                for (task in tasks) {
                    db.collection("Project").document("m4eORfGbZIchw1zfE1m0").collection("Task")
                        .add(task)
                }
                Log.d("add task:", "Success")
            } catch (e:Exception) {
                Log.d("add task fail:", e.message ?: null.toString())
            }
        }


        val todos = mutableListOf<Todo>(
            Todo(id = "005", serial = 0, members = mutableListOf("004"), name = "1st todo",
                status = "proposal", startTime = null, endTime = null, preTodo = null, task = null, description = "111"),
            Todo(id = "006", serial = 1, members = mutableListOf("000", "004"), name = "2nd todo",
                status = "proposal", startTime = null, endTime = null, preTodo = null, task = null, description = "222"),
            Todo(id = "007", serial = 2, members = mutableListOf("000", "004"), name = "3rd todo",
                status = "proposal", startTime = null, endTime = null, preTodo = "00002", task = null, description = "333")
        )

        addTodo.setOnClickListener {
            try {
                for (todo in todos) {
                    db.collection("Project").document("m4eORfGbZIchw1zfE1m0")
                        .collection("Task")
                        .document("3U9m6ud4OkE4lY3He9pJ")
                        .collection("Todo")
                        .add(todo)
                }
                Log.d("add todo:", "Success")
            } catch (e:Exception) {
                Log.d("add todo fail:", e.message ?: null.toString())
            }
        }

        val events = mutableListOf<Event>(
            Event(fromUser = "001", type = "team", project = null, task = null,
                todo = null, team = "ThUkrGaCYJNulR5ECb8U" , toUsers = mutableListOf("004", "005"), id = "001", time = null, messages = "加入了"),
            Event(fromUser = "004", type = "team", project = null, task = null,
                todo = null, team = "nWjaFIKAmJXtyghPEsLS" , toUsers = mutableListOf("004", "005"), id = "002", time = null, messages = "加入了"),
            Event(fromUser = "004", type = "task", project = null, task = "002",
                todo = null, team = null, toUsers = mutableListOf("004", "001"), id = "003", time = null, messages = "新增了"),
            Event(fromUser = "001", type = "todo", project = null, task = "Construct",
                todo = null, team = null, toUsers = null, id = "004", time = null, messages = "完成了"),
            Event(fromUser = "001", type = "project", project = "001", task = "Construct",
                todo = null, team = null, toUsers = null, id = "005", time = null, messages = "建立了")
        )

        addEvent.setOnClickListener {
            try {
                for (event in events) {
                    db.collection("Event")
                        .add(event)
                }
                Log.d("add event:", "Success")
            } catch (e:Exception) {
                Log.d("add event fail:", e.message ?: null.toString())
            }
        }


        return binding.root

    }
}
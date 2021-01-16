package com.eric.startupmatching.project.running.child

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.*
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProjectRunningTaskViewModel(arg: Project): ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var arg = arg

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: MutableLiveData<List<Task>>
        get() = _taskList


    //// Chat Room Navigation /////
    private val _chatRoomId = MutableLiveData<String>()
    val chatRoomId: LiveData<String>
        get() = _chatRoomId

    fun setChatRoomId(chatRoomId: String) {
        _chatRoomId.value = chatRoomId
    }


    //// update to-do status from "running" -> "done" in Firebase

    private val _sendDoneTodoInfo = MutableLiveData<Todo>()
    val sendDoneTodoInfo: LiveData<Todo>
        get() = _sendDoneTodoInfo

    fun updateTodoStatusToDone(todo: Todo) {
        coroutineScope.launch {
            db.collection("Project").document(arg.id.toString())
                .collection("Task").document(todo.task!!)
                .collection("Todo").document(todo.id.toString())
                .update("status", TodoStatus.Done.status)
                .addOnSuccessListener {
                    _sendDoneTodoInfo.value = todo
                    checkAllDone(taskList.value!!)
                }
        }
    }

    fun updateTodoStatusToRunning(todo: Todo) {
        coroutineScope.launch {
            db.collection("Project").document(arg.id.toString())
                .collection("Task").document(todo.task!!)
                .collection("Todo").document(todo.id.toString())
                .update("status", TodoStatus.Running.status)
                .addOnSuccessListener {
                    _sendDoneTodoInfo.value = todo
                }
        }
    }

    var _processTaskCount = 0

    var items = mutableListOf<Any>()

    private val _listToSubmit = MutableLiveData<List<Any>>()
    val listToSubmit: LiveData<List<Any>>
        get() = _listToSubmit

    //// Confirm project done

    private val _confirmProjectDone = MutableLiveData<Boolean>()
    val confirmProjectDone: LiveData<Boolean>
        get() = _confirmProjectDone

    fun confirmProjectDone() {
        _confirmProjectDone.value = true
    }

    fun checkAllDone(taskList: List<Task>) {
        var taskCount = 0
        for (task in taskList) {
            coroutineScope.launch {
                db.collection("Project").document(arg.id!!)
                    .collection("Task").document(task.id!!)
                    .collection("Todo")
                    .get()
                    .addOnSuccessListener {
                        var todos = it.toObjects(Todo::class.java)
                        var todoCount = 0
                        if (todos.isNullOrEmpty()) {
                            taskCount += 1
                        } else {
                            for (todo in todos) {
                                if (todo.status == TodoStatus.Done.status) {
                                    todoCount += 1
                                }
                                if (todoCount == todos.size) {
                                    taskCount += 1
                                }
                            }
                        }
                        if (taskCount == taskList.size) {
                            _projectDone.value = true
                        }
                    }
            }
        }
    }

    private val _projectDone = MutableLiveData<Boolean>()
    val projectDone: LiveData<Boolean>
        get() = _projectDone

    fun getTasks() {
        coroutineScope.launch {
            db.collection("Project").document(arg.id.toString())
                .collection("Task")
                .addSnapshotListener { value, error ->
                    var taskList = value?.toObjects(Task::class.java)
                    if (!taskList.isNullOrEmpty()) {
                        taskList.sortBy { it.serial }
                        _taskList.value = taskList
                        Log.d("_taskList", _taskList.value.toString())
                    }
                }
        }
    }

    fun getTodoByTask(task: Task) {
        var member = mutableListOf<TreeViewModel>()
        coroutineScope.launch {
            db.collection("Project").document(arg.id.toString())
                .collection("Task").document(task.id.toString())
                .collection("Todo")
                .get()
                .addOnSuccessListener {
                    var todoList = it.toObjects(Todo::class.java)
                    if (!todoList.isNullOrEmpty()) {
                        todoList.sortBy { it.serial }
                        for (todo in todoList) {
                            member.add(TaskChildModel(todo))
                        }
                    }
                    var parent = TaskParentModel(task)
                    parent.children = member as ArrayList<TreeViewModel>
                    items.add(parent)
                    Log.d("items", items.toString())
                    _processTaskCount += 1
                    Log.d("_processTaskCount", _processTaskCount.toString())
                    if (_processTaskCount == taskList.value?.size ?: 0) {
                        _listToSubmit.value = items
                        Log.d("_listToSubmit", _listToSubmit.value.toString())
                        items = mutableListOf<Any>()
                        _processTaskCount = 0
                    }
                }
                .addOnFailureListener {
                    var parent = TaskParentModel(task)
                    parent.children = member as ArrayList<TreeViewModel>
                    items.add(parent)
                    Log.d("items2", items.toString())
                    _processTaskCount += 1
                    if (_processTaskCount == taskList.value?.size ?: 0) {
                        _listToSubmit.value = items
                        Log.d("_listToSubmit2", _listToSubmit.value.toString())
                        items = mutableListOf<Any>()
                        _processTaskCount = 0
                    }
                }
        }
    }

    //Navigation to chat room

    private val _setNavigationToChatRoom = MutableLiveData<String>()
    val setNavigationToChatRoom: LiveData<String>
        get() = _setNavigationToChatRoom

    fun setNavigationToChatRoom(chatRoomId: String) {
        _setNavigationToChatRoom.value = chatRoomId
    }
}
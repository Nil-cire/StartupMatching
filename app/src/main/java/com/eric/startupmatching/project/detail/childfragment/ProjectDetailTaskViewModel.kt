package com.eric.startupmatching.project.detail.childfragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.*
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.*

class ProjectDetailTaskViewModel(arg: Project): ViewModel() {

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

    var _processTaskCount = 0

    var items = mutableListOf<Any>()

    private val _listToSubmit = MutableLiveData<List<Any>>()
    val listToSubmit: LiveData<List<Any>>
        get() = _listToSubmit


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
                .addOnSuccessListener { result ->
                    var todoList = result.toObjects(Todo::class.java)
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
        }
    }
}
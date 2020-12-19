package com.eric.startupmatching.project.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.Post
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.Task
import com.eric.startupmatching.data.Todo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class ProjectEditTeamViewModel(project: Project): ViewModel() {

    val projectArgs = project

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    private val _taskList = MutableLiveData<List<Task>>()
    val taskList: LiveData<List<Task>>
        get() = _taskList

    private val _getTodoCount = MutableLiveData<Int>()
    val getTodoCount: LiveData<Int>
        get() = _getTodoCount

    var listToSubmitHolder = mutableListOf<Any>()

    var taskInstance = Task()

    private val _listToSubmit = MutableLiveData<List<Any>>()
    val listToSubmit: LiveData<List<Any>>
        get() = _listToSubmit

    private val _taskListGet = MutableLiveData<Boolean>()
    val taskListGet: LiveData<Boolean>
        get() = _taskListGet

    //todo val
    private val _todoSize = MutableLiveData<Int>()
    val todoSize: LiveData<Int>
        get() = _todoSize

    val todoTask = MutableLiveData<Task>()

    var todoInstance = Todo()

    val db = FirebaseFirestore.getInstance()
}
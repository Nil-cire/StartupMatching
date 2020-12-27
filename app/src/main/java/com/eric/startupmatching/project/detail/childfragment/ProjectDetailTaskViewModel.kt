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

    private val _taskList2 = MutableLiveData<List<Task>>()
    val taskList2: MutableLiveData<List<Task>>
        get() = _taskList2

    private val _todoList = MutableLiveData<List<List<Todo>>>()
    val todoList: LiveData<List<List<Todo>>>
        get() = _todoList

//    private val _processTaskCount = MutableLiveData<Int>()
//    val processTaskCount: LiveData<Int>
//        get() = _processTaskCount

    var _processTaskCount = 0

    var items = mutableListOf<Any>()

    private val _listToSubmit = MutableLiveData<List<Any>>()
    val listToSubmit: LiveData<List<Any>>
        get() = _listToSubmit

    init {
//        _processTaskCount.value = 0
    }

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
//                .addOnFailureListener {
//                    var parent = TaskParentModel(task)
//                    parent.children = member as ArrayList<TreeViewModel>
//                    items.add(parent)
//                    Log.d("items2", items.toString())
//                    _processTaskCount += 1
//                    if (_processTaskCount == taskList.value?.size ?: 0) {
//                        _listToSubmit.value = items
//                        Log.d("_listToSubmit2", _listToSubmit.value.toString())
//                        items = mutableListOf<Any>()
//                        _processTaskCount = 0
//                    }
//                }
        }
    }




    // Firebase query functions
//    fun getProjectTasks(project: Project) {
//        var cont3 = 0
//        coroutineScope.launch {
//            val taskList = mutableListOf<Task>()
//            if (!project.tasks.isNullOrEmpty()) {
//                for (taskId in project.tasks) {
//                    db.collection("Task")
//                        .whereEqualTo("id", taskId)
//                        .get()
//                        .addOnSuccessListener {
//                            cont3 += 1
//                            taskList.add(it.toObjects(Task::class.java)[0])
//                            taskList.sortBy { it.id }
//                            if (cont3 == project.tasks?.size) {
//                                _taskList.value = taskList
//                                Log.d("ProjectTasks", _taskList.value.toString())
//                            }
//                        }
//                }
//            }
//        }
//    }
//
//    fun getProjectTodos(taskList: List<Task>) {
//        coroutineScope.launch {
//            var lista = mutableListOf<Task>()
//            var listb = mutableListOf<List<Todo>>()
//            var listc = mutableListOf<Todo>()
//            var cont1 = 0
//            var cont2 = 0
//
//            for (task in taskList) {
//                Log.d("tasks", task.id.toString())
//                lista.add(task)
//
//                for (todoId in task.todo!!) {
//                    delay(200)
//                    Log.d("todoId", todoId.toString())
//                    db.collection("Todo")
//                        .whereEqualTo("id", todoId)
//                        .get()
//                        .addOnCompleteListener {
//                            it.let { qs ->
//                                Log.d("todoId2", it.toString())
//                                listc.add(qs.result!!.toObjects(Todo::class.java)[0])
//                                cont2 += 1
//                                Log.d("cont4", cont2.toString())
//                                Log.d("singleTaskUser", listc.toString())
//
//                                if (cont2 == task.todo.size) {
//                                    listb.add(listc)
//                                    listc = mutableListOf()
//                                    Log.d("multiTaskUser1", listb.toString())
//                                    cont1 += 1
//                                    cont2 = 0
//                                    Log.d("cont3", cont1.toString())
//
//                                    if (cont1 == taskList.size) {
//                                        _taskList2.value = lista
//                                        _todoList.value = listb
//                                        Log.d("multiTaskUser2", todoList.value.toString())
//                                        Log.d("ProjectTask2", taskList2.value.toString())
//                                    }
//                                }
//                            }
//                        }
//                }
//            }
//        }
//    }
}
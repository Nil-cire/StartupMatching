package com.eric.startupmatching.project.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.data.*
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import com.eric.startupmatching.project.treeview.model.todo.TodoChildModel
import com.eric.startupmatching.project.treeview.model.todo.TodoParentModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class
ProjectEditTaskViewModel(project: Project): ViewModel() {

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

    fun getTaskByProject(project: Project) {
        var list = mutableListOf<Task>()
        coroutineScope.launch {
            db.collection("Project").document(project.id!!)
                .collection("Task")
//                .orderBy("serial", Query.Direction.ASCENDING)
//                .get()
                .addSnapshotListener { value, error ->
                    if (value != null) {
                        list.addAll(value.toObjects(Task::class.java))
                        list.sortBy { it.serial }
                    }
//                    list.sortBy { it.serial }
                    Log.d("editTaskList", list.toString())
                    _taskList.value = list
                    list = mutableListOf()
                    Log.d("getTaskByProject", taskList.value.toString())
                }


        }
    }

//    fun getTaskByProject(project: Project) {
//        var list = mutableListOf<Task>()
//        coroutineScope.launch {
//            db.collection("Project").document(project.id!!)
//                .collection("Task")
//                .orderBy("serial", Query.Direction.ASCENDING)
//                .get()
//                .addOnSuccessListener { qs ->
//                    list.addAll(qs.toObjects(Task::class.java))
////                    list.sortBy { it.serial }
//                    Log.d("editTaskList", list.toString())
//                    _taskList.value = list
//                    Log.d("getTaskByProject", taskList.value.toString())
//                }
//        }
//    }

    fun getTodoByTask(task: Task) {
        var list = mutableListOf<Todo>()
        var treeChildList = mutableListOf<TreeViewModel>()
        task.id?.let {
            coroutineScope.launch {
                db.collection("Project").document(projectArgs.id!!)
                    .collection("Task").document(task.id!!)
                    .collection("Todo")
                    .get()
                    .addOnSuccessListener {qs ->
                        list.addAll(qs.toObjects(Todo::class.java))
                        list.sortBy { it.serial }
                        Log.d("editTodoList", list.toString())
                        for (todo in list) {
                            treeChildList.add(TaskChildModel(todo))
                        }
                        val treeParent = TaskParentModel(task)
                        treeParent.children = treeChildList as ArrayList<TreeViewModel>
                        listToSubmitHolder.add(treeParent)
                        _getTodoCount.value = _getTodoCount.value?.plus(1)
                        if (getTodoCount.value == taskList.value?.size) {
                            var a = listToSubmitHolder as MutableList<TaskParentModel>
                            a.sortBy { it.content.serial }
                            _listToSubmit.value = a
                            listToSubmitHolder = mutableListOf()
                            _getTodoCount.value = 0
                            _taskListGet.value = true
                            Log.d("getTaskByProject2", listToSubmit.value.toString())
                            Log.d("listToSubmitHolder", listToSubmitHolder.toString())
                        }
                    }
            }
        }

    }

    fun addTask(task: Task) {
        Log.d("fuked up", projectArgs.id.toString())
        var taskId = ""
        coroutineScope.launch {
                try {
                    db.collection("Project").document(projectArgs.id!!)
                        .collection("Task")
                        .add(task)
                        .addOnSuccessListener {dc ->
                            dc.update("id", dc.id)
                            taskId = dc.id
                        }
                        .addOnSuccessListener {
                            var chatRoom = ChatRoom(id = taskId)
                            db.collection("ChatRoom")
                                .add(chatRoom)
                                .addOnSuccessListener {chatRoom->
                                    chatRoom.update("id", it.id)
                                }
                        }
                } catch (e: Exception) {
                    Log.d("fuked", projectArgs.id.toString())
                }
        }

//        val s = treeChildList[0] as TaskChildModel
//        s.content.serial = 1
//        treeChildList[0] = s
    }

    fun getTodoSize(task: Task) {
        task.id?.let {
            coroutineScope.launch {
                db.collection("Project").document(projectArgs.id!!)
                    .collection("Task").document(task.id!!)
                    .collection("Todo")
                    .get()
                    .addOnSuccessListener {
                        _todoSize.value = it.size()
                        Log.d("todoSize", it.size().toString())
                    }
            }
        }

    }

    fun addTodo(todo: Todo) {
        coroutineScope.launch {
            db.collection("Project").document(projectArgs.id!!)
                .collection("Task").document(todoTask.value?.id.toString())
                .collection("Todo")
                .add(todo)
                .addOnSuccessListener {
                    it.update("id", it.id)
                    Log.d("todoAdded", "Success")
                }
        }
    }

    fun reArrangeTaskAndTodo() {
        //re-arrange
        //upload to db
    }

    fun updateTodos(todo: Todo, task: Task) {
        coroutineScope.launch {
            db.collection("Project").document(projectArgs.id.toString())
                .collection("Task").document(task.id.toString())
                .collection("Todo").document(todo.id.toString())
                .set(todo)
        }
    }

    fun updateTasks(task: Task) {
        coroutineScope.launch {
            db.collection("Project").document(projectArgs.id.toString())
                .collection("Task").document(task.id.toString())
                .set(task)
        }
    }

    fun editTaskDescription(task: Task , description: String) {
        coroutineScope.launch {
            db.collection("Project").document(projectArgs.id.toString())
                .collection("Task").document(task.id.toString())
                .update("description", description)
        }
    }

    fun editTodoDescription(todo: Todo , description: String) {
        coroutineScope.launch {
            db.collection("Project").document(projectArgs.id.toString())
                .collection("Task").document(todo.task.toString())
                .collection("Todo").document(todo.id.toString())
                .update("description", description)
        }
    }

    //snapShotListener
    fun observeTaskDataChanged() {
        if (taskListGet.value == true) {
            coroutineScope.launch {
                db.collection("Project").document(projectArgs.id!!)
                    .collection("Task")
                    .addSnapshotListener { value, error ->
                        getTaskByProject(projectArgs)
                    }
            }
        }
    }

    fun resetTaskListGet() {
        _taskListGet.value = false
    }

    init {
        _getTodoCount.value = 0
//        observeTaskDataChanged()
    }
}



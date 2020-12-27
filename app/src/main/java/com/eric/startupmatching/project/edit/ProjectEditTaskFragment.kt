package com.eric.startupmatching.project.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.*
import com.eric.startupmatching.data.Task
import com.eric.startupmatching.data.Todo
import com.eric.startupmatching.databinding.FragmentProjectEditTaskBinding
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskChildViewBinder
import com.eric.startupmatching.project.edit.dialog.AddTaskDialog
import com.eric.startupmatching.project.edit.dialog.AddTodoDialog
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel
import kotlinx.android.synthetic.main.activity_main.*


class ProjectEditTaskFragment : Fragment(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val project = ProjectEditTaskFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTaskViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTaskViewModel::class.java)
        val binding = FragmentProjectEditTaskBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MultiTypeAdapter2(this)
        adapter.register(TaskParentModel::class.java, ProjectEditTaskParentAdapter(
            viewModel, ProjectEditTaskParentAdapter.OnClickListener{
            var task = it.content
            viewModel.todoTask.value = task
            viewModel.getTodoSize(task)
            Log.d("addTodoBtn", "Add")
        }))

        adapter.register(TaskChildModel::class.java, ProjectEditTaskChildAdapter(viewModel))

        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)

        viewModel.taskList.observe(viewLifecycleOwner, Observer { taskList ->
//            taskList.sortedByDescending { it.serial }
            for (task in taskList) {
                viewModel.getTodoByTask(task)
            }
        })

        viewModel.listToSubmit.observe(viewLifecycleOwner, Observer {list ->
            adapter.items = list
            adapter.notifyDataSetChanged()
//            Log.d("listToSubmit", it.toString())
        })

        //Show add task dialog
        requireActivity().project_edit_task.setOnClickListener {
            fragmentManager?.let { it1 -> AddTaskDialog(viewModel).show(it1, "show") }
            Log.d("listToSubmit2222", adapter.items.toString())
        }

        // add todoo
        viewModel.todoSize.observe(viewLifecycleOwner, Observer {
            fragmentManager?.let { it1 -> AddTodoDialog(viewModel).show(it1, "show") }
        })

        // done edit
        binding.create.setOnClickListener {
            var task:Task? = null
            var taskList = mutableListOf<Task>()
            var todoList = mutableListOf<Todo>()
            for ((index, taskModel) in adapter.items.withIndex()) {
                if (taskModel is TaskParentModel) {
                    task = taskModel.content
                    taskList.add(task)
                } else if (taskModel is TaskChildModel) {
                    var todo = taskModel.content
                    todoList.add(todo)
                    if (adapter.items[index].javaClass != adapter.items[index + 1].javaClass) {
                        for ((index, todo) in todoList.withIndex()) {
                            todo.serial = index
                            if (task != null) {
                                viewModel.updateTodos(todo, task)
                            }
                        }
                    }
                }
            }
            for (task in taskList) {
                viewModel.updateTasks(task)
            }
        }
        // get and snapshot data change on Firebase
        viewModel.getTaskByProject(project)

        viewModel.todoAdded.observe(viewLifecycleOwner, Observer {
            viewModel.getTaskByProjectWhenTodoAdded(project)
        })

        binding.create.setOnClickListener {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectDetailFragment(project))
        }


        return binding.root
    }


    override fun onResume() {
        super.onResume()
        requireActivity().project_edit_task.visibility = View.VISIBLE
        requireActivity().project_edit_task.text = "新增任務"
        val project = ProjectEditTaskFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTaskViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTaskViewModel::class.java)

        Log.d("ProjectResume", "Resume")
//        viewModel.observeTaskDataChanged()

        var backBtn = requireActivity().back_button
        backBtn.visibility = View.VISIBLE
        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun onPause() {
        super.onPause()
        val project = ProjectEditTaskFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTaskViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTaskViewModel::class.java)
        requireActivity().project_edit_task.visibility = View.GONE
        requireActivity().back_button.visibility = View.GONE
        viewModel.resetTaskListGet()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
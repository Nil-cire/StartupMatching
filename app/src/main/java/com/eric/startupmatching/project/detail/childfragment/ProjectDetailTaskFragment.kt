package com.eric.startupmatching.project.detail.childfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.MultiTypeAdapter2
import com.eric.startupmatching.OnStartDragListener
import com.eric.startupmatching.SimpleItemTouchHelperCallback
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.FragmentProjectDetailTaskBinding
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskChildViewBinder
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskParentViewBinder
import com.eric.startupmatching.project.treeview.model.TreeViewModel
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel

class ProjectDetailTaskFragment(val arg: Project): Fragment(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arg = arg
        val binding = FragmentProjectDetailTaskBinding.inflate(inflater, container, false)
        val viewModelFactory = ProjectDetailTaskViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailTaskViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = MultiTypeAdapter2(this)
        adapter.register(TaskParentModel::class.java, TaskParentViewBinder())
        adapter.register(TaskChildModel::class.java, TaskChildViewBinder())
        val recyclerView = binding.recyclerView
        binding.recyclerView.adapter = adapter
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)

        viewModel.taskList.observe(viewLifecycleOwner, Observer {
            Log.d("projectTaskListObsv", it.toString())
            viewModel.getProjectTodos(it)
        })

        viewModel.todoList.observe(viewLifecycleOwner, Observer {
            val items = mutableListOf<Any>()
            for (i in it.indices) {
                var member = mutableListOf<TreeViewModel>()
                val team = viewModel.taskList2.value?.get(i)?.let { it1 ->
                    TaskParentModel(it1)
                }
                for (j in it[i]) {
                    member.add(TaskChildModel(j))
                }
                if (team != null) {
                    team.children = member as ArrayList<TreeViewModel>
                    items.add(team)
                }
            }
            adapter.items = items
            adapter.notifyDataSetChanged()
            Log.d("adapteritems", items.toString())
        })
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val arg = arg
        val viewModelFactory = ProjectDetailTaskViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailTaskViewModel::class.java)
        viewModel.getProjectTasks(arg)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
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
            for (task in it) {
                viewModel.getTodoByTask(task)
            }
        })

        viewModel.listToSubmit.observe(viewLifecycleOwner, Observer {list ->

            Log.d("001xxx", list.toString())
            val x = list as ArrayList<TaskParentModel>
            Log.d("002xxx", x.toString())
            x.sortBy { it.content.serial }
            Log.d("003xxx", x.toString())
            adapter.items = x
            adapter.notifyDataSetChanged()
        })

        viewModel.getTasks()
        return binding.root
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
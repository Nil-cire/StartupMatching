package com.eric.startupmatching.project.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.*
import com.eric.startupmatching.databinding.FragmentProjectEditTaskBinding
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskChildViewBinder
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskParentViewBinder
import com.eric.startupmatching.project.edit.dialog.AddTaskDialog
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
        adapter.register(TaskParentModel::class.java, TaskParentViewBinder())
        adapter.register(TaskChildModel::class.java, TaskChildViewBinder())
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

        requireActivity().project_edit_task.setOnClickListener {
            fragmentManager?.let { it1 -> AddTaskDialog(viewModel).show(it1, "show") }
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        requireActivity().project_edit_task.visibility = View.VISIBLE
        val project = ProjectEditTaskFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTaskViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTaskViewModel::class.java)
        viewModel.getTaskByProject(project)
        viewModel.observeTaskDataChanged()

    }

    override fun onPause() {
        super.onPause()
        requireActivity().project_edit_task.visibility = View.GONE
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
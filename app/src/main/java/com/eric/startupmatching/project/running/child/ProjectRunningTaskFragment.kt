package com.eric.startupmatching.project.running.child

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.MultiTypeAdapter
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.MultiTypeAdapter2
import com.eric.startupmatching.OnStartDragListener
import com.eric.startupmatching.SimpleItemTouchHelperCallback
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.FragmentProjectDetailTaskBinding
import com.eric.startupmatching.databinding.FragmentProjectRunningTaskBinding
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTaskViewModel
import com.eric.startupmatching.project.detail.childfragment.ProjectDetailTaskViewModelFactory
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskChildViewBinder
import com.eric.startupmatching.project.detail.childfragment.adapter.task.TaskParentViewBinder
import com.eric.startupmatching.project.running.child.viewbinder.RunTaskChildViewBinder
import com.eric.startupmatching.project.running.child.viewbinder.RunTaskParentViewBinder
import com.eric.startupmatching.project.running.dialog.ProjectDoneConfirmDialog
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import com.eric.startupmatching.project.treeview.model.task.TaskParentModel

class ProjectRunningTaskFragment(val arg: Project): Fragment(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arg = arg
        val binding = FragmentProjectRunningTaskBinding.inflate(inflater, container, false)
        val viewModelFactory = ProjectRunningTaskViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectRunningTaskViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val adapter = MultiTypeAdapter()
        adapter.register(TaskParentModel::class.java, RunTaskParentViewBinder(viewModel))
        adapter.register(TaskChildModel::class.java, RunTaskChildViewBinder(viewModel))

//        val recyclerView = binding.recyclerView
        binding.recyclerView.adapter = adapter
//        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
//        mItemTouchHelper = ItemTouchHelper(callback)
//        mItemTouchHelper!!.attachToRecyclerView(recyclerView)
        binding.noData.visibility = View.GONE

        viewModel.taskList.observe(viewLifecycleOwner, Observer {
            Log.d("projectTaskListObsv", it.toString())
            for (task in it) {
                viewModel.getTodoByTask(task)
            }
        })

        viewModel.listToSubmit.observe(viewLifecycleOwner, Observer {list ->
            Log.d("listToSubmit2", list.toString())
            var x = list as ArrayList<TaskParentModel>
            if (list.isNullOrEmpty()) {
                binding.noData.visibility = View.VISIBLE
            } else {
                x.sortBy { it.content.serial }
                adapter.items = x
                adapter.notifyDataSetChanged()
            }
        })


        //// Jump to Chat Room on clicking chat-btn ////
        viewModel.chatRoomId.observe(viewLifecycleOwner, Observer {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalChatRoomDetailFragment(it))
        })

        viewModel.sendDoneTodoInfo.observe(viewLifecycleOwner, Observer{
            //TODO create info with to-do and send it to all users in project
        })

        //// Check if to-dos are all done, if so, show project done confirm dialog
        viewModel.projectDone.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Log.d("ProjectAllDone", "DONE")
                fragmentManager?.let { it1 -> ProjectDoneConfirmDialog(viewModel).show(it1, "confirmDialog") }
            }
        })

        //// Navigate to project done page if confirmed
        viewModel.confirmProjectDone.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                //TODO navigate to project Done
            }
        })

//        viewModel.observeTaskDataChanges(arg)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val arg = arg
        val viewModelFactory = ProjectRunningTaskViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectRunningTaskViewModel::class.java)
        viewModel.getTasks()
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
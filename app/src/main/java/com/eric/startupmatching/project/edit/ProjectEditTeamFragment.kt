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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.MultiTypeAdapter2
import com.eric.startupmatching.OnStartDragListener
import com.eric.startupmatching.SimpleItemTouchHelperCallback
import com.eric.startupmatching.databinding.FragmentProjectEditTeamBinding
import com.eric.startupmatching.project.edit.dialog.AddTeamDialog
import com.eric.startupmatching.project.treeview.model.team.TeamChildModel
import com.eric.startupmatching.project.treeview.model.team.TeamParentModel
import kotlinx.android.synthetic.main.activity_main.*

class ProjectEditTeamFragment : Fragment(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val project = ProjectEditTeamFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTeamViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTeamViewModel::class.java)
        val binding = FragmentProjectEditTeamBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val adapter = MultiTypeAdapter2(this)
        adapter.register(
            TeamParentModel::class.java, ProjectEditTeamParentAdapter(
            viewModel, ProjectEditTeamParentAdapter.OnClickListener{
//                var task = it.content
//                viewModel.todoTeam.value = task
//                viewModel.getTodoSize(task)
//                Log.d("addTodoBtn", "Add")
            }))

        adapter.register(TeamChildModel::class.java, ProjectEditTeamChildAdapter(viewModel))
        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)

        viewModel.teamIdList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                for (teamId in it) {
                    Log.d("aTeammmm", teamId.toString())
                    viewModel.getTeamsByIds(teamId)
                }
            }
        })

        viewModel.teamList.observe(viewLifecycleOwner, Observer {
            for (team in it) {
                viewModel.getUserByTeam(team)
            }
        })



        viewModel.listToSubmit.observe(viewLifecycleOwner, Observer {list ->
            adapter.items = list
            adapter.notifyDataSetChanged()
//            Log.d("listToSubmit", it.toString())
        })

        requireActivity().project_edit_task.setOnClickListener {
            fragmentManager?.let { it1 -> AddTeamDialog(viewModel).show(it1, "show") }
            Log.d("listToSubmit2222", adapter.items.toString())
        }

        // add todoo
//        viewModel.todoSize.observe(viewLifecycleOwner, Observer {
//            fragmentManager?.let { it1 -> AddTeamDialog(viewModel).show(it1, "show") }
//        })

        // done edit
//        binding.create.setOnClickListener {
//            var task: Team? = null
//            var taskList = mutableListOf<Team>()
//            var todoList = mutableListOf<Todo>()
//            for ((index, taskModel) in adapter.items.withIndex()) {
//                if (taskModel is TeamParentModel) {
//                    task = taskModel.content
//                    taskList.add(task)
//                } else if (taskModel is TeamChildModel) {
//                    var todo = taskModel.content
//                    todoList.add(todo)
//                    if (adapter.items[index].javaClass != adapter.items[index + 1].javaClass) {
//                        for ((index, todo) in todoList.withIndex()) {
//                            todo.serial = index
//                            if (task != null) {
//                                viewModel.updateTodos(todo, task)
//                            }
//                        }
//                    }
//                }
//            }
//            for (task in taskList) {
//                viewModel.updateTeams(task)
//            }
//        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        requireActivity().project_edit_task.visibility = View.VISIBLE
        val project = ProjectEditTeamFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTeamViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTeamViewModel::class.java)
        viewModel.getTeamsByProject(project)
        Log.d("ProjectResume", "Resume")
//        viewModel.observeTeamDataChanged()

        var backBtn = requireActivity().back_button
        backBtn.visibility = View.VISIBLE
        backBtn.setOnClickListener {
            requireActivity().onBackPressed()
        }

    }

    override fun onPause() {
        super.onPause()
        requireActivity().project_edit_task.visibility = View.GONE
        requireActivity().back_button.visibility = View.GONE
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
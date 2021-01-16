package com.eric.startupmatching.project.edit

import android.app.FragmentManager
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
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.MultiTypeAdapter2
import com.eric.startupmatching.OnStartDragListener
import com.eric.startupmatching.SimpleItemTouchHelperCallback
import com.eric.startupmatching.databinding.FragmentProjectEditTeamBinding
import com.eric.startupmatching.project.edit.dialog.AddTeamDialog
import com.eric.startupmatching.project.edit.dialog.SelectMemberDialog
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
        val adapter = ProjectEditTeamAdapter(ProjectEditTeamAdapter.OnClickListener{},
            viewModel, ProjectEditTeamAdapter.OnClickListener2{})
        binding.recyclerView.adapter = adapter

        viewModel.teamIdList.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty()) {
                    Log.d("aTeammmm", it.toString())
                    viewModel.getTeamsByIds(it)
            }
        })

        viewModel.teamList.observe(viewLifecycleOwner, Observer { list ->
            list.sortedBy { it.teamLeader }
            adapter.submitList(list)
            Log.d("listSubmitxx", list.toString())
            adapter.notifyDataSetChanged()
        })

        viewModel.editTeam.observe(viewLifecycleOwner, Observer {
            viewModel.selectedTeam = it
            fragmentManager?.let { it1 -> SelectMemberDialog(viewModel).show(it1, "addMember") }
        })

        requireActivity().project_edit_task.setOnClickListener {
            fragmentManager?.let { it1 -> AddTeamDialog(viewModel).show(it1, "show") }
        }

        binding.create.setOnClickListener {
            this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectDetailFragment(project))
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        requireActivity().project_edit_task.visibility = View.VISIBLE
        requireActivity().project_edit_task.text = "新增團隊"
        val project = ProjectEditTeamFragmentArgs.fromBundle(requireArguments()).projectArgs
        val viewModelFactory = ProjectEditTeamViewModelFactory(project)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectEditTeamViewModel::class.java)
        viewModel.getTeamsByProject(project)
        Log.d("ProjectResume", "Resume")
        viewModel.observeTeamDataChanged()

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
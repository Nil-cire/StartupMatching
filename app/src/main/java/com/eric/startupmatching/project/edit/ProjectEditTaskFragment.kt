package com.eric.startupmatching.project.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.MainFragment
import com.eric.startupmatching.OnStartDragListener
import com.eric.startupmatching.RecyclerListAdapter
import com.eric.startupmatching.SimpleItemTouchHelperCallback
import com.eric.startupmatching.databinding.FragmentProjectEditTaskBinding
import kotlinx.android.synthetic.main.activity_main.*


class ProjectEditTaskFragment : Fragment(), OnStartDragListener {
    private var mItemTouchHelper: ItemTouchHelper? = null

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProjectEditTaskBinding.inflate(inflater, container, false)
        val adapter = RecyclerListAdapter(requireActivity(), this)
        val recyclerView = binding.recyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val callback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(adapter)
        mItemTouchHelper = ItemTouchHelper(callback)
        mItemTouchHelper!!.attachToRecyclerView(recyclerView)

        requireActivity().project_edit_task.setOnClickListener {

        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().project_edit_task.visibility = View.VISIBLE

    }

    override fun onPause() {
        super.onPause()
        requireActivity().project_edit_task.visibility = View.GONE
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        mItemTouchHelper!!.startDrag(viewHolder!!)
    }
}
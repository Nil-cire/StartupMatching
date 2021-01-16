package com.eric.startupmatching.team.information

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eric.startupmatching.data.*
import com.eric.startupmatching.databinding.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TeamInformationAdapter(val onClickListener: OnClickListener) : ListAdapter<Event, RecyclerView.ViewHolder>(CategoryDiffCallback) {

    class TeamViewHolder(var binding: ItemTeamInfoTeamRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        val db = FirebaseFirestore.getInstance()
        private var viewModelJob = Job()
        private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
        fun bind(event: Event) {
            Log.d("TeamViewHolder", event.toString())
            binding.event = event
            binding.executePendingBindings()
            coroutineScope.launch {
                db.collection("User")
                    .whereEqualTo("id", event.fromUser)
                    .get()
                    .addOnSuccessListener {
                        var user = it.toObjects(User::class.java)
                        binding.dutyStatus.text = user[0].name
                    }
            }
        }
    }

    class TaskViewHolder(var binding: ItemTeamInfoTaskRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        val db = FirebaseFirestore.getInstance()
        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()
            db.collection("User")
                .whereEqualTo("id", event.fromUser)
                .get()
                .addOnSuccessListener {
                    val user = it.toObjects(User::class.java)
                    binding.dutyStatus.text = user[0].name
                }
            db.collection("Task")
                .whereEqualTo("id", event.task)
                .get()
                .addOnSuccessListener {
                    val task = it.toObjects(Task::class.java)
                    binding.time2.text = task[0].name
                }
        }
    }

    class TodoViewHolder(var binding: ItemTeamInfoTodoRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        val db = FirebaseFirestore.getInstance()
        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()

            db.collection("User")
                .whereEqualTo("id", event.fromUser)
                .get()
                .addOnSuccessListener {
                    val user = it.toObjects(User::class.java)
                    binding.dutyStatus.text = user[0].name
                }
            db.collection("Todo")
                .whereEqualTo("id", event.todo)
                .get()
                .addOnSuccessListener {
                    val todo = it.toObjects(Todo::class.java)
                    binding.time2.text = todo[0].name
                }
        }
    }

    class ProjectViewHolder(var binding: ItemTeamInfoProjectRecyclerViewBinding):
        RecyclerView.ViewHolder(binding.root) {
        val db = FirebaseFirestore.getInstance()
        fun bind(event: Event) {
            binding.event = event
            binding.executePendingBindings()

            db.collection("User")
                .whereEqualTo("id", event.fromUser)
                .get()
                .addOnSuccessListener {
                    val user = it.toObjects(User::class.java)
                    binding.dutyStatus.text = user[0].name
                }
            db.collection("Project")
                .whereEqualTo("id", event.project)
                .get()
                .addOnSuccessListener {
                    val project = it.toObjects(Project::class.java)
                    binding.time2.text = project[0].projectName
                }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            "team" -> 0
            "task" -> 1
            "todo" -> 2
            else -> 3 // "project"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> TeamViewHolder(
                ItemTeamInfoTeamRecyclerViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            1 -> TaskViewHolder(
                ItemTeamInfoTaskRecyclerViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            2 -> TodoViewHolder(
                ItemTeamInfoTodoRecyclerViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            3 -> ProjectViewHolder(
                ItemTeamInfoProjectRecyclerViewBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val chatRoom = getItem(position)
        when (holder) {
            is TeamViewHolder -> {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(chatRoom)
                }
                holder.bind(chatRoom)
            }
            is TaskViewHolder -> {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(chatRoom)
                }
                holder.bind(chatRoom)
            }
            is TodoViewHolder -> {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(chatRoom)
                }
                holder.bind(chatRoom)
            }
            is ProjectViewHolder -> {
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(chatRoom)
                }
                holder.bind(chatRoom)
            }
            else -> {
                Log.d("Boooo", "cant bind data")
            }
        }
    }



    companion object CategoryDiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (event: Event) -> Unit) {
        fun onClick(event: Event) = clickListener(event)
    }
}
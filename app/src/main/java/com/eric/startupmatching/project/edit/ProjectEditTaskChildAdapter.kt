package com.eric.startupmatching.project.edit

import android.app.Activity
import android.text.InputType
import android.text.InputType.TYPE_NULL
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.drakeet.multitype.ItemViewBinder
import com.eric.startupmatching.OnExpandAndHideListener
import com.eric.startupmatching.TreeViewUtil
import com.eric.startupmatching.data.EditTodoDescriptionBtn
import com.eric.startupmatching.databinding.ItemProjectEditTodoRecyclerViewBinding
import com.eric.startupmatching.project.treeview.model.task.TaskChildModel
import kotlinx.android.synthetic.main.item_child.view.*
import kotlinx.android.synthetic.main.item_child.view.tvContent
import kotlinx.android.synthetic.main.item_project_edit_todo_recycler_view.view.*


class ProjectEditTaskChildAdapter(var viewModel: ProjectEditTaskViewModel) : ItemViewBinder<TaskChildModel, ProjectEditTaskChildAdapter.ViewHolder>() {
    private lateinit var mExpandAndHideListener: OnExpandAndHideListener

    class ViewHolder(var binding: ItemProjectEditTodoRecyclerViewBinding,
                     var viewModel: ProjectEditTaskViewModel,
                     var holderType: Int) : RecyclerView.ViewHolder(binding.root) {

        private fun hideKeyboard(view: View) {
            val inputMethodManager = view.context.getSystemService(Activity.INPUT_METHOD_SERVICE)
                    as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun bind(model: TaskChildModel) {
            binding.model = model
            holderType = 1

            binding.name.text = model.content.name

            binding.detailText.isFocusable = true
            binding.detailText.isFocusableInTouchMode = true
            binding.detailText.inputType = TYPE_NULL

            binding.confirmButton.setOnClickListener {
                Log.d("BTN", "Clicked")
                if (binding.confirmButton.text == EditTodoDescriptionBtn.Edit.type) {
                    binding.confirmButton.text = EditTodoDescriptionBtn.Confirm.type
                    binding.detailText.inputType = InputType.TYPE_CLASS_TEXT
                } else {
                    binding.confirmButton.text = EditTodoDescriptionBtn.Edit.type
                    var description = binding.detailText.text.toString()
                    viewModel.editTodoDescription(model.content, description)
                    binding.detailText.text= null
                    hideKeyboard(binding.detailText)
                    binding.detailText.inputType = TYPE_NULL
                }
            }
            binding.detailText.setText(model.content.description)
        }
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectEditTodoRecyclerViewBinding.inflate(layoutInflater, parent, false)

        mExpandAndHideListener = TreeViewUtil.generateExpandAndHideListener()
        return ViewHolder(binding, viewModel, 1)
    }

    override fun onBindViewHolder(holder: ViewHolder, model: TaskChildModel) {

        holder.bind(model)
        holder.itemView.detail_text.addTextChangedListener {
        }
    }
}
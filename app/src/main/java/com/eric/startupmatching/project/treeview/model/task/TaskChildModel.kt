package com.eric.startupmatching.project.treeview.model.task

import com.eric.startupmatching.data.Todo
import com.eric.startupmatching.project.treeview.model.TreeViewModel

data class TaskChildModel(val content: Todo) : TreeViewModel()
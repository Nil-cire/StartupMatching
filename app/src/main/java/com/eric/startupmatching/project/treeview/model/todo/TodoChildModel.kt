package com.eric.startupmatching.project.treeview.model.todo

import com.eric.startupmatching.data.Todo
import com.eric.startupmatching.project.treeview.model.TreeViewModel

data class TodoChildModel(val content: Todo) : TreeViewModel()
package com.eric.startupmatching.project.treeview.model.task

import com.eric.startupmatching.data.Task
import com.eric.startupmatching.data.Team
import com.eric.startupmatching.project.treeview.model.TreeViewModel

data class TaskParentModel(val content: Task) : TreeViewModel()
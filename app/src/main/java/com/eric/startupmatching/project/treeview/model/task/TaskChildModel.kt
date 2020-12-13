package com.eric.startupmatching.project.treeview.model.task

import com.eric.startupmatching.data.Todo
import com.eric.startupmatching.project.treeview.model.TreeViewModel


/**
 * Created by zzw on 2018/6/22.
 */
data class TaskChildModel(val content: Todo) : TreeViewModel()
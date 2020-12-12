package com.eric.startupmatching.project.detail.childfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.drakeet.multitype.MultiTypeAdapter
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.databinding.FragmentProjectDetailTeamBinding
import com.eric.startupmatching.project.treeview.adapter.ChildViewBinder
import com.eric.startupmatching.project.treeview.adapter.ParentViewBinder
import com.eric.startupmatching.project.treeview.model.ChildModel
import com.eric.startupmatching.project.treeview.model.ParentModel
import com.eric.startupmatching.project.treeview.model.TreeViewModel

class ProjectDetailTeamFragment(val arg: Project): Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val arg = arg
        val binding = FragmentProjectDetailTeamBinding.inflate(inflater, container, false)
        val viewModelFactory = ProjectDetailTeamViewModelFactory(arg)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(ProjectDetailTeamViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        val adapter = MultiTypeAdapter()
        adapter.register(ParentModel::class.java, ParentViewBinder())
        adapter.register(ChildModel::class.java, ChildViewBinder())

        binding.recyclerView.adapter = adapter
        adapter.items = generateItems()



        return binding.root
    }
    private fun generateItems(): ArrayList<Any> {
        val items = ArrayList<Any>()
        val beiChild = arrayListOf<TreeViewModel>(ChildModel("刘封"), ChildModel("刘禅"), ChildModel("刘永"), ChildModel("刘理"))
        val liangChild = arrayListOf<TreeViewModel>(ChildModel("诸葛乔"), ChildModel("诸葛瞻"), ChildModel("诸葛怀"), ChildModel("诸葛果"))
        val yuChild = arrayListOf<TreeViewModel>(ChildModel("关平"), ChildModel("关兴"))
        val feiChild = arrayListOf<TreeViewModel>(ChildModel("张苞"), ChildModel("张绍"))
        val yunChild = arrayListOf<TreeViewModel>(ChildModel("赵统"), ChildModel("赵广"))
        val bei = ParentModel("刘备")
        bei.children = beiChild
        val liang = ParentModel("诸葛亮")
        liang.children = liangChild
        val yu = ParentModel("关羽")
        yu.children = yuChild
        val fei = ParentModel("张飞")
        fei.children = feiChild
        val yun = ParentModel("赵云")
        yun.children = yunChild
        items.add(bei)
        items.add(liang)
        items.add(yu)
        items.add(fei)
        items.add(yun)
        return items
    }
}
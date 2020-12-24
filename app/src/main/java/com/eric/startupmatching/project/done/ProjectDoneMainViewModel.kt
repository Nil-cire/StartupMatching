package com.eric.startupmatching.project.done

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Post
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ProjectDoneMainViewModel(arg: Project): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    var projectArgs = arg

    val db = FirebaseFirestore.getInstance()

    private val _team = MutableLiveData<Team>()
    val team: LiveData<Team>
        get() = _team



    private val _teamList = MutableLiveData<List<Team>>()
    val teamList: MutableLiveData<List<Team>>
        get() = _teamList

    private val _projectList = MutableLiveData<List<Project>>()
    val projectList: LiveData<List<Project>>
        get() = _projectList

    val user = UserInfo.currentUser

    //// creating a post by comment and photo -> then create achievement for each member with postId included

    private val _addPostComplete = MutableLiveData<Boolean>()
    val addPostComplete: LiveData<Boolean>
        get() = _addPostComplete

    fun postProject(content: String, imageUrl: String?) {
        val post = Post(content = content, image = imageUrl, poster = UserInfo.currentUser.value?.id!!, timeDate = Calendar.getInstance().time)
        coroutineScope.launch {
            db.collection("Post")
                .add(post)
                .addOnSuccessListener {
                    it.update("id", it.id)
                }.addOnSuccessListener {
                    _addPostComplete.value = true
                }
        }
    }
}
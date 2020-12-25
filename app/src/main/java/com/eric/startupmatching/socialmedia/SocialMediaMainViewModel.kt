package com.eric.startupmatching.socialmedia
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SocialMediaMainViewModel: ViewModel() {

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    val db = FirebaseFirestore.getInstance()

    //// get all Posts, User's included
    fun getPosts(userId: String) {
        var list = mutableListOf<Post>()
        coroutineScope.launch {
            db.collection("Post")
//                .whereNotEqualTo("poster", userId)
//                .orderBy("timeDate")
                .get()
                .addOnSuccessListener { it2 ->
                    Log.d("postLista", it2.toString())
                    it2?.let {QuerySnapshot ->
                        QuerySnapshot.toObjects(Post::class.java).forEach{ post ->
                            list.add(post)
                            Log.d("postList", list.toString())
                        }
                        list.sortByDescending { it.timeDate } // New Posts on Top Of screen
                        _postList.value = list
                        Log.d("postListaa", _postList.value.toString())
                    }
                }
        }
    }

    fun observeNewPosts(userId: String) {
        db.collection("Post")
            .whereNotEqualTo("poster", UserInfo.currentUser.value?.id)
            .addSnapshotListener { value, error ->
                value?.let {
                    getPosts(userId)
                }
            }
    }

    init {
        UserInfo.currentUser.value?.id?.let { getPosts(it) }
    }
}
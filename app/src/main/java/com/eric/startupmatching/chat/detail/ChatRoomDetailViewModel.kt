package com.eric.startupmatching.chat.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eric.startupmatching.UserInfo
import com.eric.startupmatching.data.ChatRoom
import com.eric.startupmatching.data.Message
import com.eric.startupmatching.data.Project
import com.eric.startupmatching.data.Team
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ChatRoomDetailViewModel(arg: String): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)


    private val _messageList = MutableLiveData<List<Message>>()
    val messageList: LiveData<List<Message>>
        get() = _messageList

    private val _messageIdList = MutableLiveData<List<String>>()
    val messageIdList: LiveData<List<String>>
        get() = _messageIdList

    var singleMessageId = MutableLiveData<String>()

    private val _messageAdded = MutableLiveData<Int>()
    val messageAdded: LiveData<Int>
        get() = _messageAdded

    var chatRoomId = arg

    val db = FirebaseFirestore.getInstance()

    fun getMessagesId(userId: String) {
        var list = mutableListOf<String>()
        coroutineScope.launch {
            db.collection("ChatRoom")
                .whereEqualTo("id", chatRoomId)
                .get()
                .addOnSuccessListener {
                    Log.d("Geta ChatRoomList", it.toString())
                    it?.let {
                        it.toObjects(ChatRoom::class.java).forEach{ chatRoom ->
                            chatRoom.messages?.let {
                                for (id in chatRoom.messages) {
                                    if (id != null) {
                                        list.add(id)
                                    }
                                }
                            }
                        }
                        _messageIdList.value = list
                    }
                }
        }
    }

    fun getMessage(list: List<String>) {
        val listForQuery = mutableListOf<Message>()
        coroutineScope.launch {
            for (id in list) {
                db.collection("Message")
                    .whereEqualTo("id", id)
                    .get()
                    .addOnSuccessListener { it ->
//                        Log.d("Geta", it.toString())
                        it?.let{
//                            Log.d("Getaa", it.toString())
                            it.toObjects(Message::class.java).forEach{ message ->
//                                Log.d("Getaaa", message.toString())
                                listForQuery.add(message)
//                                Log.d("Getaaaa", listForQuery.toString())
                            }
                        }

                        _messageList.value = listForQuery.sortedByDescending { list1 -> list1.postTimestamp }
//                        Log.d("Getaaaaa", _messageList.value.toString())
                    }
            }
        }
//        _messageList.value = listForQuery
//        Log.d("Getaaaaa", _messageList.value.toString())
    }

    fun sendText(text: String) {
        var message = Message(
            "",
            text,
            UserInfo.currentUser.value!!.id!!,
            Calendar.getInstance().time,
            mutableListOf(UserInfo.currentUser.value!!.id)
        )
        db.collection("Message").add(message).addOnSuccessListener {
            it.update("id", it.id)
            singleMessageId.value = it.id
            _messageAdded.value = 1
        }
    }

    fun addMessageToChatRoomFitebase() {
        db.collection("ChatRoom")
            .whereEqualTo("id", chatRoomId)
            .get()
            .addOnSuccessListener {
                var messageList = mutableListOf<String>()
                it.documents[0].toObject(ChatRoom::class.java)?.messages?.let { list ->
                    for (id in list) {
                        if (id != null) {
                            messageList.add(id)
                        }
                    }
                }
                messageList.add(singleMessageId.value!!)
                it.documents[0].reference.update("messages", messageList)
                Log.d("setMsgToChatroom", "send")
            }
    }

    fun observeNewMessage() {
        db.collection("ChatRoom")
            .whereEqualTo("id", chatRoomId)
            .addSnapshotListener { value, error ->
                value?.let {
                    UserInfo.currentUser.value?.id.let { it1 -> getMessagesId(it1!!) }
                    Log.d("setMsgToChatroom", "update")
                }
            }
    }

    init {
        UserInfo.currentUser.value!!.id?.let { getMessagesId(it) }
    }
}
package com.eric.startupmatching.chat.detail

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eric.startupmatching.databinding.FragmentChatRoomDetailBinding


class ChatRoomDetailFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatRoomDetailBinding.inflate(inflater, container, false)
        val argument = ChatRoomDetailFragmentArgs.fromBundle(requireArguments()).chatRoomIdArgs
        val viewModelProvider = ChatRoomDetailViewModelFactory(argument)
        val viewModel = ViewModelProvider(this, viewModelProvider).get(ChatRoomDetailViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        val adapter = ChatRoomDetailAdapter(ChatRoomDetailAdapter.OnClickListener{})
        binding.chatroomDetailRecyclerView.adapter = adapter

        viewModel.messageIdList.observe(viewLifecycleOwner, Observer {
            Log.d("Observea MessageIdList", it.toString())
            viewModel.getMessage(it)
        })

        viewModel.messageList.observe(viewLifecycleOwner, Observer {
            Log.d("Observea MessageList", it.toString())
            adapter.submitList(it)
        })


        //// send text and upload to firebase
        binding.sendBtn.setOnClickListener {
            var text = binding.inputMessage.text.toString()
            binding.inputMessage.text.clear()
            viewModel.sendText(text)
            hideKeyboard(this.requireActivity())
        }

        viewModel.messageAdded.observe(viewLifecycleOwner, Observer {
            viewModel.addMessageToChatRoomFitebase()
        })

        viewModel.observeNewMessage()

        return binding.root
    }

    fun hideKeyboard(activity: Activity) {
        val imm =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
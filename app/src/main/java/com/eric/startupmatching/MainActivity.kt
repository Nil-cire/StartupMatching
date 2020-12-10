package com.eric.startupmatching

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.findNavController
import com.eric.startupmatching.databinding.ActivityMainBinding
import com.eric.startupmatching.login.UserManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        if(!UserManager.isLoggedIn){
//            startActivity(Intent(this, LoginActivity::class.java))
//        } else {
//
//        }
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        this.supportActionBar?.hide()
//        this.actionBar?.hide()
        val binding = androidx.databinding.DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val teamBtn = binding.assistant
        val matchingBtn = binding.search
        val projectBtn = binding.projects
        val chatBtn = binding.messages
        val socialmediaBtn = binding.socialmedia


        //setup icons of bottom navigation
        fun assistantPageIconSetup() {
            teamBtn.setImageResource(R.drawable.baseline_fiber_new_white_24dp)
            matchingBtn.setImageResource(R.drawable.baseline_folder_open_black_24dp)
            projectBtn.setImageResource(R.drawable.baseline_search_project_black_24dp)
            chatBtn.setImageResource(R.drawable.baseline_forum_black_24dp)
            socialmediaBtn.setImageResource((R.drawable.baseline_language_black_24dp))
        }

        fun matchPageIconSetup() {
            teamBtn.setImageResource(R.drawable.baseline_fiber_new_black_24dp)
            matchingBtn.setImageResource(R.drawable.baseline_folder_open_white_24dp)
            projectBtn.setImageResource(R.drawable.baseline_search_project_black_24dp)
            chatBtn.setImageResource(R.drawable.baseline_forum_black_24dp)
            socialmediaBtn.setImageResource((R.drawable.baseline_language_black_24dp))
        }

        fun projectPageIconSetup() {
            teamBtn.setImageResource(R.drawable.baseline_fiber_new_black_24dp)
            matchingBtn.setImageResource(R.drawable.baseline_folder_open_black_24dp)
            projectBtn.setImageResource(R.drawable.baseline_search_project_white_24dp)
            chatBtn.setImageResource(R.drawable.baseline_forum_black_24dp)
            socialmediaBtn.setImageResource((R.drawable.baseline_language_black_24dp))
        }

        fun chatPageIconSetup() {
            teamBtn.setImageResource(R.drawable.baseline_fiber_new_black_24dp)
            matchingBtn.setImageResource(R.drawable.baseline_folder_open_black_24dp)
            projectBtn.setImageResource(R.drawable.baseline_search_project_black_24dp)
            chatBtn.setImageResource(R.drawable.baseline_forum_white_24dp)
            socialmediaBtn.setImageResource((R.drawable.baseline_language_black_24dp))
        }

        fun socialMediaPageIconSetup() {
            teamBtn.setImageResource(R.drawable.baseline_fiber_new_black_24dp)
            matchingBtn.setImageResource(R.drawable.baseline_folder_open_black_24dp)
            projectBtn.setImageResource(R.drawable.baseline_search_project_black_24dp)
            chatBtn.setImageResource(R.drawable.baseline_forum_black_24dp)
            socialmediaBtn.setImageResource((R.drawable.baseline_language_white_24dp))
        }

        //setup bottom navigation buttons
        teamBtn.setOnClickListener {
            assistantPageIconSetup()
//            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalAssistantFragment())
        }

        matchingBtn.setOnClickListener {
            matchPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalProjectMainFragment())
        }

        projectBtn.setOnClickListener {
            projectPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalMatchMainFragment())
        }

        chatBtn.setOnClickListener {
            chatPageIconSetup()
//            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalChatroomMainFragment())
        }

        socialmediaBtn.setOnClickListener {
            socialMediaPageIconSetup()
//            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalSocialMediaMain())
        }

        //tool bar button functions



    }
}
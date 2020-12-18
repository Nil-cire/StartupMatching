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
            teamBtn.alpha = 1.0F
            matchingBtn.alpha = 0.3F
            projectBtn.alpha = 0.3F
            chatBtn.alpha = 0.3F
            socialmediaBtn.alpha = 0.3F
        }

        fun matchPageIconSetup() {
            teamBtn.alpha = 0.3F
            matchingBtn.alpha = 1.0F
            projectBtn.alpha = 0.3F
            chatBtn.alpha = 0.3F
            socialmediaBtn.alpha = 0.3F
        }

        fun projectPageIconSetup() {
            teamBtn.alpha = 0.3F
            matchingBtn.alpha = 0.3F
            projectBtn.alpha = 1.0F
            chatBtn.alpha = 0.3F
            socialmediaBtn.alpha = 0.3F
        }

        fun chatPageIconSetup() {
            teamBtn.alpha = 0.3F
            matchingBtn.alpha = 0.3F
            projectBtn.alpha = 0.3F
            chatBtn.alpha = 1.0F
            socialmediaBtn.alpha = 0.3F
        }

        fun socialMediaPageIconSetup() {
            teamBtn.alpha = 0.3F
            matchingBtn.alpha = 0.3F
            projectBtn.alpha = 0.3F
            chatBtn.alpha = 0.3F
            socialmediaBtn.alpha = 1.0F
        }

        //setup bottom navigation buttons
        teamBtn.setOnClickListener {
            assistantPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalTeamMainFragment())
        }

        matchingBtn.setOnClickListener {
            matchPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalProjectMainFragment())
        }

        projectBtn.setOnClickListener {
            projectPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalPersonMainFragment())
        }

        chatBtn.setOnClickListener {
            chatPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalChatRoomMainFragment())
        }

        socialmediaBtn.setOnClickListener {
            socialMediaPageIconSetup()
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalSocialMediaMainFragment())
        }

        //tool bar button functions





    }
}
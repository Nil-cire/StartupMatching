package com.eric.startupmatching

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.eric.startupmatching.data.FragmentName
import com.eric.startupmatching.databinding.ActivityMainBinding
import com.eric.startupmatching.login.UserManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//         check if User has signed in, if not, jump to login activity
        if (UserManager.userToken == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // status bar controll
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        this.supportActionBar?.hide()
//        this.actionBar?.hide()

        val binding = androidx.databinding.DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val teamBtn = binding.assistant
        val matchingBtn = binding.search
        val projectBtn = binding.projects
        val chatBtn = binding.messages
        val socialmediaBtn = binding.socialmedia


        //setup bottom navigation icons display while navigation
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

        //setup bottom navigation buttons onClick function
        teamBtn.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalSocialMediaMainFragment())
        }

        matchingBtn.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalProjectMainFragment())
        }

        projectBtn.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalPersonMainFragment())
        }

        chatBtn.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalChatRoomMainFragment())
        }

        socialmediaBtn.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(MainNavigationDirections.actionGlobalProfileFragment())
        }

        //tool bar button functions

        // fragment type detection for Corresponding change
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { controller, destination, arguments ->
            viewModel.fragmentType.value = when (controller.currentDestination?.id) {
                R.id.socialMediaMainFragment -> FragmentName.SocialMediaFragment.type
                R.id.projectMainFragment -> FragmentName.ProjectMainFragment.type
                R.id.personMainFragment -> FragmentName.PeopleMainFragment.type
                R.id.chatRoomMainFragment -> FragmentName.ChatRoomMainFragment.type
                R.id.teamMainFragment -> FragmentName.ProfileMainFragment.type
                else -> viewModel.fragmentType.value
            }
        }

        viewModel.fragmentType.observe(this, Observer {
            when (it) {
                FragmentName.SocialMediaFragment.type -> assistantPageIconSetup()
                FragmentName.ProjectMainFragment.type -> matchPageIconSetup()
                FragmentName.PeopleMainFragment.type -> projectPageIconSetup()
                FragmentName.ChatRoomMainFragment.type -> chatPageIconSetup()
                FragmentName.ProfileMainFragment.type -> socialMediaPageIconSetup()
            }
        })
    }
}
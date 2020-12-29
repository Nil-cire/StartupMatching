package com.eric.startupmatching.login

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.eric.startupmatching.MainNavigationDirections
import com.eric.startupmatching.databinding.FragmentLoginMainBinding

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginMainBinding.inflate(inflater, container, false)

//        binding.lottie.playAnimation() //播放
////
////        binding.lottie.pauseAnimation() //暫停
////
////        binding.lottie.cancelAnimation() //取消
////
////        binding.lottie.getDuration() //獲取動畫時長
//
//        binding.lottie.addAnimatorListener(object : Animator.AnimatorListener {
//            //新增動畫監聽
//            override fun onAnimationStart(animation: Animator?) {}
//            override fun onAnimationEnd(animation: Animator?) {}
//            override fun onAnimationCancel(animation: Animator?) {}
//            override fun onAnimationRepeat(animation: Animator?) {}
//        })

//        binding.loginBtn.setOnClickListener {
////            binding.lottie.cancelAnimation()
//            this.findNavController().navigate(MainNavigationDirections.actionGlobalProjectMainFragment())
//        }
        return binding.root
    }
}
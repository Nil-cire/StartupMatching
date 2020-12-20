package com.eric.startupmatching

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("setImages")

//fun ImageView.setCircularImage(imageUrl: String?) {
//    imageUrl?.let {
//        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
//        Glide.with(this.context)
//            .load(imageUrl)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.icons_abstract)
//                    .error(R.drawable.icons_abstract))
//            .into(this)
//    }
//}

fun ImageView.setImage(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.icons_abstract)
                    .error(R.drawable.icons_abstract))
            .into(this)
    }
}

@BindingAdapter("setFollower")
fun TextView.setFollower(follower: Int?) {
    text = if (follower == null) {
        "追蹤數 : 0 人"
    } else {
        "追蹤數: $follower 人"
    }
}

@BindingAdapter("setFollowing")
fun TextView.setFollowing(following: Int?) {
    text = if (following == null) {
        "被追蹤數: 0 人"
    } else {
        "被追蹤數 $following 人"
    }
}

@BindingAdapter("setAchievementCount")
fun TextView.setAchievementCount(achievement: Int?) {
    text = if (achievement == null) {
        "成就: 0 個"
    } else {
        "成就: $achievement 個"
    }
}
package com.eric.startupmatching

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.util.*

@BindingAdapter("setImages")
fun ImageView.setImage(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.himym)
                    .error(R.drawable.himym))
            .into(this)
    }
}

@BindingAdapter("setTrophyImages")
fun ImageView.setTrophyImages(imageUrl: String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.trophy)
                    .error(R.drawable.trophy))
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

@BindingAdapter("setTimeWithFormat")
fun TextView.setTimeWithFormat(date: Date?) {
    text = if (date == null) {
        "超過5年以前"
    } else {
        "${date.year} - ${date.month} - ${date.day}"
    }
}
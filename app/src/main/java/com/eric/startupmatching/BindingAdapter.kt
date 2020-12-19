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

@SuppressLint("SetTextI18n")
fun TextView.setFollower(follower: Int?) {
    if (follower == null) {
        text = "追蹤者 0 人"
    } else {
        text = "追蹤者 $follower 人"
    }
}

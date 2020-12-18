package com.eric.startupmatching

import android.view.View
import android.widget.ImageView
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

package com.eric.startupmatching

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter

@BindingAdapter("productImages")
fun ImageView.setMultiImage(imageUrl:String?) {
    imageUrl?.let {
        val imgUri = imageUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(this.context)
            .load(imageUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder))
            .into(this)
    }
}

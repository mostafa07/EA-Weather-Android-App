package com.example.venuesnearby.ui.databinding

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.emiratesauctionsweatherapp.R
import java.io.File

@BindingAdapter("imageResource")
fun setImage(imageView: ImageView, imageUri: Uri?) {
    Glide.with(imageView.rootView.context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_large)
                .error(R.drawable.placeholder_large)
        )
        .load(imageUri)
        .into(imageView)
}

@BindingAdapter("imageResource")
fun setImage(imageView: ImageView, imageUrl: String?) {
    Glide.with(imageView.rootView.context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_large)
                .error(R.drawable.placeholder_large)
        )
        .load(imageUrl)
        .into(imageView)
}

@BindingAdapter("imageResource")
fun setImage(imageView: ImageView, imageFile: File?) {
    Glide.with(imageView.rootView.context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_large)
                .error(R.drawable.placeholder_large)
        )
        .load(imageFile)
        .into(imageView)
}

@BindingAdapter("imageResource")
fun setImage(imageView: ImageView, imageResourceId: Int) {
    Glide.with(imageView.rootView.context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_large)
                .error(R.drawable.placeholder_large)
        )
        .load(imageResourceId)
        .into(imageView)
}

@BindingAdapter("imageResource")
fun setImage(imageView: ImageView, imageDrawable: Drawable?) {
    Glide.with(imageView.rootView.context)
        .setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.placeholder_large)
                .error(R.drawable.placeholder_large)
        )
        .load(imageDrawable)
        .into(imageView)
}

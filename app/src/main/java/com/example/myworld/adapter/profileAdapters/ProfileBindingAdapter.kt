package com.example.myworld.adapter.profileAdapters

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.myworld.R
import com.example.myworld.fragment.profile.ProfileFragment

@BindingAdapter(value = ["imageUrl", "context"], requireAll = true)
fun image(view: ImageView, url: String?, context: ProfileFragment) {
    Glide
        .with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.mipmap.ic_launcher)
        .into(view);
}

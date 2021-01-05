package com.example.myworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myworld.R
import kotlinx.android.synthetic.main.gallery_recycler_row.view.*

open class GalleryImageAdapter(private var context: Context, private var image: ArrayList<String>, var photoListener: PhotoListener) : RecyclerView.Adapter<GalleryImageAdapter.ViewHolder>()
{
    var imageList = image

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return  image.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var image = image[position]

        Glide.with(context).load(image).into(holder.itemView.gallery_iamge)
        holder.itemView.setOnClickListener {
            photoListener.onPhotoClick(image)
        }

    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {

    }

    interface PhotoListener
    {
        fun onPhotoClick(path : String)
    }
}
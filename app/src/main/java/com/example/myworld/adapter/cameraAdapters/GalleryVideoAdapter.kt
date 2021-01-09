package com.example.myworld.adapter.cameraAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myworld.R
import kotlinx.android.synthetic.main.gallery_video_recycler_row.view.*

open class GalleryVideoAdapter(private var context: Context, private var video : ArrayList<String>, var photoListener: PhotoListener) : RecyclerView.Adapter<GalleryVideoAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return  video.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var video = video[position]
        Glide.with(context).load(video).into(holder.itemView.gallery_video)
        holder.itemView.video_button.visibility = View.VISIBLE
        holder.itemView.setOnClickListener {
            photoListener.onPhotoClick(video)
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
package com.example.myworld.adapter.exploreAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myworld.R
import com.example.myworld.model.SearchVideoModel

class VideoRecyclerAdapter(private val context: Context, private val allVideos: List<SearchVideoModel>) : RecyclerView.Adapter<VideoRecyclerAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.user_search_video_item, parent, false))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video=allVideos[position]

//        Glide.with(context)
//                .load(friend.imageUrl)
//                .error(R.drawable.mypage)
//                .centerCrop()
//                .into(holder.userImage)
        holder.user_name_search.text=video.videoTitle
    }

    override fun getItemCount(): Int {
        return allVideos.size
    }

    class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var user_name_search: TextView =itemView.findViewById(R.id.textViewVideoTitle)
    }

}
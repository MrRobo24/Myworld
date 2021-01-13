package com.example.myworld.adapter.homeFeedAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.VideoModel
import kotlinx.android.synthetic.main.video_suggestion_home_fragment_recycler_item.view.*

class FeedSuggestionAdapter(private var video : ArrayList<VideoModel>) : RecyclerView.Adapter<FeedSuggestionAdapter.MyViewHolder>()
{
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(video : VideoModel)
        {
            itemView.video_suggestion_title.text = video.videoTitle
            itemView.video_suggestion_description.text = video.videoDesc
        }

        fun onClick(video : VideoModel)
        {
            itemView.video_suggestion_title.setOnClickListener {
                Toast.makeText(itemView.context , video.videoTitle , Toast.LENGTH_LONG).show()
            }
            itemView.video_suggestion_description.setOnClickListener {
                Toast.makeText(itemView.context , video.videoDesc , Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_suggestion_home_fragment_recycler_item , parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val video = video[position]
        holder.bind(video)
        holder.onClick(video)
    }

    override fun getItemCount(): Int
    {
        return video.size
    }
}
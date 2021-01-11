package com.example.myworld.adapter.profileAdapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.VideoModel
import kotlinx.android.synthetic.main.user_story_recycler_item.view.*

class StoryAdapter(var video : ArrayList<VideoModel>) : RecyclerView.Adapter<StoryAdapter.MyViewHolder>()
{
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        fun bind(videoModel: VideoModel)
        {
            itemView.story_video_profile_fragment.apply {
                setVideoURI(Uri.parse(videoModel.videoUrl))
                setOnPreparedListener {
                    it.setDataSource(context, Uri.parse(videoModel.videoUrl))
                    it.prepare()
                    it.start()
                }
            }
            itemView.video_description_profile_fragment.text = videoModel.videoDesc
            itemView.video_title_profile_fragment.text = videoModel.videoTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_story_recycler_item , parent , false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int
    {
        return video.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        var video = video[position]
        holder.bind(video)
    }
}
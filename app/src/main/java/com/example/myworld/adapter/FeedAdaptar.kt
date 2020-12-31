package com.example.myworld.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.VideoModel
import kotlinx.android.synthetic.main.fragment_home.view.*

class FeedAdaptar(var arrVideo : ArrayList<VideoModel>) : RecyclerView.Adapter<FeedAdaptar.MyViewHolder>()
{

    var arrVideoModel: ArrayList<VideoModel> = arrVideo


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int
    {
        return arrVideoModel.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val video = arrVideo[position]
        holder.bind(video)
        holder.itemView.user_feed_name.text = video.videoTitle
        holder.itemView.user_feed_discription.text = video.videoDesc
        holder.itemView.videoView.setVideoPath(video.videoUrl)
        holder.itemView.videoView.setVideoURI(video.videoUrl.toUri())
        holder.itemView.videoView.setOnPreparedListener {
            holder.itemView.video_progressBar.visibility = View.GONE
            it.start()

            val videoRatio : Float = it.videoWidth/it.videoHeight.toFloat()
            val screenRatio : Float = holder.itemView.videoView.width/holder.itemView.videoView.height.toFloat()
            val scale : Float = videoRatio / screenRatio
            if (scale >= 1f)
            {
                holder.itemView.videoView.scaleX = scale
            }
            else
            {
                holder.itemView.videoView.scaleY = (1f / scale)
            }
        }
        holder.itemView.videoView.setOnCompletionListener {
            it.start()
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        fun bind(arrVideoModel: VideoModel)
        {
            itemView.user_feed_name.text = arrVideoModel.videoTitle
            itemView.user_feed_discription.text = arrVideoModel.videoDesc
        }
    }
}
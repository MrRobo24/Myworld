package com.example.myworld.adapter.cameraAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.SongInfo
import kotlinx.android.synthetic.main.music_recycler_row.view.*

class SongAdapter(var myListSong: ArrayList<SongInfo>, val context : Context, var musicListener: MusicListener) : RecyclerView.Adapter<SongAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.music_recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = myListSong.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val song = myListSong[position]
        holder.bind(song , context)
        holder.itemView.music_play.setOnClickListener {
            musicListener.onClick(song)
        }
        holder.itemView.imageView3.setOnClickListener {
            musicListener.onClick(song)
            //context.startService(Intent(context,AudioService(context,song)::class.java))
        }
        holder.itemView.user_name_search.setOnClickListener {
            musicListener.onSelect(song)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
        }

        fun bind(song : SongInfo, context: Context)
        {
            itemView.user_name_search.text = song.title
        }
    }

    interface MusicListener
    {
        fun onClick(song : SongInfo) : String
        {
            return song.songURL.toString()
        }
        fun onSelect(song: SongInfo) : String
        {
            return song.songURL.toString()
        }
    }
}
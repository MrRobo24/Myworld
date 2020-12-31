package com.example.myworld.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.SongInfo
import com.example.myworld.service.AudioService
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.recycler_row.view.*
import kotlin.coroutines.coroutineContext

class SongAdapter(var myListSong: ArrayList<SongInfo> , val context : Context) : RecyclerView.Adapter<SongAdapter.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = myListSong.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val song = myListSong[position]
        holder.bind(song , context)
        holder.itemView.music_play.setOnClickListener {
            context.startService(Intent(context,AudioService(context,song)::class.java))
        }
        holder.itemView.imageView3.setOnClickListener {
            context.startService(Intent(context,AudioService(context,song)::class.java))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
//            itemView.setOnClickListener {
//                val snack: String = "Item Postion clciked: $adapterPosition"
//                Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
//            }
        }

        fun bind(song : SongInfo , context: Context)
        {
            itemView.user_name_search.text = song.title
        }
    }
}
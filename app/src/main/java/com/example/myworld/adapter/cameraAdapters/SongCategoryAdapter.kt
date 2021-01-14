package com.example.myworld.adapter.cameraAdapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.SongInfo
import okhttp3.internal.notifyAll

class SongCategoryAdapter(
    var categoryList: ArrayList<Pair<Int, ArrayList<SongInfo>>>,
    val context: Context,
    var musicListener: SongAdapter.MusicListener

) : RecyclerView.Adapter<SongCategoryAdapter.SongCategoryViewHolder>() {

    var viewPool: RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

    class SongCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txtSongCategoryName: TextView = view.findViewById(R.id.txtSongCategoryName)
        var rvMusicRecyclerView: RecyclerView = view.findViewById(R.id.rvMusicRecyclerView)
        var txtViewMore: TextView = view.findViewById(R.id.txtViewMore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongCategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.song_category_row, parent, false)

        return SongCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongCategoryViewHolder, position: Int) {
        var currItem = categoryList[position]

        holder.txtSongCategoryName.text = "Category $position"
        val layoutManager = LinearLayoutManager(
            holder.rvMusicRecyclerView.context,
            LinearLayoutManager.VERTICAL,
            false
        )

        var songAdapter: SongAdapter
        if (currItem.first == 10) {
            Log.d("SongCatAdapter", "LIMIT: ${currItem.first}")
            holder.txtViewMore.text = "Show Less"
            songAdapter = SongAdapter(currItem.second, context, musicListener)
        } else {
            Log.d("SongCatAdapter", "LIMIT: ${currItem.first}")
            songAdapter =
                SongAdapter(
                    currItem.second.slice(0 until currItem.first) as ArrayList<SongInfo>,
                    context,
                    musicListener
                )
            holder.txtViewMore.text = "View More"
        }

        layoutManager.initialPrefetchItemCount = currItem.first


        holder.rvMusicRecyclerView.layoutManager = layoutManager
        holder.rvMusicRecyclerView.adapter = songAdapter
        holder.rvMusicRecyclerView.setRecycledViewPool(viewPool)

        songAdapter.itemLimit = currItem.first

        holder.txtViewMore.setOnClickListener {


            synchronized(songAdapter) {

                if (currItem.first == 5) {
                    currItem = currItem.copy(first = (currItem.first * 2) % 15)
                    Log.d("SongCatAdapter", "LIMIT: ${currItem.first}")
                    //songAdapter.notifyItemRangeChanged(0, currItem.first)
                    songAdapter.myListSong = currItem.second
                    songAdapter.itemLimit = currItem.first
                    songAdapter.notifyDataSetChanged()
                    holder.txtViewMore.text = "Show Less"

                } else {
                    currItem = currItem.copy(first = (currItem.first * 2) % 15)
                    Log.d("SongCatAdapter", "LIMIT: ${currItem.first}")
                    songAdapter.myListSong = currItem.second.slice(0..5) as ArrayList<SongInfo>
                    songAdapter.itemLimit = currItem.first
                    songAdapter.notifyDataSetChanged()
                    holder.txtViewMore.text = "View More"

                }

                categoryList[position] = currItem

            }


        }


    }

    override fun getItemCount(): Int {

        return categoryList.size
    }

}
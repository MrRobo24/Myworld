package com.example.myworld.adapter.exploreAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myworld.R
import com.example.myworld.model.SearchFriendsModel

class FriendsRecyclerViewAdapter(private val context: Context, private val allFriends: List<SearchFriendsModel>) : RecyclerView.Adapter<FriendsRecyclerViewAdapter.FriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        return FriendsViewHolder(LayoutInflater.from(context).inflate(R.layout.user_search_friends_item, parent, false))
    }

    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val friend=allFriends[position]

        Glide.with(context)
                .load(friend.imageUrl)
                .error(R.drawable.mypage)
                .centerCrop()
                .into(holder.userImage)
        holder.user_name_search.text=friend.name
        holder.user_channel_search.text=friend.channel
    }

    override fun getItemCount(): Int {
        return allFriends.size
    }

    class FriendsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var userImage: ImageView =itemView.findViewById(R.id.userImage)
        var user_name_search: TextView =itemView.findViewById(R.id.user_name_search)
        var user_channel_search: TextView =itemView.findViewById(R.id.user_channel_search)
    }

}
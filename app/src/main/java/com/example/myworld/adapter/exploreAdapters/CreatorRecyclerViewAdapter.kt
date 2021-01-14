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
import com.example.myworld.model.SearchCreatorModel
import de.hdodenhof.circleimageview.CircleImageView

class CreatorRecyclerViewAdapter(private val context: Context, private val allCreators: List<SearchCreatorModel>) : RecyclerView.Adapter<CreatorRecyclerViewAdapter.CreatorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreatorViewHolder {
        return CreatorViewHolder(LayoutInflater.from(context).inflate(R.layout.user_search_creator_item, parent, false))
    }

    override fun onBindViewHolder(holder: CreatorViewHolder, position: Int) {
        val creator=allCreators[position]

        Glide.with(context)
                .load(creator.imageUrl)
                .error(R.drawable.mypage)
                .centerCrop()
                .into(holder.userImage)
        holder.user_name_search.text=creator.name
        holder.user_channel_search.text=creator.channel
    }

    override fun getItemCount(): Int {
        return allCreators.size
    }

    class CreatorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var userImage:CircleImageView=itemView.findViewById(R.id.userImage)
        var user_name_search:TextView=itemView.findViewById(R.id.user_name_search)
        var user_channel_search:TextView=itemView.findViewById(R.id.user_channel_search)
    }

}
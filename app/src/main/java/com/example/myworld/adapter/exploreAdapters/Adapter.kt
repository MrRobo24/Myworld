package com.example.myworld.adapter.exploreAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import java.util.*

class Adapter(val item: ArrayList<String>): RecyclerView.Adapter<Adapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val listImage: ImageView = itemView.findViewById(R.id.list_image)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.listImage.setImageResource(R.drawable.mypage)
    }

    override fun getItemCount(): Int {
        return item.size
    }

}
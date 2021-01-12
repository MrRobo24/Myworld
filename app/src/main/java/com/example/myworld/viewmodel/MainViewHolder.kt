package com.example.myworld.viewmodel

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.adapter.exploreAdapters.CategoryItemAdapter

class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
{
    var categoryTitle: TextView = itemView.findViewById(R.id.textViewTitle)
    var itemRecycler: RecyclerView = itemView.findViewById(R.id.recyclerViewChild)

    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var childRecyclerAdapter: CategoryItemAdapter

}
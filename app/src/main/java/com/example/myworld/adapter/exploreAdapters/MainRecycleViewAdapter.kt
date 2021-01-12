package com.example.myworld.adapter.exploreAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.AllCategory
import com.example.myworld.model.CategoryItem
import com.example.myworld.viewmodel.MainViewHolder

class MainRecycleViewAdapter(private val context: Context, private val allCategory: List<AllCategory>) : RecyclerView.Adapter<MainViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int)
    {
        holder.categoryTitle.text = allCategory[position].categoryTitle
        setCatItemRecycler(holder.itemRecycler, allCategory[position].categoryItem, position)

        if(position==0)
            holder.categoryTitle.visibility=View.GONE
    }

    override fun getItemCount(): Int
    {
        return allCategory.size
    }

    private fun setCatItemRecycler(recyclerView: RecyclerView, categoryItem: List<CategoryItem>, position: Int)
    {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = CategoryItemAdapter(context,categoryItem,position)
        }
    }



}
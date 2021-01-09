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

class MainRecycleViewAdapter(private val context: Context, private val allCategory: List<AllCategory>) : RecyclerView.Adapter<MainRecycleViewAdapter.MainViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder
    {
        val view = LayoutInflater.from(context).inflate(R.layout.view_item, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int)
    {
        holder.categoryTitle.text = allCategory[position].categoryTitle
        setCatItemRecycler(holder.itemRecycler, allCategory[position].categoryItem)
    }

    override fun getItemCount(): Int
    {
        return allCategory.size
    }

    class MainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var categoryTitle: TextView = itemView.findViewById(R.id.cat_table)
        var itemRecycler: RecyclerView = itemView.findViewById(R.id.cat_item_recycler)

    }


    private fun setCatItemRecycler(recyclerView: RecyclerView, categoryItem: List<CategoryItem>)
    {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context , RecyclerView.HORIZONTAL , false)
            adapter = CategoryItemAdapter(context , categoryItem)
        }
    }



}
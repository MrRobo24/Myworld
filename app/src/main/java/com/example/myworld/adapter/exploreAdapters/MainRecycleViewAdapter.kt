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

class MainRecycleViewAdapter(private val context: Context, private val allCategory: List<AllCategory>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun getItemViewType(position: Int): Int {
        return if (position==0) {
            0
        } else {
            1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            val view= LayoutInflater.from(context).inflate(R.layout.header_view_item, parent, false)
            ViewHolder1(view)
        } else {
            val view= LayoutInflater.from(context).inflate(R.layout.view_item, parent, false)
            ViewHolder2(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder1){
            setCatItemRecycler(holder.itemRecycler, allCategory[position].categoryItem, position)
        }else if(holder is ViewHolder2){
            holder.categoryTitle.text = allCategory[position].categoryTitle
            setCatItemRecycler(holder.itemRecycler, allCategory[position].categoryItem, position)
        }
    }

    override fun getItemCount(): Int {
        return allCategory.size
    }

    private fun setCatItemRecycler(recyclerView: RecyclerView, categoryItem: List<CategoryItem>, position: Int)
    {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter = CategoryItemAdapter(context,categoryItem,position)
        }
    }



    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemRecycler: RecyclerView = itemView.findViewById(R.id.recyclerViewChild)
    }

    class ViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        var itemRecycler: RecyclerView = itemView.findViewById(R.id.recyclerViewChild)
    }

}
package com.example.myworld.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.CategoryItem

class CategoryItemAdapter(private val context: Context, private val categoryItem:List<CategoryItem>) : RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>()
{

    class CategoryItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var textView: TextView = itemView.findViewById(R.id.text_View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder
    {
        return CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_item, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int)
    {
        holder.textView.text = categoryItem[position].categoryTitle
    }

    override fun getItemCount(): Int {
        return categoryItem.size
    }
}
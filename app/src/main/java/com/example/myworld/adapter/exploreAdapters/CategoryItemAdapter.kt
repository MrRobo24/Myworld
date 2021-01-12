package com.example.myworld.adapter.exploreAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.CategoryItem

class CategoryItemAdapter(private val context: Context, private val categoryItem:List<CategoryItem>, private val pos: Int) : RecyclerView.Adapter<CategoryItemAdapter.CategoryItemViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemViewHolder
    {
//        return CategoryItemViewHolder(LayoutInflater.from(context).inflate(R.layout.category_row_item, parent, false))
        return when (pos) {
            0 -> {
                val view= LayoutInflater.from(context).inflate(R.layout.child_item_3, parent, false)
                CategoryItemViewHolder(view)
            }
            1 -> {
                val view= LayoutInflater.from(context).inflate(R.layout.child_item_1, parent, false)
                CategoryItemViewHolder(view)
            }
            else -> {
                val view= LayoutInflater.from(context).inflate(R.layout.child_item_2, parent, false)
                CategoryItemViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: CategoryItemViewHolder, position: Int)
    {
        holder.textView.text = categoryItem[position].categoryTitle
    }

    override fun getItemCount(): Int {
        return categoryItem.size
    }

    class CategoryItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var textView: TextView = itemView.findViewById(R.id.textView)
    }
}
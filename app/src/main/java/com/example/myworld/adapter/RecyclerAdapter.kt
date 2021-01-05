package com.example.myworld.adapter

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.music_recycler_row.view.*

class RecyclerAdapter(private var movieList: ArrayList<com.example.myworld.Movie>) :
    RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.music_recycler_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = movieList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                val snack: String = "Item Position clicked: $adapterPosition"
                Snackbar.make(itemView, snack, Snackbar.LENGTH_SHORT).show()
            }
        }

        fun bind(movie: com.example.myworld.Movie)
        {
//            itemView.movieNameTextView.text = movie.name
//            itemView.ratingTextView.text = movie.rating.toString()
        }
    }
}
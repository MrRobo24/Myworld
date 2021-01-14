package com.example.myworld.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.adapter.exploreAdapters.FriendsRecyclerViewAdapter
import com.example.myworld.adapter.exploreAdapters.VideoRecyclerAdapter
import com.example.myworld.model.SearchFriendsModel
import com.example.myworld.model.SearchVideoModel
import kotlinx.android.synthetic.main.fragment_user_search_friends.*
import kotlinx.android.synthetic.main.fragment_user_search_video.*

class user_search_video : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_search_video, container, false)
    }

    override fun onStart() {
        val allVideos: MutableList<SearchVideoModel> = ArrayList()

        allVideos.add(SearchVideoModel("This is for the title of the searched video"))

        setVideosRecycler(allVideos)

        super.onStart()
    }

    private fun setVideosRecycler(allVideos: List<SearchVideoModel>)
    {
        recyclerViewVideos.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter= VideoRecyclerAdapter(context,allVideos)
        }
    }
}
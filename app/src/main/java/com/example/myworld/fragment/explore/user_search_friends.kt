package com.example.myworld.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.adapter.exploreAdapters.CreatorRecyclerViewAdapter
import com.example.myworld.adapter.exploreAdapters.FriendsRecyclerViewAdapter
import com.example.myworld.model.SearchCreatorModel
import com.example.myworld.model.SearchFriendsModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_user_search_friends.*

class user_search_friends : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_search_friends, container, false)
    }

    override fun onStart() {
        val allFriends: MutableList<SearchFriendsModel> = ArrayList()
        allFriends.add(SearchFriendsModel("","A","Channel A"))
        allFriends.add(SearchFriendsModel("","B","Channel B"))
        allFriends.add(SearchFriendsModel("","C","Channel C"))
        allFriends.add(SearchFriendsModel("","D","Channel D"))
        allFriends.add(SearchFriendsModel("","E","Channel E"))
        allFriends.add(SearchFriendsModel("","F","Channel F"))
        allFriends.add(SearchFriendsModel("","G","Channel G"))
        allFriends.add(SearchFriendsModel("","H","Channel H"))
        allFriends.add(SearchFriendsModel("","I","Channel I"))

        setFriendsRecycler(allFriends)

        super.onStart()
    }

    private fun setFriendsRecycler(allFriends: List<SearchFriendsModel>)
    {
        recyclerViewFriends.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter= FriendsRecyclerViewAdapter(context,allFriends)
        }
    }

}
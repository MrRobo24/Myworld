package com.example.myworld.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.adapter.exploreAdapters.CreatorRecyclerViewAdapter
import com.example.myworld.adapter.exploreAdapters.MainRecycleViewAdapter
import com.example.myworld.model.AllCategory
import com.example.myworld.model.SearchCreatorModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_user_search_creato.*

class user_search_creator : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_search_creato, container, false)
    }

    override fun onStart() {
        val allCreators: MutableList<SearchCreatorModel> = ArrayList()
        allCreators.add(SearchCreatorModel("","A","Channel A"))
        allCreators.add(SearchCreatorModel("","B","Channel B"))
        allCreators.add(SearchCreatorModel("","C","Channel C"))
        allCreators.add(SearchCreatorModel("","D","Channel D"))
        allCreators.add(SearchCreatorModel("","E","Channel E"))
        allCreators.add(SearchCreatorModel("","F","Channel F"))
        allCreators.add(SearchCreatorModel("","G","Channel G"))
        allCreators.add(SearchCreatorModel("","H","Channel H"))
        allCreators.add(SearchCreatorModel("","I","Channel I"))

        setCreatorRecycler(allCreators)

        super.onStart()
    }

    private fun setCreatorRecycler(allCreators: List<SearchCreatorModel>)
    {
        recyclerViewCreator.apply {
            layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            adapter= CreatorRecyclerViewAdapter(context,allCreators)
        }
    }
}
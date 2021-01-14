package com.example.myworld.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.myworld.R
import com.example.myworld.adapter.exploreAdapters.SearchTabviewAdopter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import kotlin.collections.ArrayList


class ExploreSearchFragment : Fragment() {

    lateinit var viewPager: ViewPager2
    lateinit var tabLayout: TabLayout

    private val titles: ArrayList<String> = arrayListOf("Video", "Friends", "Creator")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_explore_search, container, false)

        viewPager = view.findViewById(R.id.viewpager)

        viewPager.currentItem = 0

        tabLayout = view.findViewById(R.id.tabLayout)
        init()

        return view
    }

    private fun init() {
        // removing toolbar elevation
        viewPager.adapter = SearchTabviewAdopter(titles, childFragmentManager, lifecycle)

        // attaching tab mediator
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = titles[position]
        }.attach()
    }
}
package com.example.myworld.adapter.profileAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myworld.fragment.profile.user_search_creator
import com.example.myworld.fragment.profile.user_search_friends
import com.example.myworld.fragment.profile.user_search_video


class SearchTabviewAdopter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                user_search_video()
            }
            1->{
                user_search_friends()
            }
            2->{
                user_search_creator()
            }
            else->{
                user_search_video()
            }
        }
    }
}
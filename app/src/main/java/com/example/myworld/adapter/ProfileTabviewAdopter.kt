package com.example.myworld.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myworld.fragment.user_profile_more
import com.example.myworld.fragment.user_profile_story
import com.example.myworld.fragment.user_profile_video

class ProfileTabviewAdopter(fragmentManager: FragmentManager, lifecycle: Lifecycle):
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                user_profile_story()
            }
            1->{
                user_profile_video()
            }
            2->{
                user_profile_more()
            }
            else->{
                user_profile_story()
            }
        }
    }
}
package com.example.myworld.adapter.profileAdapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myworld.fragment.profile.UserProfileMore
import com.example.myworld.fragment.profile.UserProfileStory
import com.example.myworld.fragment.profile.UserProfileVideo

class ProfileTabViewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int
    {
        return 3
    }

    override fun createFragment(position: Int): Fragment
    {
        return when(position)
        {
            0->
            {
                UserProfileStory()
            }
            1->
            {
                UserProfileVideo()
            }
            2->
            {
                UserProfileMore()
            }
            else->
            {
                UserProfileStory()
            }
        }
    }
}
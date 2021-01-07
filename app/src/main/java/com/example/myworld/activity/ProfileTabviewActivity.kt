package com.example.myworld.activity

import com.example.myworld.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myworld.adapter.ProfileTabviewAdopter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileTabviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        val viewpagerprofile=findViewById<ViewPager2>(R.id.view_pager_2)
        val tablayout=findViewById<TabLayout>(R.id.profileTab)

        viewpagerprofile.adapter= ProfileTabviewAdopter(this.supportFragmentManager,lifecycle)
        TabLayoutMediator(tablayout,viewpagerprofile){ tab, position->

            when(position){
                0->{
                    tab.text="Story"
                }
                1->{
                    tab.text="Video"
                }
                2->{
                    tab.text="More"
                }

            }
        }.attach()
    }


}
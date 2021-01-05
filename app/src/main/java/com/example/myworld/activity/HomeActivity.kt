package com.example.myworld.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.adapter.FeedAdaptar
import com.example.myworld.R
import com.example.myworld.fragment.*
import com.example.myworld.model.VideoModel
import kotlinx.android.synthetic.main.activity_home.*
import java.util.ArrayList

class HomeActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val homeFragment = HomeFragment()
        val exploreFragment = SearchFragment()
        val profileFragment = ProfileFragment()
        val cameraFragment = CameraFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.user_home -> makeCurrentFragment(homeFragment)
                R.id.user_explore -> makeCurrentFragment(exploreFragment)
                R.id.user_profile -> makeCurrentFragment(profileFragment)
                R.id.user_camera -> makeCurrentFragment(cameraFragment)
            }
            true
        }

    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.container , fragment).commit()
    }
}
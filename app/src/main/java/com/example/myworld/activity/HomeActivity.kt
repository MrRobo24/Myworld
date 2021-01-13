package com.example.myworld.activity

import android.R.attr.tag
import android.R.id
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myworld.R
import com.example.myworld.fragment.*
import com.example.myworld.fragment.camera.CameraFragment
import com.example.myworld.fragment.camera.VideoUploadFragment
import com.example.myworld.fragment.explore.SearchFragment
import com.example.myworld.fragment.home.HomeFragment
import com.example.myworld.fragment.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), CameraFragment.SendMessage
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Myworld)
        setContentView(R.layout.activity_home)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val homeFragment = HomeFragment()
        val exploreFragment = SearchFragment()
        val profileFragment = ProfileFragment()
        val cameraFragment = CameraFragment()

        makeCurrentFragment(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.user_home -> {
                    bottom_navigation.visibility = View.VISIBLE
                    makeCurrentFragment(homeFragment)
                }
                R.id.user_explore -> {
                    bottom_navigation.visibility = View.VISIBLE
                    makeCurrentFragment(exploreFragment)
                }
                R.id.user_profile -> {
                    bottom_navigation.visibility = View.VISIBLE
                    makeCurrentFragment(profileFragment)
                }
                R.id.user_camera -> {
                    bottom_navigation.visibility = View.GONE
                    makeCurrentFragment(cameraFragment)
                }
            }
            true
        }

    }

    override fun onResume()
    {
        bottom_navigation.visibility = View.VISIBLE
        bottom_navigation.isEnabled = true
        super.onResume()
    }

    override fun onBackPressed()
    {
        finish()
        super.onBackPressed()
    }

    override fun onRestart()
    {
        bottom_navigation.visibility = View.VISIBLE
        bottom_navigation.isEnabled = true
        super.onRestart()
    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.container, fragment).commit()
    }

    //To transfer data to VideoUploadFragment
    override fun sendData(message: String?) {
        Log.i("FilePath HomeActivity",message!!)

        val bundle = Bundle()
        bundle.putString("File Path",message)

        val transaction = this.supportFragmentManager.beginTransaction()
        val frag2 = VideoUploadFragment()
        frag2.arguments = bundle

        transaction.replace(R.id.relativeLayout, frag2)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }
}

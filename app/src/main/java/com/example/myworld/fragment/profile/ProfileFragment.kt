package com.example.myworld.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.myworld.R
import com.example.myworld.adapter.profileAdapters.ProfileTabViewAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment()
{

    private lateinit var viewPagerGroup : ViewPager2
    private lateinit var tabLayout: TabLayout

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root =  inflater.inflate(R.layout.fragment_profile, container, false)

        //setting up the viewPager and the TabLayout
        viewPagerGroup = root.findViewById(R.id.view_pager_2)
        tabLayout = root.findViewById(R.id.profileTab)

        return root
    }


    override fun onStart()
    {

        //Setting up the ViewPager Adapter and The TabLayout
        viewPagerGroup.adapter= ProfileTabViewAdapter(this.childFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout,viewPagerGroup){ tab, position->

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

        //Sending user to Profile Setting Fragment
        profile_setting_button.setOnClickListener {
           val profileSettingFragment = ProfileSettingFragment()
            profileSettingFragment.show(childFragmentManager,"Bottom Sheet Dialog")
        }
        super.onStart()
    }

    private fun makeCurrentFragment(fragment: Fragment) = childFragmentManager.beginTransaction().apply {
        replace(R.id.container , fragment).commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
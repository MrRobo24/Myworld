package com.example.myworld.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.adapter.FeedAdaptar
import com.example.myworld.model.VideoModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_home.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var arrVideoModel = ArrayList<VideoModel>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
//        addDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onStart()
    {
        super.onStart()
        addDetails()
        viewpager.adapter = FeedAdaptar(arrVideoModel)
    }

    private fun addDetails()
    {
        arrVideoModel.add(
                VideoModel(
                        "Tree with flowers",
                        "The branches of a tree wave in the breeze, with pointy leaves ",
                        "https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4"
                )
        )
        arrVideoModel.add(
                VideoModel(
                        "multicolored lights",
                        "A man with a small beard and mustache wearing a white sweater, sunglasses, and a backwards black baseball cap turns his head in different directions under changing colored lights.",
                        "https://assets.mixkit.co/videos/preview/mixkit-man-under-multicolored-lights-1237-large.mp4"
                )
        )
        arrVideoModel.add(
                VideoModel(
                        "holding neon light",
                        "Bald man with a short beard wearing a large jean jacket holds a long tubular neon light thatch",
                        "https://assets.mixkit.co/videos/preview/mixkit-man-holding-neon-light-1238-large.mp4"
                )
        )
        arrVideoModel.add(
                VideoModel(
                        "Sun over hills",
                        "The sun sets or rises over hills, a body of water beneath them.",
                        "https://assets.mixkit.co/videos/preview/mixkit-sun-over-hills-1183-large.mp4"
                )
        )
    }
    companion object
    {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.example.myworld.fragment.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.example.myworld.R
import com.example.myworld.`class`.HomeFeedClass
import com.example.myworld.adapter.homeFeedAdapters.FeedAdapter
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.home_feed_recycler_item.*
import kotlin.math.abs


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() , LifecycleObserver , View.OnTouchListener
{
    private var playerPlayerState = 0

    companion object {
        private const val TAG = "HomeFragment"
        private const val MIN_DISTANCE = 100
    }

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
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart()
    {
        //initPlayer()
        //val model = ViewModelProvider(this).get(HomeFeedClass()::class.java)
        viewpager.apply {
            adapter?.itemCount?.minus(1)?.let { currentItem = it }
            adapter = FeedAdapter(context, playerPlayerState, HomeFeedClass().addDetails())
        }
        super.onStart()
    }


    override fun onStop()
    {
        super.onStop()
        releasePlayer()
    }

    /** Release Player */
    private fun releasePlayer()
    {
        if ( exoPlayer_home_fragment.player!= null  && exoPlayer_home_fragment.player.playWhenReady)
        {
            playerPlayerState = exoPlayer_home_fragment.player.playbackState
            exoPlayer_home_fragment.player.playbackState
            exoPlayer_home_fragment.player.release()
            exoPlayer_home_fragment.player = null
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume()
    {
        //val model = ViewModelProvider(this).get(HomeFeedClass()::class.java)
        viewpager.adapter = context?.let { FeedAdapter(it , playerPlayerState, HomeFeedClass().addDetails()) }
        super.onResume()
    }

    override fun onPause()
    {
        releasePlayer()
        super.onPause()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }
}
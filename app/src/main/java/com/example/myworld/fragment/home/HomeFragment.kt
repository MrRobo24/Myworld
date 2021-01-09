package com.example.myworld.fragment.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.`class`.HomeFeedClass
import com.example.myworld.adapter.homeFeedAdapters.FeedAdapter
import com.example.myworld.fragment.home.player.MediaPlayerImplementation
import com.example.myworld.fragment.home.player.OnSwipeListener
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
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
class HomeFragment : Fragment() , LifecycleObserver , View.OnTouchListener
{

    private lateinit var playerView: PlayerView
    private lateinit var mediaPlayer: MediaPlayerImplementation
    private lateinit var gestureDetector: GestureDetector

    companion object {
        private const val TAG = "HomeFragment"
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
        val root =  inflater.inflate(R.layout.fragment_home, container, false)

        playerView = root.findViewById(R.id.exoPlayer_home_fragment)

        return root
    }

    override fun onStart()
    {
        //initPlayer()
        //val model = ViewModelProvider(this).get(HomeFeedClass()::class.java)
        viewpager.apply {
            adapter?.itemCount?.minus(1)?.let { currentItem = it }
            adapter = FeedAdapter(context , HomeFeedClass().addDetails())
        }
        super.onStart()
    }

    override fun onStop()
    {
        super.onStop()
        //releasePlayer()
    }

    private fun play()
    {

    }

    /** Initialize the ExoPlayer */
    private fun initPlayer()
    {
        mediaPlayer = MediaPlayerImplementation.getInstance(requireContext())
        playerView.player = mediaPlayer.player

        mediaPlayer.player.addListener(object : Player.EventListener{
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {
                TODO("Not yet implemented")
            }

            override fun onTracksChanged(
                trackGroups: TrackGroupArray?,
                trackSelections: TrackSelectionArray?
            ) {
                TODO("Not yet implemented")
            }

            override fun onLoadingChanged(isLoading: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                TODO("Not yet implemented")
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                TODO("Not yet implemented")
            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
                TODO("Not yet implemented")
            }

            override fun onPlayerError(error: ExoPlaybackException?) {
                TODO("Not yet implemented")
            }

            override fun onPositionDiscontinuity(reason: Int) {
                TODO("Not yet implemented")
            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
                TODO("Not yet implemented")
            }

            override fun onSeekProcessed() {
                TODO("Not yet implemented")
            }

        })

        play()
    }

    /** Release Player */
    private fun releasePlayer()
    {
        mediaPlayer.releasePlayer()
        playerView.player = null
    }

    override fun onResume()
    {
        //val model = ViewModelProvider(this).get(HomeFeedClass()::class.java)
        viewpager.adapter = context?.let { FeedAdapter(it, HomeFeedClass().addDetails()) }
        super.onResume()
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean
    {
        return gestureDetector.onTouchEvent(event)
    }

    private fun setViews(isEnabled : Boolean)
    {
        //TODO HIDE PROGRESSBAR
        if (isEnabled)
        {
            val onSwipeListener: OnSwipeListener = object : OnSwipeListener() {
                override fun onSwipe(direction: Direction?): Boolean {
                    return when (direction) {
                        Direction.up ->
                        {
//                            if (mediaPlayer.player.hasNext())
//                            {
//                                mediaPlayer.player.next()
//                            }
                            true
                        }

                        Direction.down ->
                        {
//                            if (mediaPlayer.player.hasPrevious())
//                            {
//                                mediaPlayer.player.previous()
//                            }
                            true
                        }
                        else -> super.onSwipe(direction)
                    }
                }
            }
            gestureDetector = GestureDetector(requireContext(), onSwipeListener)
            playerView.setOnTouchListener(this)
        } else {
            playerView.visibility = View.GONE
        }
    }
}
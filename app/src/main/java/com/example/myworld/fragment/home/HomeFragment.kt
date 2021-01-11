package com.example.myworld.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
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
class HomeFragment : Fragment() , LifecycleObserver , View.OnTouchListener , GestureDetector.OnGestureListener
{

    private var x1 : Float = 0.0f
    private var x2 : Float = 0.0f
    private var y1 : Float = 0.0f
    private var y2 : Float = 0.0f

    private lateinit var playerView: PlayerView
    private lateinit var mediaPlayer: MediaPlayerImplementation
    private lateinit var gestureDetector: GestureDetector

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
        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onStart()
    {
        gestureDetector =GestureDetector(context , this)
        //initPlayer()
        //val model = ViewModelProvider(this).get(HomeFeedClass()::class.java)
        viewpager.apply {
            adapter?.itemCount?.minus(1)?.let { currentItem = it }
            adapter = FeedAdapter(context , HomeFeedClass().addDetails())
        }
        viewpager.setOnClickListener {
            Toast.makeText(it.context , "Clicked", Toast.LENGTH_LONG).show()
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
        gestureDetector = object : GestureDetector(context , this)
        {
            override fun onTouchEvent(event: MotionEvent?): Boolean
            {
                gestureDetector.onTouchEvent(event)
                if (event != null)
                {
                    when (event.action)
                    {
                        //When we start to swipe
                        0 -> {
                            x1 = event.x
                            y1 = event.y
                        }
                        //When we end to swipe
                        1 -> {
                            x2 = event.x
                            y2 = event.y

                            //Getting Value for the Horizontal Swipe
                            val valueX: Float = x2 - x1

                            if (abs(valueX) > HomeFragment.MIN_DISTANCE)
                            {
                                //Detect Left to Right Swipe
                                if (x2 > x1) {
                                } else {
                                    //Detect Right to Left Swipe
                                    Log.i("Swipe", "Right to left Swipe")
                                    Toast.makeText(context,
                                        "Right To Left Swipe ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                        }
                    }
                }

                return super.onTouchEvent(event)
            }
        }
        //val model = ViewModelProvider(this).get(HomeFeedClass()::class.java)
        viewpager.adapter = context?.let { FeedAdapter(it, HomeFeedClass().addDetails()) }
        super.onResume()
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

    override fun onDown(e: MotionEvent?): Boolean {return false }

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {return false}

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {return false}

    override fun onLongPress(e: MotionEvent?) {}

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {return false}

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        if (event != null)
        {
            when (event.action)
            {
                //When we start to swipe
                0 -> {
                    x1 = event.x
                    y1 = event.y
                }
                //When we end to swipe
                1 -> {
                    x2 = event.x
                    y2 = event.y

                    //Getting Value for the Horizontal Swipe
                    val valueX: Float = x2 - x1

                    if (abs(valueX) > HomeFragment.MIN_DISTANCE)
                    {
                        //Detect Left to Right Swipe
                        if (x2 > x1) {
                        } else {
                            //Detect Right to Left Swipe
                            Log.i("Swipe", "Right to left Swipe")
                            Toast.makeText(context,
                                "Right To Left Swipe ",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
        return true
    }
}
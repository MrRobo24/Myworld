package com.example.myworld.adapter.homeFeedAdapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.fragment.home.HomeFragment
import com.example.myworld.model.VideoModel
import com.example.myworld.utilites.Constant
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashChunkSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.custom_controller_exo_player_home_fragment.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.home_feed_recycler_item.view.*
import java.net.URI
import kotlin.math.abs

class FeedAdapter(var context: Context, var arrVideo : ArrayList<VideoModel>) : RecyclerView.Adapter<FeedAdapter.MyViewHolder>() , View.OnTouchListener , GestureDetector.OnGestureListener
{
    private var x1 : Float = 0.0f
    private var x2 : Float = 0.0f
    private var y1 : Float = 0.0f
    private var y2 : Float = 0.0f

    companion object
    {
        const val MIN_DISTANCE = 50
    }

    private lateinit var gestureDetector: GestureDetector

    //late init var player : PlayerView
    lateinit var simpleExoPlayer : SimpleExoPlayer

    private var playbackPosition = 0L

    //Initialise load control
    var loadControl : LoadControl = DefaultLoadControl()

    //Initialise band width meter
    private val  bandWidthMeter by lazy {
        DefaultBandwidthMeter()
    }

    private val adaptiveTrackSelection by lazy {
        AdaptiveTrackSelection.Factory(bandWidthMeter)
    }

    //Setup Media Source
    private fun buildMediaSource(uri : Uri) : MediaSource
    {
        val dataSourceFactory = DefaultHttpDataSourceFactory("mediaPlayer" , bandWidthMeter)
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri , dataSourceFactory , dashChunkSourceFactory , null  , null)
    }

    //Prepare ExoPlayer
    private fun prepareExoPlayer(url: String)
    {
        val uri = Uri.parse(url)
        val mediaSource = buildMediaSource(uri)
        simpleExoPlayer.prepare(mediaSource)

    }

    /** Initialize ExoPlayer */
    private fun initializeExoPlayer(url: String , holder: MyViewHolder)
    {
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(context),
                DefaultTrackSelector(adaptiveTrackSelection),
                DefaultLoadControl()
        )

        prepareExoPlayer(url)
        holder.itemView.exoPlayer_home_fragment.player = simpleExoPlayer
        simpleExoPlayer.seekTo(playbackPosition)
        simpleExoPlayer.playWhenReady = true
    }

    /** Release ExoPlayer */
    private fun releaseExoPlayer()
    {
        playbackPosition = simpleExoPlayer.currentPosition
        simpleExoPlayer.release()

    }

    //Initialize track selector
    var trackSelector : TrackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWidthMeter))

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        private var x1 : Float = 0.0f
        private var x2 : Float = 0.0f
        private var y1 : Float = 0.0f
        private var y2 : Float = 0.0f


        private lateinit var gestureDetector: GestureDetector
        fun bind(arrVideoModel: VideoModel) {
            itemView.user_feed_name.text = arrVideoModel.videoTitle
            itemView.user_feed_discription.text = arrVideoModel.videoDesc

            itemView.user_feed_name.setOnClickListener {
                Toast.makeText(it.context, "Clicked", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_feed_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        gestureDetector = object : GestureDetector(holder.itemView.context , this)
        {
            override fun onTouchEvent(event : MotionEvent?): Boolean {
                gestureDetector.onTouchEvent(event)
                if (event != null)
                {
                    when (event.action)
                    {
                        //When we start to swipe
                        MotionEvent.ACTION_DOWN -> {
                            x1 = event.x
                            y1 = event.y
                        }
                        //When we end to swipe
                        MotionEvent.ACTION_UP ->
                        {
                            x2 = event.x
                            y2 = event.y

                            //Getting Value for the Horizontal Swipe
                            val valueX: Float = x2 - x1

                            if (abs(valueX) > MIN_DISTANCE)
                            {
                                //Detect Left to Right Swipe
                                if (x2 > x1) { }
                                else
                                {
                                    //Detect Right to Left Swipe
                                    Log.i("TAG_SWIPE", "Right to left Swipe")
                                    Toast.makeText(holder.itemView.context , "Right To Left Swipe ", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }
                }
                return super.onTouchEvent(event)
            }
        }

        Log.i("VIDEO_URL" , position.toString())
        val video = arrVideo[position]
        holder.bind(video)

        holder.itemView.user_comment.setOnClickListener {
            Toast.makeText(context , "Clicked" , Toast.LENGTH_LONG).show()
        }
        holder.itemView.home_feed_item.setOnClickListener {
            if (Constant.exoPlayerIsPlaying)
            {
                holder.itemView.exoplayer_play_button_home_fragment.visibility = View.VISIBLE
                Constant.exoPlayerIsPlaying = false
            }
            else if (!Constant.exoPlayerIsPlaying)
            {
                holder.itemView.exoplayer_play_button_home_fragment.visibility = View.GONE
                Constant.exoPlayerIsPlaying = true
                simpleExoPlayer.playWhenReady = true
            }
            //Toast.makeText(it.context , "Clicked" , Toast.LENGTH_LONG).show()
        }



        //initializeExoPlayer(video.videoUrl , holder)
        Log.i("VIDEO_URL" , video.videoUrl)

        initializePlayer(context , video , holder)

//        simpleExoPlayer.addListener(object : Player.EventListener {
//            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int)
//            {
//
//            }
//
//            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?)
//            {
////                simpleExoPlayer.playWhenReady = false
//            }
//
//            override fun onLoadingChanged(isLoading: Boolean)
//            {
//
//            }
//
//            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int)
//            {
//                //Check Condition
//                if (playbackState == Player.STATE_BUFFERING)
//                {
//                    //When Buffering
//                    //Show Progress Bar
//                    holder.itemView.video_progressBar.visibility = View.VISIBLE
//                }
//                else if (playbackState == Player.STATE_READY)
//                {
//                    //When Ready
//                    //Hide Progress bar
//                    holder.itemView.video_progressBar.visibility =View.GONE
//                }
//            }
//
//            override fun onRepeatModeChanged(repeatMode: Int)
//            {
//
//            }
//
//            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean)
//            {
//
//            }
//
//            override fun onPlayerError(error: ExoPlaybackException?)
//            {
//
//            }
//
//            override fun onPositionDiscontinuity(reason: Int)
//            {
//
//            }
//
//            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?)
//            {
//
//            }
//
//            override fun onSeekProcessed()
//            {
//
//            }
//
//        })

    }

    override fun getItemCount(): Int {
        return arrVideo.size
    }



    private fun initializePlayer(context: Context , video : VideoModel , holder : MyViewHolder)
    {
        //Initial Simple ExoPlayer
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(holder.itemView.exoPlayer_home_fragment.context , trackSelector , loadControl)

        //Initialize data source factory
        val factory : DefaultHttpDataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")

        //Initialize extractors factory
        val extractorsFactory : ExtractorsFactory = DefaultExtractorsFactory()

        //Initialize media source
        val mediaSource : MediaSource = ExtractorMediaSource(video.videoUrl.toUri() , factory , extractorsFactory , null , null)

        //Set Player
        holder.itemView.exoPlayer_home_fragment.player = simpleExoPlayer

        //Keep screen on
        holder.itemView.exoPlayer_home_fragment.keepScreenOn = true

        //Prepare media
        simpleExoPlayer.prepare(mediaSource)

        //Play media when Player is ready
        simpleExoPlayer.playWhenReady = true

        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int)
            {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?)
            {
//                simpleExoPlayer.playWhenReady = false
            }

            override fun onLoadingChanged(isLoading: Boolean)
            {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int)
            {
                //Check Condition
                if (playbackState == Player.STATE_BUFFERING)
                {
                    //When Buffering
                    //Show Progress Bar
                    holder.itemView.video_progressBar.visibility = View.VISIBLE
                }
                else if (playbackState == Player.STATE_READY)
                {
                    //When Ready
                    //Hide Progress bar
                    holder.itemView.video_progressBar.visibility =View.GONE
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int)
            {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean)
            {

            }

            override fun onPlayerError(error: ExoPlaybackException?)
            {

            }

            override fun onPositionDiscontinuity(reason: Int)
            {

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?)
            {

            }

            override fun onSeekProcessed()
            {

            }

        })
    }

    override fun onDown(e: MotionEvent?): Boolean {return false}

    override fun onShowPress(e: MotionEvent?) {}

    override fun onSingleTapUp(e: MotionEvent?): Boolean {return false}

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {return false}

    override fun onLongPress(e: MotionEvent?) {}

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {return false}


//        override fun onTouch(v: View?, event: MotionEvent?): Boolean
//        {
//
//            if (event != null) {
//                when (event.action)
//                {
//                    //When we start to swipe
//                    0 -> {
//                        x1 = event.x
//                        y1 = event.y
//                    }
//                    //When we end to swipe
//                    1 -> {
//                        x2 = event.x
//                        y2 = event.y
//
//                        //Getting Value for the Horizontal Swipe
//                        val valueX: Float = x2 - x1
//
//                        if (abs(valueX) > FeedAdapter.MIN_DISTANCE)
//                        {
//                            //Detect Left to Right Swipe
//                            if (x2 > x1) { }
//                            else
//                            {
//                                //Detect Right to Left Swipe
//                                Log.i("Swipe", "Right to left Swipe")
//                                Toast.makeText(context, "Right To Left Swipe ", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//
//                    }
//                }
//            }
//
//            return true
//        }
}
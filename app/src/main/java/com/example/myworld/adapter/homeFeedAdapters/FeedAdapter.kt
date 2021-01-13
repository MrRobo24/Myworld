package com.example.myworld.adapter.homeFeedAdapters

import android.content.Context
import android.media.session.PlaybackState
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.VideoModel
import com.example.myworld.utilites.Constant
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.custom_controller_exo_player_home_fragment.view.*
import kotlinx.android.synthetic.main.home_feed_recycler_item.view.*
import java.time.chrono.MinguoChronology
import kotlin.math.abs

@RequiresApi(Build.VERSION_CODES.M)
class FeedAdapter(var context: Context,var playbackState: Int,  var arrVideo : ArrayList<VideoModel>) : RecyclerView.Adapter<FeedAdapter.MyViewHolder>(),View.OnTouchListener , GestureDetector.OnGestureListener {

    //Variables the touch detection
    private var x1: Float = 0.0f
    private var x2: Float = 0.0f
    private var y1: Float = 0.0f
    private var y2: Float = 0.0f

    private var position = 0
    private var videoUrl : String = arrVideo[position].videoUrl
    private lateinit var holder : MyViewHolder

    companion object {
        const val MIN_DISTANCE_HORIZONTAL_SWIPE = 1
        const val MIN_DISTANCE_VERTICAL_SWIPE = 0.01
    }

    private lateinit var gestureDetector : GestureDetector

    //late init var player : PlayerView
    lateinit var simpleExoPlayer: SimpleExoPlayer

    private var playbackPosition = 0L

    //Initialise load control
    var loadControl: LoadControl = DefaultLoadControl()

    //Initialise band width meter
    private val bandWidthMeter by lazy {
        DefaultBandwidthMeter()
    }

    private val adaptiveTrackSelection by lazy {
        AdaptiveTrackSelection.Factory(bandWidthMeter)
    }

    //Setup Media Source
    private fun buildMediaSource(uri: Uri): MediaSource {
        val dataSourceFactory = DefaultHttpDataSourceFactory("mediaPlayer", bandWidthMeter)
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        return DashMediaSource(uri, dataSourceFactory, dashChunkSourceFactory, null, null)
    }

    //Prepare ExoPlayer
    private fun prepareExoPlayer(url: String) {
        val uri = Uri.parse(url)
        val mediaSource = buildMediaSource(uri)
        simpleExoPlayer.prepare(mediaSource)

    }

    /** Initialize ExoPlayer */
    private fun initializeExoPlayer(url: String, holder: MyViewHolder) {
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
    private fun releaseExoPlayer() {
        playbackPosition = simpleExoPlayer.currentPosition
        simpleExoPlayer.release()

    }

    //Initialize track selector
    private var trackSelector: TrackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWidthMeter))

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var x1: Float = 0.0f
        private var x2: Float = 0.0f
        private var y1: Float = 0.0f
        private var y2: Float = 0.0f


        private lateinit var gestureDetector: GestureDetector
        fun bind(arrVideoModel: VideoModel) {
            itemView.user_feed_name.text = arrVideoModel.videoTitle
            itemView.user_feed_discription.text = arrVideoModel.videoDesc

            itemView.user_feed_name.setOnClickListener {
                Toast.makeText(it.context, "Clicked", Toast.LENGTH_LONG).show()
            }
        }

        fun onClick(video: VideoModel, videoList: ArrayList<VideoModel>) {
            itemView.home_feed_item.setOnClickListener {
                Toast.makeText(it.context, "EXO PLAYER CLICKED", Toast.LENGTH_LONG).show()
            }

            //Setting Up Default Story View
            itemView.default_expand_story_view.setOnClickListener {
                itemView.expand_story_view.visibility = View.VISIBLE
                itemView.story_view.visibility = View.VISIBLE
                itemView.default_expand_story_view.visibility = View.GONE
                itemView.default_story_view.visibility = View.GONE
                itemView.story_view.apply {
                    layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                    adapter = StorySuggestionAdapter(videoList)
                }
            }

            //Setting Up the Story View
            itemView.story_suggestion_view_button.setOnClickListener {
                itemView.story_suggestion_view_button.visibility = View.GONE
                itemView.expand_story_view.visibility = View.VISIBLE
                itemView.story_view.visibility = View.VISIBLE
                itemView.default_expand_story_view.visibility = View.GONE
                itemView.default_story_view.visibility = View.GONE
                itemView.story_view.apply {
                    layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
                    adapter = StorySuggestionAdapter(videoList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        gestureDetector = GestureDetector(context , this)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_feed_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("Position_bind" , position.toString())
        Log.i("VIDEO_URL", position.toString())

        this.position = position

        this.holder = holder

        val video = arrVideo[position]

        holder.itemView.home_feed_item.setOnTouchListener(this)
        /** Binding the data of the video with the UI. */
        holder.bind(video)

        /** Actions according to the clickListener. */
        holder.onClick(video, arrVideo)

        /** Checking the position of the video.
         * If the position is [1] then , the nested suggestion recycler view will be visible.
         * Else , the circular suggestion will be visible
         */
        suggestionVisible(holder, position)


        //TODO implement video play and pause onClick
        holder.itemView.home_feed_item.setOnClickListener {
            if (Constant.exoPlayerIsPlaying) {
                Constant.exoPlayerIsPlaying = false
                holder.itemView.exoplayer_play_button_home_fragment.visibility = View.VISIBLE
                Constant.exoPlayerIsPlaying = false
            } else if (!Constant.exoPlayerIsPlaying) {
                holder.itemView.exoplayer_play_button_home_fragment.visibility = View.GONE
                Constant.exoPlayerIsPlaying = true
                simpleExoPlayer.playWhenReady = true
            }
            //Toast.makeText(it.context , "Clicked" , Toast.LENGTH_LONG).show()
        }


        //initializeExoPlayer(video.videoUrl , holder)
        Log.i("VIDEO_URL", video.videoUrl)

        initializePlayer(context,video , holder)
    }

    /** Checking the position of the video.
     * If the position is [1] then , the nested suggestion recycler view will be visible.
     * Else , the circular suggestion will be visible
     */
    private fun suggestionVisible(holder: MyViewHolder, position: Int) {
        //If the position is equal to 1st video then the nested suggestion recycler view will be visible.
        if (position == 0) {
            holder.itemView.default_expand_story_view.visibility = View.VISIBLE
            holder.itemView.default_story_view.visibility = View.VISIBLE
            holder.itemView.expand_story_view.visibility = View.GONE
            holder.itemView.story_view.visibility = View.GONE
            holder.itemView.default_story_view.apply {
                layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
                adapter = DefaultStorySuggestionAdapter(arrVideo)
            }
        }
        else
        {
            holder.itemView.default_expand_story_view.visibility = View.GONE
            holder.itemView.home_fragment_video_suggestion_view.visibility = View.GONE
            holder.itemView.default_story_view.visibility = View.GONE
            holder.itemView.story_suggestion_view_button.visibility = View.VISIBLE
        }
    }

    private fun resetSuggestionVisible(holder: MyViewHolder, position: Int) {
        //If the position is equal to 1st video then the nested suggestion recycler view will be visible.
        if (position == 0) {
            holder.itemView.default_expand_story_view.visibility = View.VISIBLE
            holder.itemView.default_story_view.visibility = View.VISIBLE
            holder.itemView.expand_story_view.visibility = View.GONE
            holder.itemView.story_view.visibility = View.GONE
            holder.itemView.default_story_view.apply {
                layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
                adapter = DefaultStorySuggestionAdapter(arrVideo)
            }
        }
        else
        {
            holder.itemView.default_expand_story_view.visibility = View.GONE
            holder.itemView.home_fragment_video_suggestion_view.visibility = View.GONE
            holder.itemView.default_story_view.visibility = View.GONE
            holder.itemView.story_suggestion_view_button.visibility = View.VISIBLE
        }
    }



    /** Returns the size of the Video to the recycler view . */
    override fun getItemCount(): Int {
        return arrVideo.size
    }

    private fun initializePlayer(context: Context, video: VideoModel, holder: MyViewHolder) {
        //Initial Simple ExoPlayer
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(holder.itemView.exoPlayer_home_fragment.context, trackSelector, loadControl)

        //Initialize data source factory
        val factory = DefaultHttpDataSourceFactory("exoplayer_video")

        //Initialize extractors factory
        val extractorsFactory: ExtractorsFactory = DefaultExtractorsFactory()

        //Initialize media source
        val mediaSource: MediaSource = ExtractorMediaSource(Uri.parse(video.videoUrl), factory, extractorsFactory, null, null)

        //Set Player
        holder.itemView.exoPlayer_home_fragment.player = simpleExoPlayer

        //Keep screen on
        holder.itemView.exoPlayer_home_fragment.keepScreenOn = true

        //Prepare media
        simpleExoPlayer.prepare(mediaSource)

        simpleExoPlayer.repeatMode = SimpleExoPlayer.REPEAT_MODE_ONE

        //Play media when Player is ready
        simpleExoPlayer.playWhenReady = false

        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int) {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
//                simpleExoPlayer.playWhenReady = false
            }

            override fun onLoadingChanged(isLoading: Boolean) {

            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                //Check Condition
                if (playbackState == Player.STATE_BUFFERING) {
                    //When Buffering
                    //Show Progress Bar
                    holder.itemView.video_progressBar.visibility = View.VISIBLE
                } else if (playbackState == Player.STATE_READY) {
                    //When Ready
                    //Hide Progress bar
                    holder.itemView.video_progressBar.visibility = View.GONE
                }
            }

            override fun onRepeatModeChanged(repeatMode: Int) {

            }

            override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {

            }

            override fun onPlayerError(error: ExoPlaybackException?) {

            }

            override fun onPositionDiscontinuity(reason: Int) {

            }

            override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {

            }

            override fun onSeekProcessed() {

            }

        })
    }


    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        //Toast.makeText(context, "onShowPress", Toast.LENGTH_SHORT).show()
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        //Toast.makeText(context, "onSingleTapUP", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean
    {

        Log.i("Position" , position.toString())
        //Reset The Suggestions
        resetSuggestionVisible(holder , position)

        if (e1 != null)
        {
            x1 = e1.x
            y1 = e1.y
        }
        if (e2 != null) {
            x2=e2.x
            y2=e2.y
        }
        if (abs(y2-y1) > MIN_DISTANCE_VERTICAL_SWIPE)
        {
            //Detect Scroll from Down to Up
            if (y2 > y1)
            {
                Log.i("Swipe", "Up to down")
            }
            //Detect Scroll from Up to Down
            else
            {
                Log.i("Swipe", "Down to Up Swipe")
                //Toast.makeText(context , "Down to up" , Toast.LENGTH_SHORT).show()
            }
        }

        //Toast.makeText(context, "onScroll", Toast.LENGTH_SHORT).show()
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
        //Toast.makeText(context, "onLongPress", Toast.LENGTH_SHORT).show()
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        //Toast.makeText(context, "onFling", Toast.LENGTH_SHORT).show()
        return false
    }

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

                        val valueY : Float = y2 - y1

                        /** Detect Horizontal Swipe on the view. */
                        if (abs(valueX) > MIN_DISTANCE_HORIZONTAL_SWIPE)
                        {
                            //Detect Left to Right Swipe
                            if (x2 > x1) {
                                Log.i("Swipe", "Left to right Swipe")
                                v?.home_fragment_video_suggestion_view?.visibility = View.GONE
                            }
                            else
                            {
                                //Detect Right to Left Swipe

                                Log.i("Swipe", "Right to left Swipe")
                                if (v?. story_view?.visibility == View.VISIBLE)
                                {
                                    v.story_view?.visibility = View.GONE
                                }
                                v?.home_fragment_video_suggestion_view?.visibility = View.VISIBLE
                                v?.home_fragment_video_suggestion_view?.apply {
                                    layoutManager = LinearLayoutManager(v?.context , LinearLayoutManager.VERTICAL ,false )
                                    adapter = FeedSuggestionAdapter(arrVideo)

                                }
                            }
                        }

                    }
                }
            }
        return true
    }

    /** Play the Video in the screen. */
    override fun onViewAttachedToWindow (holder: MyViewHolder)
    {
        holder.itemView.exoPlayer_home_fragment.player.playWhenReady = true;
    }


    /** Pause the Video On View Changed and scrolled. */
    override fun onViewDetachedFromWindow (holder: MyViewHolder){
        holder.itemView.exoPlayer_home_fragment.player.playWhenReady = false;
    }

}
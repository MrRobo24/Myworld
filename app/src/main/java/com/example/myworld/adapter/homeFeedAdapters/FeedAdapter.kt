package com.example.myworld.adapter.homeFeedAdapters

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.model.VideoModel
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
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.net.URI

class FeedAdapter(var context: Context, var arrVideo : ArrayList<VideoModel>) : RecyclerView.Adapter<FeedAdapter.MyViewHolder>()
{

    //lateinit var player : PlayerView
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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int
    {
        return arrVideo.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        Log.i("VIDEO_URL" , position.toString())
        val video = arrVideo[position]
        holder.itemView.user_feed_name.text = video.videoTitle
        holder.itemView.user_feed_discription.text = video.videoDesc

        //initializeExoPlayer(video.videoUrl , holder)
        Log.i("VIDEO_URL" , video.videoUrl)

        initializePlayer(context , video , holder)
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
        simpleExoPlayer.playWhenReady = false

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

    override fun onViewAttachedToWindow (holder: MyViewHolder){
        holder.itemView.exoPlayer_home_fragment.player.playWhenReady = true;
    }

    override fun onViewDetachedFromWindow (holder: MyViewHolder){
        holder.itemView.exoPlayer_home_fragment.player.playWhenReady = false;
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        fun bind(arrVideoModel: VideoModel)
        {
            itemView.user_feed_name.text = arrVideoModel.videoTitle
            itemView.user_feed_discription.text = arrVideoModel.videoDesc
        }
    }
}
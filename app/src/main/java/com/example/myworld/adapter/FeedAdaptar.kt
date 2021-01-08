package com.example.myworld.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myworld.R
import com.example.myworld.fragment.HomeFragment
import com.example.myworld.model.VideoModel
import com.example.myworld.utilites.Constant
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import kotlinx.android.synthetic.main.custom_controller_exo_player_home_fragment.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.logging.Handler

class FeedAdaptar(var context: Context , var arrVideo : ArrayList<VideoModel>) : RecyclerView.Adapter<FeedAdaptar.MyViewHolder>()
{

    var arrVideoModel: ArrayList<VideoModel> = arrVideo


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_home, parent, false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int
    {
        return arrVideoModel.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val video = arrVideo[position]
        holder.bind(video)
        holder.itemView.user_feed_name.text = video.videoTitle
        holder.itemView.user_feed_discription.text = video.videoDesc

        initializePlayer(context , video , holder)
    }

    /** Initialize ExoPlayer */
    private fun initializePlayer(context: Context , video : VideoModel , holder : MyViewHolder)
    {
        var simpleExoPlayer : SimpleExoPlayer

        //Initialise load control
        var loadControl : LoadControl = DefaultLoadControl()

        //Initialise band width meter
        var bandWidthMeter : BandwidthMeter = DefaultBandwidthMeter()

        //Initialize track selector
        var trackSelector : TrackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWidthMeter))

        //Initial Simple ExoPlayer
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(context , trackSelector , loadControl)

        //Initialize data source factory
        var factory : DefaultHttpDataSourceFactory = DefaultHttpDataSourceFactory("exoplayer_video")

        //Initialize extractors factory
        var extractorsFactory : ExtractorsFactory = DefaultExtractorsFactory()

        //Initialize media source
        var mediaSource : MediaSource = ExtractorMediaSource(video.videoUrl.toUri() , factory , extractorsFactory , null , null)

        //Set Player
        holder.itemView.exoPlayer_home_fragment.player = simpleExoPlayer

        //Keep screen on
        holder.itemView.exoPlayer_home_fragment.keepScreenOn = true

        //Prepare media
        simpleExoPlayer.prepare(mediaSource)

        //Play media when Player is ready
        simpleExoPlayer.playWhenReady = true

        simpleExoPlayer.repeatMode = Player.REPEAT_MODE_ONE

        simpleExoPlayer.addListener(object : Player.EventListener {
            override fun onTimelineChanged(timeline: Timeline?, manifest: Any?, reason: Int)
            {

            }

            override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?)
            {

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

        //TODO PLAY AND PAUSE THE VIDEO IN HOME FEED
        holder.itemView.exoPlay_custom_controller_layout.setOnClickListener {
            if (Constant.exoPlayerIsPlaying)
            {
                holder.itemView.exoplayer_pause_button_home_fragment.visibility = View.VISIBLE
                holder.itemView.exoplayer_play_button_home_fragment.visibility = View.GONE
                simpleExoPlayer.playWhenReady = false
            }
            else
            {
                holder.itemView.exoplayer_pause_button_home_fragment.visibility = View.GONE
                holder.itemView.exoplayer_play_button_home_fragment.visibility = View.VISIBLE
                simpleExoPlayer.playWhenReady = true
            }
        }
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
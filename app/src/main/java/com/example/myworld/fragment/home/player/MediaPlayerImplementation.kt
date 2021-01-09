package com.example.myworld.fragment.home.player

import android.content.Context
import android.net.Uri
import com.danikula.videocache.CacheListener
import com.danikula.videocache.HttpProxyCacheServer
import com.example.myworld.model.VideoModel
import com.example.myworld.utilites.CacheUtils
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ads.AdsMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.coroutines.channels.consumesAll
import java.io.File

class MediaPlayerImplementation  private constructor(private val context: Context ) : CacheListener
{
    private val proxy : HttpProxyCacheServer = HttpProxyCacheServer.Builder(context.applicationContext)
            .cacheDirectory(CacheUtils.getVideoCacheDir(context.applicationContext))
            .build()

    val player : SimpleExoPlayer

    init {
        player = initializePlayer()
    }

    companion object
    {
        @Volatile
        private var INSTANCE : MediaPlayerImplementation? = null

        fun getInstance(context: Context) : MediaPlayerImplementation
        {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this)
            {
                val newInstance = MediaPlayerImplementation(context)
                INSTANCE = newInstance
                return newInstance
            }
        }
    }

    /** Initialize ExoPlayer and returning the Instance of new exoPlayer */
    private fun initializePlayer() : SimpleExoPlayer
    {

        //Initialise band width meter
        val  bandWidthMeter by lazy {
            DefaultBandwidthMeter()
        }

        val adaptiveTrackSelection by lazy {
            AdaptiveTrackSelection.Factory(bandWidthMeter)
        }

        //Initialize track selector
        val trackSelector : TrackSelector = DefaultTrackSelector(AdaptiveTrackSelection.Factory(bandWidthMeter))

        val renderersFactory = DefaultRenderersFactory(context)

        //Initialise load control
        val loadControl : LoadControl = DefaultLoadControl()

        return ExoPlayerFactory.newSimpleInstance(
                renderersFactory,
                trackSelector,
                loadControl
        )
    }

    /** Play the ExoPlayer when the player is ready. */
    fun play(playerState: PlayerState , video : VideoModel)
    {
        player.prepare(buildMediaSourcePlayList(video.videoUrl) , false , false)
        player.repeatMode = Player.REPEAT_MODE_ONE
        player.seekTo(playerState.currentWindow , playerState.playbackPosition)
        player.playWhenReady = playerState.playWhenReady
    }

    /** Build the Media Source for the player and return the media source. */
    private fun buildMediaSourcePlayList(url : String) : MediaSource
    {
        setProxy(url)
        val dataSourceFactory = DefaultHttpDataSourceFactory("mediaPlayer" , DefaultBandwidthMeter())
        val dashChunkSourceFactory = DefaultDashChunkSource.Factory(dataSourceFactory)
        val userAgent = Util.getUserAgent(context , context.packageName)
        return DashMediaSource(Uri.parse(url),dataSourceFactory , dashChunkSourceFactory , null , null)

    }

    /** Release the Player after the View is ended. */
    fun releasePlayer()
    {
        player.release()
        proxy.unregisterCacheListener(this)
    }


    override fun onCacheAvailable(cacheFile: File?, url: String?, percentsAvailable: Int) {}

    /** Register the url for the cache */
    private fun setProxy(url: String)
    {
        proxy.registerCacheListener(this , url)
    }

}
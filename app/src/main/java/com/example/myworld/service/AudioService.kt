package com.example.myworld.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.support.v4.media.session.PlaybackStateCompat.ACTION_PLAY
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.example.myworld.model.SongInfo
import com.example.myworld.utilites.Constant

open class AudioService : Service , MediaPlayer.OnPreparedListener
{
    private var songInfo : String? = null
    private var context : Context? = null
    var mediaPlayer = MediaPlayer()
    constructor()

    constructor(context: Context, songURL: String) : this(){
        this.songInfo = songURL
        this.context = context
    }

    override fun onBind(intent: Intent?): IBinder?
    {
        TODO("Not yet implemented")
    }

    override fun onCreate()
    {
        super.onCreate()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {

        when(intent?.action)
        {
            ACTION_PLAY.toString() -> {
                val myUri: Uri? = songInfo?.toUri() // initialize Uri here
                mediaPlayer.apply {
                    setAudioAttributes(
                            AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                    )
                    this.setOnPreparedListener(this@AudioService)
                    if (myUri != null)
                    {
                        context?.let { setDataSource(it, myUri) }
                        prepareAsync()
//                Constant.isPlaying = true
//                        start()
                    }
                }
            }
        }
        return START_STICKY
    }

    override fun onPrepared(mp: MediaPlayer?)
    {
        mp?.start()
    }

    fun isPlaying() : Boolean
    {
        return mediaPlayer.isPlaying
    }

    private fun play()
    {
        mediaPlayer.start()
    }

    override fun onStart(intent: Intent?, startId: Int)
    {
        super.onStart(intent, startId)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        mediaPlayer.reset()
    }

}
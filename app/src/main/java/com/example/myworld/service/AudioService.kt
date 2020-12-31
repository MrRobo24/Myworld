package com.example.myworld.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.core.net.toUri
import com.example.myworld.SongInfo

open class AudioService : Service
{
    private var songInfo : SongInfo? = null
    private var context : Context? = null
    constructor()

    constructor(context: Context, songInfo: SongInfo) : this(){
        this.songInfo = songInfo
        this.context = context
    }

    override fun onBind(intent: Intent?): IBinder?
    {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int
    {
        var mp = MediaPlayer.create(context , this.songInfo?.songURL?.toUri())
        mp.start()
        return START_STICKY
    }

    override fun onStart(intent: Intent?, startId: Int)
    {
        super.onStart(intent, startId)
    }

    override fun onDestroy()
    {
        var mp = MediaPlayer.create(context , this.songInfo?.songURL?.toUri())
        mp.stop()
        super.onDestroy()
    }

}
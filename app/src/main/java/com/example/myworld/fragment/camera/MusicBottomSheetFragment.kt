package com.example.myworld.fragment.camera

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.adapter.cameraAdapters.SongAdapter
import com.example.myworld.model.SongInfo
import com.example.myworld.service.AudioService
import com.example.myworld.utilites.Constant
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_fragment.*

class MusicBottomSheetFragment : BottomSheetDialogFragment()
{
    var listSongs = ArrayList<SongInfo>()

    private var mediaPlayer : MediaPlayer = MediaPlayer()

    private var isPlayingMusic : Boolean = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        return inflater.inflate(R.layout.bottomsheet_fragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        val offsetFromTop = 40
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            expandedOffset = offsetFromTop
            state = STATE_EXPANDED
        }
    }

    override fun onStart()
    {
        loadSong()
        super.onStart()
    }

    /** lOAD SONGS FROM THE External and Internal STORAGE */
    private fun loadSong()
    {

        /** Fecting the Uri's of the Storage. */
        val externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val internalUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI

        val selectFile = MediaStore.Audio.Media.IS_MUSIC + "!=0"

        /**Reading the data from storage. */
        val externalContentResolver = requireContext().contentResolver!!.query(externalUri,null , selectFile,null , MediaStore.Audio.Media.TITLE)
        val internalContentResolver = requireContext().contentResolver!!.query(internalUri,null , selectFile,null , MediaStore.Audio.Media.TITLE)

        /** Adding all the music from the external storage. */
        if (externalContentResolver!=null)
        {
            while (externalContentResolver.moveToNext())
            {
                val url = externalContentResolver.getString(externalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.DATA))
                val title = externalContentResolver.getString(externalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.TITLE))
//                val author = externalContentResolver.getString(externalContentResolver.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))



                listSongs.add(SongInfo(title,url.toString()))
                Log.i("URL",externalUri.toString())
            }
        }

        /** Adding all the music from the internal storage. */
        if (internalContentResolver!=null)
        {
            while (internalContentResolver.moveToNext())
            {
                val url = internalContentResolver.getString(internalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.DATA))
                val title = internalContentResolver.getString(internalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.TITLE))

                //val artist = externalContentResolver?.getString(externalContentResolver.getColumnIndex(MediaStore.Audio.Media.ARTIST))

                listSongs.add(SongInfo(title,url.toString()))
                Log.i("URL",internalUri.toString())
                Log.i("Song Name" , title)
            }
        }

        /** Setting up the Music Recycler View for displaying the music list */
        recycler_view.apply {
            //  Setting the layout manager
            layoutManager = LinearLayoutManager(context)

            /** Setting up the adapter of the recycler view. */
            adapter = SongAdapter(listSongs , context , object : SongAdapter.MusicListener{
                override fun onClick(song: SongInfo): String
                {
                    if (Constant.isPlaying)
                    {
                        mediaPlayer.reset()
                    }
                    //Log.i("SongUrl" , song.songURL.toString())
                    val myUri: Uri? = song.songURL?.toUri() // initialize Uri here
                    mediaPlayer.apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                .setUsage(AudioAttributes.USAGE_MEDIA)
                                .build()
                        )
                        if (myUri != null)
                        {
                            setDataSource(context, myUri)
                            prepare()
                            Constant.isPlaying = true
                            start()
                        }
                    }
                    return super.onClick(song)
                }

                override fun onSelect(song: SongInfo): String
                {
                    val intent = Intent(context , AudioService(context,song.songURL.toString())::class.java)
                    context?.startService(intent)
                    Bundle().putString(Constant.songURL , song.songURL.toString())
                    Log.i("Song - On Select Call" , song.toString())
                    return  super.onSelect(song)
                }
            })
        }
    }

    override fun onResume()
    {
        /** Resetting the MediaPlayer */
        mediaPlayer.reset()
        super.onResume()
    }

    override fun onPause()
    {
            Constant.isPlaying = false
            mediaPlayer.reset()
            super.onPause()
    }

    override fun onDestroyView()
    {
        Constant.isPlaying =false
        mediaPlayer.reset()
        super.onDestroyView()
    }

}
package com.example.myworld.fragment

import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myworld.R
import com.example.myworld.adapter.SongAdapter
import com.example.myworld.SongInfo
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_fragment.*

class BottomSheetFragment : BottomSheetDialogFragment()
{
    var listSongs = ArrayList<SongInfo>()


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
        var externalUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        var internalUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI

        var selectFile = MediaStore.Audio.Media.IS_MUSIC + "!=0"

        //Reading the data from storage
        var externalContentResolver = context!!.contentResolver!!.query(externalUri,null , selectFile,null , MediaStore.Audio.Media.TITLE)
        var internalContentResolver = context!!.contentResolver!!.query(internalUri,null , selectFile,null , MediaStore.Audio.Media.TITLE)

        if (externalContentResolver!=null)
        {
            while (externalContentResolver.moveToNext())
            {
                var url = externalContentResolver.getString(externalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.DATA))
                var title = externalContentResolver.getString(externalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.TITLE))
//                val author = externalContentResolver.getString(externalContentResolver.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))



                listSongs.add(SongInfo(title,externalUri.toString()))
                Log.i("URL",externalUri.toString())
            }
        }

        if (internalContentResolver!=null)
        {
            while (internalContentResolver.moveToNext())
            {
                var url = internalContentResolver.getString(internalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.DATA))
                var title = internalContentResolver.getString(internalContentResolver.getColumnIndex(
                    MediaStore.Audio.Media.TITLE))

                //val author = externalContentResolver?.getString(externalContentResolver.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))

                listSongs.add(SongInfo(title,internalUri.toString()))
                Log.i("URL",internalUri.toString())
                Log.i("Song Name" , title)
            }
        }

        music_list_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SongAdapter(listSongs , context)
        }
    }

}
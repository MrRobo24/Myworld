package com.example.myworld.`class`

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.ViewModel

open class Gallery : ViewModel()
{

    /** Get all the image from the storage and return their URI as a string .*/
    fun listOfImages(context: Context) : ArrayList<String>
    {
        var absolutePathOfImage : String? = null
        var listOfAllImage = ArrayList<String>()

        /** For Image */
        var external_image_uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        var internal_image_uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI
        var projection = arrayOf(MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        var oderBy = MediaStore.Images.Media.DATE_TAKEN
        var external_cursor : Cursor? = context.contentResolver.query(external_image_uri,projection,null,null,
            "$oderBy DESC"
        )
        var internal_cursor : Cursor? = context.contentResolver.query(internal_image_uri,projection,null,null,
            "$oderBy DESC"
        )

        //External Storage File
        var external_column_index_data = external_cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        if (external_cursor != null)
        {
            while (external_cursor.moveToNext())
            {
                absolutePathOfImage = external_column_index_data?.let { external_cursor.getString(it) }

                if (absolutePathOfImage != null)
                {
                    listOfAllImage.add(absolutePathOfImage)
                }
            }
        }
        //Internal Storage File
        var internal_column_index_data = internal_cursor?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        if (internal_cursor != null)
        {
            while (internal_cursor.moveToNext())
            {
                absolutePathOfImage = internal_column_index_data?.let { internal_cursor.getString(it) }

                if (absolutePathOfImage != null)
                {
                    listOfAllImage.add(absolutePathOfImage)
                }
            }
        }

        /**For Video Fetching */

        var external_video_uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var internal_video_uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
        var projection_video = arrayOf(MediaStore.MediaColumns.DATA,MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        var oderByVideo = MediaStore.Video.Media.DATE_TAKEN
        var external_cursor_video : Cursor? = context.contentResolver.query(external_video_uri,projection_video,null,null,oderByVideo+ " DESC")
        var internal_cursor_video : Cursor? = context.contentResolver.query(internal_video_uri,projection_video,null,null,oderByVideo+ " DESC")

        //External Storage File
        var external_column_index_data_video = external_cursor_video?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        if (external_cursor_video != null)
        {
            while (external_cursor_video.moveToNext())
            {
                absolutePathOfImage = external_column_index_data_video?.let { external_cursor_video.getString(it) }

                if (absolutePathOfImage != null)
                {
                    listOfAllImage.add(absolutePathOfImage)
                }
            }
        }

        //Internal Storage File
        var internal_column_index_data_video = internal_cursor_video?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        if (internal_cursor_video != null)
        {
            while (internal_cursor_video.moveToNext())
            {
                absolutePathOfImage = internal_column_index_data_video?.let { internal_cursor_video.getString(it) }

                if (absolutePathOfImage != null)
                {
                    listOfAllImage.add(absolutePathOfImage)
                }
            }
        }

        return listOfAllImage
    }

    /** Get all the video from the storage and return their URI as a String . */
    fun listOfVideos(context: Context) : ArrayList<String>
    {
        var absolutePathOfImage : String? = null
        var listOfAllVideos = ArrayList<String>()

        /**For Video Fetching */

        var external_video_uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        var internal_video_uri = MediaStore.Video.Media.INTERNAL_CONTENT_URI
        var projection_video = arrayOf(MediaStore.MediaColumns.DATA,MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        var oderByVideo = MediaStore.Video.Media.DATE_TAKEN
        var external_cursor_video : Cursor? = context.contentResolver.query(external_video_uri,projection_video,null,null,oderByVideo)
        var internal_cursor_video : Cursor? = context.contentResolver.query(internal_video_uri,projection_video,null,null,oderByVideo)

        //External Storage File
        var external_column_index_data_video = external_cursor_video?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        if (external_cursor_video != null)
        {
            while (external_cursor_video.moveToNext())
            {
                absolutePathOfImage = external_column_index_data_video?.let { external_cursor_video.getString(it) }

                if (absolutePathOfImage != null)
                {
                    listOfAllVideos.add(absolutePathOfImage)
                }
            }
        }

        //Internal Storage File
        var internal_column_index_data_video = internal_cursor_video?.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)

        if (internal_cursor_video != null)
        {
            while (internal_cursor_video.moveToNext())
            {
                absolutePathOfImage = internal_column_index_data_video?.let { internal_cursor_video.getString(it) }

                if (absolutePathOfImage != null)
                {
                    listOfAllVideos.add(absolutePathOfImage)
                }
            }
        }
        return listOfAllVideos
    }
}
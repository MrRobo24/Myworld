package com.example.myworld.utilites

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.camera.core.Camera
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.myworld.fragment.profile.ProfileFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Constant
{
    companion object
    {
        //Permission request code
        const val PERMISSION_REQUEST_CODE = 0

        //Permission is granted or not
        var isPermission : Boolean =  false

        var SONG_NAME : String?= null

        //Camera Fragment
        var camera : Camera? = null
        var preview : Preview? = null
        var recordVideo : VideoCapture? = null

        var count : Int = 2

        var isRecording : Boolean = false

        var songURL : String? = null

        var isFlash : Boolean = false

        var isPlaying : Boolean = false

        var savedVideoURI : String? = null

        //HomeFeed - Home Fragment
        var exoPlayerIsPlaying : Boolean = true

        const val READ_STORAGE_PERMISSION_CODE = 1
        const val PICK_IMAGE_REQUEST_CODE = 2

        const val CAMERA_REQUEST_CODE = 3
        const val CAMERA_PICK_IMAGE = 4

        lateinit var currentPhotoPath: String

        lateinit var imageUri : Uri
    }

    /**
     * Select Picture From Gallery
     */
    fun showImageChooser(fragment: Fragment)
    {
//        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//
//        fragment as Activity.startActivityForResult(galleryIntent,
//            PICK_IMAGE_REQUEST_CODE
//        )
    }

    /**
     * Select Picture From Camera
     */
    fun openCamera(fragment: Fragment)
    {
//        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//
//        var fileName: String = timeStamp
//
//        var storageDirectory: File? = fragment.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//
//        try
//        {
//            var imageFile: File = File.createTempFile(fileName, ".jpg", storageDirectory)
//
//            currentPhotoPath = imageFile.absolutePath
//
//            imageUri = FileProvider.getUriForFile(fragment.requireContext(), "edu.califer.projectmanager.fileprovider", imageFile)
//
//            Log.e("Uri" , "$imageUri")
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
//            fragment.startActivityForResult(cameraIntent, CAMERA_PICK_IMAGE)
//            fragment.setResult(Activity.RESULT_OK)
//
//        } catch (e: IOException)
//        {
//            e.printStackTrace()
//        }

    }

    /**
     * Get the Type of The data or file extension
     */
    fun getFileExtension(activity: Activity, uri: Uri?): String?
    {
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}
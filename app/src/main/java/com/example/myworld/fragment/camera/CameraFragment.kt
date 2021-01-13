package com.example.myworld.fragment.camera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.myworld.R
import com.example.myworld.activity.HomeActivity
import com.example.myworld.model.VideoModel
import com.example.myworld.service.AudioService
import com.example.myworld.utilites.Constant
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_camera.*
import kotlinx.android.synthetic.main.user_video_upload.*
import java.io.File


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment()
{
    //Video Model
    var video : VideoModel? = null
    //BitMap for the Video Thumbnail
    private var bitmap: Bitmap? = null

    //Uri For the Recorded Video
    private var videoUri : Uri? = null

    //CountDown Timer For Start Recording
    private var countDownTimer : CountDownTimer? = null
    private var restProgress = 0
    private var restTimerDuration : Long = 5

    //MediaPlayer
    private var mp : MediaPlayer? = null
    private var url : String = ""
    private var songName : String = ""

    private var mediaRecorder = MediaRecorder()

    var sm: SendMessage?=null
    var path=""
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View?
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false)
    }

    override fun onStart()
    {
        /** Permissions checks */
        if ((context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.CAMERA
                ) } != PackageManager.PERMISSION_GRANTED)
                && (context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) } != PackageManager.PERMISSION_GRANTED)
                && (context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) } != PackageManager.PERMISSION_GRANTED)
                && (context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.RECORD_AUDIO
                ) } != PackageManager.PERMISSION_GRANTED))
        {
            camera_capture.isEnabled = false
            camera_capture_button_start.isEnabled = false
            camera_music.isEnabled = false
            camera_capture_button_stop.isEnabled = false
            gallery_selector.isEnabled = false
            camera_welcometext.visibility = View.VISIBLE
            camera_expresstext.visibility = View.VISIBLE
            camera_buttan_takepermission.visibility = View.VISIBLE
            camera_buttan_takepermission.setOnClickListener {
                askPermission()
            }
        }
        else
        {
            camera_capture_button_start.isEnabled = true
            camera_music.isEnabled = true
            gallery_selector.isEnabled = true
            camera_front_back.isEnabled = true
            camera_capture_button_start.isEnabled = true
            camera_welcometext.visibility = View.GONE
            camera_expresstext.visibility = View.GONE
            camera_buttan_takepermission.visibility = View.GONE
            startCamera()
        }


        /**Start Recording*/
        camera_capture_button_start.setOnClickListener {
            camera_music.visibility = View.GONE
            setTimer()
            camera_capture_button_stop.visibility = View.VISIBLE
            camera_capture_button_start.visibility = View.GONE
        }

        /**Stop Recording*/
        camera_capture_button_stop.setOnClickListener {
            Constant.isRecording = false
            stopRecording()
            camera_capture_button_start.visibility = View.VISIBLE
            camera_capture_button_stop.visibility = View.GONE
        }

        /** Switch Camera Between Front and Back Camera */
        camera_front_back.setOnClickListener {
            Constant.count++
            switchCamera()
        }

        /** Flash On and Off */
        camera_flash.setOnClickListener {
            if (!Constant.isFlash)
            {
                flashON()
                Constant.isFlash = true
            }
            else
            {
                flashOFF()
                Constant.isFlash = false
            }
        }

        /** Music Fetching and Selection */
        camera_music.setOnClickListener {
            val musicFragment = MusicBottomSheetFragment()
            musicFragment.show(childFragmentManager, "BottomSheetDialog")
        }

        /** Gallery setup and Fetching the Files from the storage. */
        gallery_selector.setOnClickListener {
            val galleryFragment = GalleryBottomSheetFragment()
            galleryFragment.show(childFragmentManager, "BottomSheetDialog")
        }

        /** Getting back to Home Fragment */
        camera_view_back.setOnClickListener {
            Intent(activity, HomeActivity::class.java).apply {
                this.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                this.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(this)
                activity?.finish()
            }
        }

        super.onStart()
    }

    /**Binding the Camera with Application's LifeCycle using Camera Provider.*/
    private fun startCamera()
    {
        //Creating a Listener . This let us know that weather our application has been binded with the camera.
        val cameraProvider = context?.let { ProcessCameraProvider.getInstance(it) }
        cameraProvider?.addListener(
                Runnable
                {
                    val cameraProvider = cameraProvider.get()
                    Constant.preview = Preview.Builder().build()
                    Constant.preview!!.setSurfaceProvider(cameraView.surfaceProvider)

                    Constant.recordVideo = VideoCapture.Builder().build()
                    //Camera Selector . By Default it will open back camera
                    val cameraSelector =
                            CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK)
                                    .build()
                    //Unbinding the CameraProvider
                    cameraProvider.unbindAll()
                    //Binding the camera
                    Constant.camera = cameraProvider.bindToLifecycle(
                            this,
                            cameraSelector,
                            Constant.preview,
                            Constant.recordVideo
                    )

                }, ContextCompat.getMainExecutor(context)
        )
    }

    /**Switch Front And Back Camera*/
    private fun switchCamera()
    {
        flashOFF()
        if(Constant.count % 2 == 0)
        {
            startCamera()
        }
        else
        {
            //Creating a Listener . This let us know that weather our application has been binded with the camera.
            val cameraProvider = context?.let { ProcessCameraProvider.getInstance(it) }
            cameraProvider?.addListener(Runnable {

                val cameraProvider = cameraProvider.get()
                Constant.preview = Preview.Builder().build()
                Constant.preview!!.setSurfaceProvider(cameraView.surfaceProvider)

                Constant.recordVideo = VideoCapture.Builder().build()
                //Camera Selector . By Default it will open back camera
                val cameraSelector =
                        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                                .build()
                //Unbinding the CameraProvider
                cameraProvider.unbindAll()
                //Binding the camera
                Constant.camera = cameraProvider.bindToLifecycle(
                        this,
                        cameraSelector,
                        Constant.preview,
                        Constant.recordVideo
                )

            }, ContextCompat.getMainExecutor(context))
        }
    }

    /**Start Recording*/
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    private fun startRecording()
    {
        camera_front_back.isEnabled = false
        gallery_selector.visibility = View.GONE
        //playMusic(url)
        Log.i("URL", url)
        var file = File(
                this.context?.externalMediaDirs?.first(),
                "${System.currentTimeMillis()}.mp4"
        )
        Constant.recordVideo?.startRecording(VideoCapture.OutputFileOptions.Builder(file).build(),
                ContextCompat.getMainExecutor(context),
                object : VideoCapture.OnVideoSavedCallback {
                    override fun onVideoSaved(outputFileResults: VideoCapture.OutputFileResults) {
                        Log.i("SAVED", "Video File : $file")
                        //Generating the thumbnail for the video
                        bitmap = setUpThumbnail(file)
                        video = if (bitmap != null) {
                            VideoModel(bitmap!!, "", "", file.absolutePath)
                        } else {
                            bitmap?.let { VideoModel(it, "", "", file.absolutePath) }
                        }

                        path=file.absolutePath
                        //sm?.sendData(path)
                        // Sending the recorded video URI to the VideoUploadFragment .
                        // Fetching the recorded video from the storage using the URI and then uploading the Video to the server .
                        if (video != null) {
                            VideoUploadFragment().apply {
                                this.arguments = Bundle().apply {
                                    putString(Constant.savedVideoURI, file.toString())
                                    putString("FilePath",path)
                                }
                            }
                        }
                        val sharedPreferences: SharedPreferences?=context?.getSharedPreferences("SharePref", Context.MODE_PRIVATE)
                        val sp:SharedPreferences.Editor?=sharedPreferences?.edit()
                        sp?.putString("FilePath",path)
                        sp?.apply()
                        sp?.commit()

                    }

                    override fun onError(videoCaptureError: Int, message: String, cause: Throwable?) {
                        Log.i("tag", "Video Error: $message")
                    }
                })

    }

    /**Stop Recording*/
    @SuppressLint("RestrictedApi")
    private fun stopRecording()
    {
        //Chronometer Reset after stopping.
        recording_timer.stop()
        recording_timer.visibility = View.GONE
        recording_timer_dot.visibility = View.GONE

        //Recording of the video has been stopped.
        Constant.recordVideo?.stopRecording()

        camera_capture_button_start.visibility = View.VISIBLE
        gallery_selector.visibility = View.VISIBLE
        camera_capture_button_stop.visibility = View.GONE
        camera_music.visibility = View.VISIBLE
        camera_front_back.visibility = View.VISIBLE
        camera_front_back.isEnabled = true
        Log.i("STOP", "Video File stopped")

        //Resetting the timer
        restProgress = 0
        restTimerDuration = 5

        //TODO send video thumbnail to videoUploadFragment
        Log.i("Bitmap", bitmap.toString())
        //user_video_thumbnail.setImageBitmap(bitmap)
        //Sending User to the VideoUploadFragment So that they can upload their recorded video.
        fragmentManager?.beginTransaction()?.replace(R.id.container, VideoUploadFragment())?.commit()
    }

    //To initialise SendMessage interface
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            sm=activity as SendMessage?
        } catch (e: ClassCastException) {
            throw ClassCastException("Error in retrieving data. Please try again")
        }
    }

    //Interface to connect CameraFragment and VideoUploadFragment
    interface SendMessage {
        fun sendData(message: String?)
    }

    /** Setup the Thumbnail of the recorded Video. */
    private fun setUpThumbnail(file: File) : Bitmap?
    {
        return  ThumbnailUtils.createVideoThumbnail(file.toUri().toString(),
                MediaStore.Images.Thumbnails.MINI_KIND);
    }

    /**Set CountDown Timer For the Recording.
     *Here we have started a timer of 3 seconds so the 3000 is milliseconds is 3 seconds and the countdown interval is 1 second so it 1000.*/
    private fun setTimer()
    {
        timer.visibility = View.VISIBLE
        Log.i("Finised", "SetTimer")
        countDownTimer = object : CountDownTimer(restTimerDuration * 1000, 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                // It is increased to ascending order
                restProgress++
                // Current progress is set to text view in terms of seconds.
                timer.text = (restTimerDuration.toInt() - restProgress).toString()
                Log.i("Finised", (restTimerDuration.toInt() - restProgress).toString())
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish()
            {
                timer.visibility = View.INVISIBLE
                recording_timer.base = SystemClock.elapsedRealtime()
                recording_timer.visibility = View.VISIBLE
                recording_timer_dot.visibility = View.VISIBLE
                Constant.isRecording = true
                camera_capture_button_stop.visibility = View.VISIBLE
                camera_capture_button_start.visibility = View.GONE
                startRecording()
                camera_front_back.visibility = View.INVISIBLE
                recording_timer.start()
                restProgress = 0
                restTimerDuration = 5
            }
        }.start()
    }

    /** Switch On the Flash */
    private fun flashON()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            Constant.camera?.cameraControl?.enableTorch(true)
            Constant.isFlash = true
        }
    }

    /** Switch OFF the Flash    */
    private fun flashOFF()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            Constant.camera?.cameraControl?.enableTorch(false)
            Constant.isFlash = false
        }
    }

    /**     Ask For Permissions */
    private fun askPermission()
    {
        ActivityCompat.requestPermissions(
                context as Activity, arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
        ),
                Constant.PERMISSION_REQUEST_CODE
        )
    }

    /**     Check Permissions required for the Camera   */
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constant.PERMISSION_REQUEST_CODE)
        {
            Log.i("Reach", "Reached + {${Constant.isPermission}}")
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Log.i("Reach", "Reached + {${Constant.isPermission}}")
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.i("Reach", "Reached + {${Constant.isPermission}}")
                    if (grantResults[2] == PackageManager.PERMISSION_GRANTED)
                    {
                        Log.i("Reach", "Reached + {${Constant.isPermission}}")
                        if (grantResults[3] == PackageManager.PERMISSION_GRANTED)
                        {
                            Log.i("Reach", "Reached + {${Constant.isPermission}}")
                            Constant.isPermission = true
                            camera_capture_button_stop.isEnabled = true
                            camera_front_back.isEnabled = true
                            camera_welcometext.visibility = View.INVISIBLE
                            camera_expresstext.visibility = View.INVISIBLE
                            camera_buttan_takepermission.visibility = View.INVISIBLE
                            camera_capture_button_start.isEnabled = true
                            camera_music.isEnabled = true
                            gallery_selector.isEnabled = true
                        }
                    }
                }
            }
        }
        else
        {
            Toast.makeText(
                    context, "Permissions are required to run the application!\nKindly allow.",
                    Toast.LENGTH_SHORT
            ).show()
            askPermission()
        }
    }

    override fun onResume()
    {
        /** Permissions checks */
        if ((context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.CAMERA
                ) } != PackageManager.PERMISSION_GRANTED)
            && (context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) } != PackageManager.PERMISSION_GRANTED)
            && (context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) } != PackageManager.PERMISSION_GRANTED)
            && (context?.let { ContextCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.RECORD_AUDIO
                ) } != PackageManager.PERMISSION_GRANTED))
        {
            camera_capture.isEnabled = false
            camera_capture_button_start.isEnabled = false
            camera_music.isEnabled = false
            camera_capture_button_stop.isEnabled = false
            gallery_selector.isEnabled = false
            camera_welcometext.visibility = View.VISIBLE
            camera_expresstext.visibility = View.VISIBLE
            camera_buttan_takepermission.visibility = View.VISIBLE
            camera_buttan_takepermission.setOnClickListener {
                askPermission()
            }
        }
        else
        {
            camera_capture_button_start.isEnabled = true
            camera_music.isEnabled = true
            gallery_selector.isEnabled = true
            camera_front_back.isEnabled = true
            camera_capture_button_start.isEnabled = true
            camera_welcometext.visibility = View.GONE
            camera_expresstext.visibility = View.GONE
            camera_buttan_takepermission.visibility = View.GONE
            startCamera()
        }


        /**Start Recording*/
        camera_capture_button_start.setOnClickListener {
            camera_music.visibility = View.GONE
            setTimer()
            camera_capture_button_stop.visibility = View.VISIBLE
            camera_capture_button_start.visibility = View.GONE
        }

        /**Stop Recording*/
        camera_capture_button_stop.setOnClickListener {
            Constant.isRecording = false
            stopRecording()
            camera_capture_button_start.visibility = View.VISIBLE
            camera_capture_button_stop.visibility = View.GONE
        }

        /** Switch Camera Between Front and Back Camera */
        camera_front_back.setOnClickListener {
            Constant.count++
            switchCamera()
        }

        /** Flash On and Off */
        camera_flash.setOnClickListener {
            if (!Constant.isFlash)
            {
                flashON()
                Constant.isFlash = true
            }
            else
            {
                flashOFF()
                Constant.isFlash = false
            }
        }

        /** Music Fetching and Selection */
        camera_music.setOnClickListener {
            val musicFragment = MusicBottomSheetFragment()
            musicFragment.show(childFragmentManager, "BottomSheetDialog")
        }

        /** Gallery setup and Fetching the Files from the storage. */
        gallery_selector.setOnClickListener {
            val galleryFragment = GalleryBottomSheetFragment()
            galleryFragment.show(childFragmentManager, "BottomSheetDialog")
        }

        /** Getting back to Home Fragment */
        camera_view_back.setOnClickListener {
            val i = Intent(activity, HomeActivity::class.java)
            startActivity(i)
        }

        restProgress = 0
        restTimerDuration = 5
        camera_capture_button_start.isEnabled = true
        if(Constant.isPermission)
        {
            camera_capture_button_stop.isEnabled = true
            camera_welcometext.visibility = View.INVISIBLE
            camera_expresstext.visibility = View.INVISIBLE
            camera_front_back.isEnabled = true
            camera_buttan_takepermission.visibility = View.INVISIBLE
            camera_capture_button_start.isEnabled = true
            camera_music.isEnabled = true
            gallery_selector.isEnabled = true
        }
        startCamera()
        if (Constant.isRecording)
        {
            Constant.isRecording = false
            stopRecording()
        }
        Constant.songURL = arguments?.getString(Constant.songURL)
        Log.i("SongURL", Constant.songURL.toString())
        var songUrl = Constant.songURL.toString()
        val intent = Intent(context, AudioService(context!!, songUrl)::class.java)
        //activity?.startService(intent)
//        var songUrl = activity?.intent?.getStringExtra(Constant.songURL)
//        url=songUrl.toString()
//        Log.i("SONG URL RECEIVED" , url)
//        var songName = activity?.intent?.getStringExtra(Constant.SONG_NAME)
//        if (songName != null)
//        {
//            this.songName = songName
//        }
//        if (url != null)
//        {
////            playMusic(url)
//            Log.i("SONG" , url)
//            Log.i("SONG" , songName.toString())
//        }
//        else
//        {
//            Log.i("SONG_URL" ,"NULL RETURNED")
//        }
        super.onResume()
    }

    override fun onDestroy()
    {
        if (countDownTimer != null)
        {
            countDownTimer!!.cancel()
        }
        if(Constant.isRecording)
        {
            recording_timer.stop()
            recording_timer_dot.visibility = View.GONE
            stopRecording()
        }
        super.onDestroy()
    }

    override fun onPause()
    {
        if (Constant.isRecording)
        {
            stopRecording()
        }
        restProgress = 0
        restTimerDuration = 5
        super.onPause()
    }

    override fun onStop()
    {
        if (Constant.isRecording)
        {
            stopRecording()
        }
        restProgress = 0
        restTimerDuration = 5
        super.onStop()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NotificationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
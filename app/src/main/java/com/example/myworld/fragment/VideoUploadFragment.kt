package com.example.myworld.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myworld.R
import com.example.myworld.utilites.Constant
import kotlinx.android.synthetic.main.user_video_upload.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VideoUploadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VideoUploadFragment : Fragment()
{
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var videoURI : String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.user_video_upload, container, false)
    }

    override fun onStart()
    {

        //Getting the recorded video URI from the camera fragment.
        videoURI = arguments?.getString(Constant.savedVideoURI)


        /** Action performed when user clicks on upload Button.
         * Uploading the file to the server and then sending back the user to the camera fragment. */
        videoUploadButton.setOnClickListener {
            //TODO UPLOAD THE SAVED VIDEO FILE TO THE SERVER.

            //Sending back to camera fragment after successful upload of the saved video .
            Log.i("Saved Video Uri " , videoURI.toString())
        }

        back_upload_icon.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container,CameraFragment())?.commit()
        }

        super.onStart()
    }

    override fun onResume()
    {

        super.onResume()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VideoUploadFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                VideoUploadFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
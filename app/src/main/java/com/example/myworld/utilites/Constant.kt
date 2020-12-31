package com.example.myworld.utilites

import androidx.camera.core.Camera
import androidx.camera.core.Preview
import androidx.camera.core.VideoCapture

class Constant
{
    companion object
    {
        //Camera
        const val PERMISSION_REQUEST_CODE = 0

        var isPermission : Boolean =  false

        var SONG_NAME : String?= null

        var camera : Camera? = null
        var preview : Preview? = null
        var recordVideo : VideoCapture? = null

        var count : Int = 2

        var isRecording : Boolean = false

        var songURL : String? = null

        var isFlash : Boolean = false
    }
}
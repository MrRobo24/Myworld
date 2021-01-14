package com.example.myworld.model

import android.graphics.Bitmap
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class VideoModel(
    var videoThumbnail: Bitmap?,
    var videoTitle: String,
    var videoDesc: String,
    var videoUrl: String
) : Serializable

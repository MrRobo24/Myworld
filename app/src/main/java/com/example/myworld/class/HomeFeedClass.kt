package com.example.myworld.`class`

import androidx.lifecycle.ViewModel
import com.example.myworld.model.VideoModel

class HomeFeedClass : ViewModel()
{
    var arrVideoModel = ArrayList<VideoModel>()

    /** Fetch the Details of the Home Feed */
    fun addDetails() : ArrayList<VideoModel>
    {
        arrVideoModel.add(
                VideoModel(
                        "Califer" ,
                        "Video For Testing" ,
                "https://cdn.you.ki/transcoded/he7ZZwvqX7f89Z96gAhSQCEfSJi2/merged_d0cd5ef6-a3d7-466e-9c33-d0726f1ab014-720x1280.mp4"
                )
        )


        arrVideoModel.add(
                VideoModel(
                        "Califer" ,
                        "Video For Testing" ,
                        "https://cdn.you.ki/transcoded/ZbeBESIEBZT6ZO0ip8GBlTCqgYG2/9474065c-4b32-4a98-981c-a3c01748d17d-720x1280.mp4"
                )
        )

        arrVideoModel.add(
            VideoModel(
                "Tree with flowers",
                "The branches of a tree wave in the breeze, with pointy leaves ",
                "https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4"
            )
        )
        arrVideoModel.add(
            VideoModel(
                "multicolored lights",
                "A man with a small beard and mustache wearing a white sweater, sunglasses, and a backwards black baseball cap turns his head in different directions under changing colored lights.",
                "https://assets.mixkit.co/videos/preview/mixkit-man-under-multicolored-lights-1237-large.mp4"
            )
        )
        arrVideoModel.add(
            VideoModel(
                "holding neon light",
                "Bald man with a short beard wearing a large jean jacket holds a long tubular neon light thatch",
                "https://assets.mixkit.co/videos/preview/mixkit-man-holding-neon-light-1238-large.mp4"
            )
        )
        arrVideoModel.add(
            VideoModel(
                "Sun over hills",
                "The sun sets or rises over hills, a body of water beneath them.",
                "https://assets.mixkit.co/videos/preview/mixkit-sun-over-hills-1183-large.mp4"
            )
        )
        return arrVideoModel
    }
}
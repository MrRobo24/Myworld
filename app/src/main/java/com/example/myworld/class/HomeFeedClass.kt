package com.example.myworld.`class`

import androidx.lifecycle.ViewModel
import com.example.myworld.model.VideoModel

class HomeFeedClass : ViewModel()
{
    private var arrVideoModel = ArrayList<VideoModel>()

    /** Fetch the Details of the Home Feed */
    fun addDetails() : ArrayList<VideoModel>
    {

        arrVideoModel.add(
            VideoModel( null,
                "Tree with flowers",
                "The branches of a tree wave in the breeze, with pointy leaves ",
                "https://cdn.you.ki/transcoded/he7ZZwvqX7f89Z96gAhSQCEfSJi2/merged_d0cd5ef6-a3d7-466e-9c33-d0726f1ab014-720x1280.mp4"
            )
        )
        arrVideoModel.add(
            VideoModel(null,
                "multicolored lights",
                "A man with a small beard and mustache wearing a white sweater, sunglasses, and a backwards black baseball cap turns his head in different directions under changing colored lights.",
                "https://cdn.you.ki/transcoded/ZbeBESIEBZT6ZO0ip8GBlTCqgYG2/9474065c-4b32-4a98-981c-a3c01748d17d-720x1280.mp4"
            )
        )
        arrVideoModel.add(
            VideoModel(null,
                "holding neon light",
                "Bald man with a short beard wearing a large jean jacket holds a long tubular neon light thatch",
                "https://cdn.you.ki/transcoded/3UsFX1HOCZR0KwhMQOI9C6UxYxH2/7ca2b6d2-9096-4ee5-a6ca-98d563caeda0-720x1280.mp4"
            )
        )
        arrVideoModel.add(
            VideoModel(null,
                "Sun over hills",
                "The sun sets or rises over hills, a body of water beneath them.",
                "https://cdn.you.ki/transcoded/G1F8P7O56mbBTjBULlQGqBSxcHh2/c43b3527-88d0-4ba5-9e24-889bef71206f-720x1280.mp4"
            )
        )

        return arrVideoModel
    }
}
package com.example.myworld.fragment.home.player

data class PlayerState(
        var playWhenReady: Boolean,
        var currentWindow: Int,
        var playbackPosition: Long
)
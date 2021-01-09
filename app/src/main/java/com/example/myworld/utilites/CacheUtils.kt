package com.example.myworld.utilites

import android.content.Context
import java.io.File

object CacheUtils {
    fun getVideoCacheDir(context: Context): File? {
        return File(context.externalCacheDir, "video-cache")
    }
}
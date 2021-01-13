package com.example.myworld.fragment.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myworld.R
import com.example.myworld.`class`.Gallery
import com.example.myworld.adapter.cameraAdapters.GalleryImageAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottomsheet_fragment.*

class GalleryBottomSheetFragment : BottomSheetDialogFragment() , LifecycleObserver {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme);
        val offsetFromTop = 40
        (dialog as? BottomSheetDialog)?.behavior?.apply {
            isFitToContents = false
            expandedOffset = offsetFromTop
            state = STATE_EXPANDED
        }
    }

    override fun onStart() {
        loadGallery()
        super.onStart()
    }

    /** Load Gallery */
    private fun loadGallery() {
        val model = ViewModelProvider(this).get(Gallery::class.java)
        var image = context?.let { model.listOfItems(it) }
        var video : ArrayList<String>? = context?.let { model.listOfVideos(it) }

        /** Setting up the Gallery Recycler View For Gallery Fetching */
        recycler_view.apply {
            this.layoutManager = GridLayoutManager(context, 3)
            if (image != null) {
                /** Setting up the adapter for the recycler view */
                this.adapter =
                    GalleryImageAdapter(context, image, object : GalleryImageAdapter.PhotoListener {
                        override fun onPhotoClick(path: String) {
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
//            if (video != null)
//            {
//                this.adapter = GalleryVideoAdapter(context, video, object : GalleryVideoAdapter.PhotoListener {
//                        override fun onPhotoClick(path: String) {
//                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
//            }
        }
    }
}
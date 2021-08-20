package com.thicapps.artbook.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.thicapps.artbook.adapter.ArtRecyclerAdapter
import com.thicapps.artbook.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter : ArtRecyclerAdapter,
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide:RequestManager
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            FragmentArts::class.java.name -> FragmentArts(artRecyclerAdapter)
            FragmentImageApi::class.java.name -> FragmentImageApi(imageRecyclerAdapter)
            FragmentDetails::class.java.name -> FragmentDetails(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}
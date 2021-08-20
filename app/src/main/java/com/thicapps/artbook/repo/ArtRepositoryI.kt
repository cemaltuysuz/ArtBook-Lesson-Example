package com.thicapps.artbook.repo

import ImageResponse
import androidx.lifecycle.LiveData
import com.thicapps.artbook.room.Art
import com.thicapps.artbook.util.Resource

interface ArtRepositoryI {

    suspend fun insertArt(art:Art)

    suspend fun deleteArt(art:Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString:String) :Resource<ImageResponse>
}
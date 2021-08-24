package com.thicapps.artbook.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thicapps.artbook.model.ImageResponse
import com.thicapps.artbook.room.Art
import com.thicapps.artbook.util.Resource

class FakeArtRepository() : ArtRepositoryI {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>()

    override suspend fun insertArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(0,0, listOf()))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}
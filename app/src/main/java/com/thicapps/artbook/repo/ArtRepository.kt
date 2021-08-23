package com.thicapps.artbook.repo

import androidx.lifecycle.LiveData
import com.thicapps.artbook.api.RetrofitApi
import com.thicapps.artbook.model.ImageResponse
import com.thicapps.artbook.room.Art
import com.thicapps.artbook.room.ArtDao
import com.thicapps.artbook.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class ArtRepository
    @Inject constructor(private val artDao: ArtDao,
                        private val retrofitApi: RetrofitApi) : ArtRepositoryI {

    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.getArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val responseOut : Response<ImageResponse> = retrofitApi.imageSearch(imageString)

            if (responseOut.isSuccessful){
                responseOut.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("No data !",null)
            }
        }catch (e:Exception){
            Resource.error(e.message!!,null)
        }
    }
}
package com.thicapps.artbook.api

import com.thicapps.artbook.Constants
import com.thicapps.artbook.model.ImageResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("api/")
     fun imageSearch(
        @Query("q") searchQuery : String,
        @Query("key") apiKey:String = Constants.Api.API_KEY
    ) : Response<ImageResponse>
}
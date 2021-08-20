package com.thicapps.artbook.viewmodel

import ImageResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thicapps.artbook.repo.ArtRepositoryI
import com.thicapps.artbook.room.Art
import com.thicapps.artbook.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ArtViewModel @Inject constructor(private val repositoryI: ArtRepositoryI) : ViewModel() {

    // Art Fragment
    val artist = repositoryI.getArt()

    // Image API Fragment
    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
    get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedUrl : LiveData<String>
    get() = selectedImage


    // Art Details

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
    get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url:String){
        selectedImage.postValue(url)
    }

    fun deleteArt (art:Art) = viewModelScope.launch {
        repositoryI.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repositoryI.insertArt(art)
    }

    fun makeArt(name:String, artistName:String, year:String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.value = Resource.error("Enter name, artist name, year",null)
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.value = Resource.error("Year field must be of numeric type.", null)
            return
        }

        val art = Art(name,artistName,yearInt,selectedImage.value ?:"")
        insertArt(art)
        selectedImage.value = ""
        insertArtMsg.postValue(Resource.success(art))
    }

    fun searchForImage (searchString:String){
        if (searchString.isEmpty()) return
        images.value = Resource.loading(null)

        viewModelScope.launch {
            val response = repositoryI.searchImage(searchString)
            images.value = response
        }
    }




}
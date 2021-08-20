package com.thicapps.artbook.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.thicapps.artbook.Constants
import com.thicapps.artbook.api.RetrofitApi
import com.thicapps.artbook.room.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context:Context) =
        Room.databaseBuilder(context,ArtDatabase::class.java,"ArtBookDb").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.Api.BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }
    /*
    @Singleton
    @Provides
    private fun okHttp(): OkHttpClient =
        OkHttpClient.Builder()
            .apply {
                val logging = HttpLoggingInterceptor()
                logging.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(logging)
            }
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build() */
}
package com.thicapps.artbook.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.thicapps.artbook.MainCoroutineRule
import com.thicapps.artbook.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var database:ArtDatabase
    private lateinit var dao : ArtDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),ArtDatabase::class.java)
                .allowMainThreadQueries().build()
        dao = database.artDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlockingTest {
        val exampleArt = Art("test","cemal",1999,"test.com",1)
        dao.insertArt(exampleArt)
        val list = dao.getArts().getOrAwaitValueTest()
        assertThat(list).contains(exampleArt)
    }
}
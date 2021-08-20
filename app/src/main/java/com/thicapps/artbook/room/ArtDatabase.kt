package com.thicapps.artbook.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Art::class],version = 1)
abstract class ArtDatabase : RoomDatabase() {
    abstract fun artDao (): ArtDao
}
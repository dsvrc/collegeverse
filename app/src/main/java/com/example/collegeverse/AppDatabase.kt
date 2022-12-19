package com.example.clgshare

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [MyPostData::class, MyProfileData::class, MyFavouriteData::class, MyConnectionData::class,MyQueryData::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDataDao(): AppDataDao
}
package com.example.blankspace.hilt

import android.content.Context
import com.example.blankspace.data.room.MyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(coroutineScope: CoroutineScope, @ApplicationContext context: Context) =
        MyDatabase.getDatabase(context, coroutineScope)

    @Provides
    @Singleton
    fun providesRoomDao(database: MyDatabase) = database.roomDao()
}
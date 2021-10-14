/*
 * Copyright (c) 2021. Ryan Wong (hello@ryanwong.co.uk)
 *
 */

package uk.ryanwong.dazn.codechallenge.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ryanwong.dazn.codechallenge.data.source.local.DaznApiDatabase
import uk.ryanwong.dazn.codechallenge.data.source.local.daos.DaznApiDaos
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLogDaos(database: DaznApiDatabase): DaznApiDaos {
        return DaznApiDaos(database.eventsDao, database.scheduleDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): DaznApiDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            DaznApiDatabase::class.java,
            "dazn_api_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
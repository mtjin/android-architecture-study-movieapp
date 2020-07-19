package com.mtjin.androidarchitecturestudy.di.data.local

import android.content.Context
import androidx.room.Room
import com.mtjin.androidarchitecturestudy.data.login.source.local.LoginLocalDataSource
import com.mtjin.androidarchitecturestudy.data.login.source.local.LoginLocalDataSourceImpl
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieDao
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieDatabase
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieLocalDataSource
import com.mtjin.androidarchitecturestudy.data.search.source.local.MovieLocalDataSourceImpl
import com.mtjin.androidarchitecturestudy.utils.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    //localRepo
    @Provides
    @Singleton
    fun provideLoginLocalDataSource(preferenceManager: PreferenceManager): LoginLocalDataSource {
        return LoginLocalDataSourceImpl(preferenceManager)
    }

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    //room
    @Provides
    @Singleton
    fun provideDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "Movie.db"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.movieDao()
    }

    // sharedPref
    @Provides
    @Singleton
    fun providePreferenceManager(context: Context): PreferenceManager {
        return PreferenceManager((context))
    }

}
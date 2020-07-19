package com.mtjin.androidarchitecturestudy.di.data.remote

import com.mtjin.androidarchitecturestudy.api.ApiInterface
import com.mtjin.androidarchitecturestudy.data.search.source.remote.MovieRemoteDataSource
import com.mtjin.androidarchitecturestudy.data.search.source.remote.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(apiInterface: ApiInterface): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiInterface)
    }
}